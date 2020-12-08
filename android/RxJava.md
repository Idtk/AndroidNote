# RxJava
## 基础接口
*被观察者*
```Java
public interface ObservableSource<T> {
    void subscribe(@NonNull Observer<? super T> observer);
}
```
*观察者*
```Java
public interface Observer<T> {
    void onSubscribe(@NonNull Disposable d);
    void onNext(@NonNull T t);
    void onError(@NonNull Throwable e);
    void onComplete();
}
```
## 被观察者的封装
使用示例
```Java
HttpUtil.Builder("pro/v1/detection/submit")
    .Params("order_id", orderId)
    .Obpost()
    .subscribeOn(Schedulers.io())
    .unsubscribeOn(Schedulers.io())
    .observeOn(AndroidSchedulers.mainThread())
    .subscribe({

    }){}
```
被观察者的实现类为Observable,这里列举其subscribeOn与observeOn方法的实现
```Java
public abstract class Observable<T> implements ObservableSource<T> {

    public final Observable<T> subscribeOn(Scheduler scheduler) {
        return new ObservableSubscribeOn<T>(this, scheduler);
    }

    public final Observable<T> observeOn(Scheduler scheduler, boolean delayError, int bufferSize) {

        return new ObservableObserveOn<T>(this, scheduler, delayError, bufferSize);
    }    
}
```
可以看到调用subscribeOn与observeOn方法后，分别将被观察者包装成了ObservableSubscribeOn与ObservableObserveOn类型，其map方法也是类似，每次的链式调用都是对被观察者的再一次包装，直到调用subscribe方法，进行订阅。

## 订阅之后的观察
我们先来看一下subscribe方法
```Java
public final Disposable subscribe(Consumer<? super T> onNext, Consumer<? super Throwable> onError,
        Action onComplete, Consumer<? super Disposable> onSubscribe) {
    LambdaObserver<T> ls = new LambdaObserver<T>(onNext, onError, onComplete, onSubscribe);
    subscribe(ls);
    return ls;
}

public final void subscribe(Observer<? super T> observer) {
    ObjectHelper.requireNonNull(observer, "observer is null");
    try {
        observer = RxJavaPlugins.onSubscribe(this, observer);
        subscribeActual(observer);
    } catch (NullPointerException e) { // NOPMD
        throw e;
    } catch (Throwable e) {
        throw npe;
    }
}
```
可以看到subscribe最终调用了被观察者的subscribeActual方法，结合各个被观察中的实现一般都带有语句`ObservableSource.subscribe(observer);`,最终将形成一条链式调用。
```Java
Observable<Stirng>
    .subscribe(ObservableSubscribeOn.SubscribeOnObserver)
    .subscribe(ObservableUnsubscribeOn.UnsubscribeObserver)
    .subscribe(ObservableObserveOn.ObserveOnObserver)
    .subscribe(ObservableMap.MapObserver)
    .subscribe(LambdaObserver)
```
`Observable<Stirng>`这里即模拟的网络请求返回的jsonString数据。当Retrofit的请求完成时，其RxJava2CallAdapter将使用的CallEnqueueObservable发出事务，CallEnqueueObservable作为被观者，在其得到请求返回时会调用`observer.onNext(response);`方法，随后则按照调用链的顺序进行数据的传递。

## 线程的切换
RxJava最为大家熟知的一个功能就是线程的切换，其调用`subscribeOn(Schedulers.io())`方法，将被观察者的执行线程切换指IO线程进行，给使用者带来了便利。
```Java
// Schedulers.io()
static final class IoHolder {
    static final Scheduler DEFAULT = new IoScheduler();
}

public IoScheduler(ThreadFactory threadFactory) {
    this.threadFactory = threadFactory;
    this.pool = new AtomicReference<CachedWorkerPool>(NONE);
    start();
}

@Override
public void start() {
    CachedWorkerPool update = new CachedWorkerPool(KEEP_ALIVE_TIME, KEEP_ALIVE_UNIT, threadFactory);
    if (!pool.compareAndSet(NONE, update)) {
        update.shutdown();
    }
}

CachedWorkerPool(long keepAliveTime, TimeUnit unit, ThreadFactory threadFactory) {
    this.keepAliveTime = unit != null ? unit.toNanos(keepAliveTime) : 0L;
    this.expiringWorkerQueue = new ConcurrentLinkedQueue<ThreadWorker>();
    this.allWorkers = new CompositeDisposable();
    this.threadFactory = threadFactory;
    ScheduledExecutorService evictor = null;
    Future<?> task = null;
    if (unit != null) {
        evictor = Executors.newScheduledThreadPool(1, EVICTOR_THREAD_FACTORY);
        task = evictor.scheduleWithFixedDelay(this, this.keepAliveTime, this.keepAliveTime, TimeUnit.NANOSECONDS);
    }
    evictorService = evictor;
    evictorTask = task;
}

// AndroidSchedulers.mainThread()
private static final class MainHolder {
    static final Scheduler DEFAULT
        = new HandlerScheduler(new Handler(Looper.getMainLooper()), false);
}

public final class ObservableObserveOn<T> extends AbstractObservableWithUpstream<T, T> {
    @Override
    protected void subscribeActual(Observer<? super T> observer) {
        Scheduler.Worker w = scheduler.createWorker();

        source.subscribe(new ObserveOnObserver<T>(observer, w, delayError, bufferSize));
    }
}

final class HandlerScheduler extends Scheduler {
    private final Handler handler;
    private final boolean async;

    HandlerScheduler(Handler handler, boolean async) {
        this.handler = handler;
        this.async = async;
    }

    @Override
    public Worker createWorker() {
        return new HandlerWorker(handler, async);
    }

    private static final class HandlerWorker extends Worker {
        private final Handler handler;
        private final boolean async;

        private volatile boolean disposed;

        HandlerWorker(Handler handler, boolean async) {
            this.handler = handler;
            this.async = async;
        }

        @Override
        @SuppressLint("NewApi") // Async will only be true when the API is available to call.
        public Disposable schedule(Runnable run, long delay, TimeUnit unit) {
            run = RxJavaPlugins.onSchedule(run);
            ScheduledRunnable scheduled = new ScheduledRunnable(handler, run);
            Message message = Message.obtain(handler, scheduled);
            message.obj = this; // Used as token for batch disposal of this worker's runnables.
            if (async) {
                message.setAsynchronous(true);
            }
            handler.sendMessageDelayed(message, unit.toMillis(delay));
            // Re-check disposed state for removing in case we were racing a call to dispose().
            if (disposed) {
                handler.removeCallbacks(scheduled);
                return Disposables.disposed();
            }
            return scheduled;
        }
}

```
其中被观察者IO线程的切换是通过创建一个单核心线程的线程池来实现，其核心代码为`Executors.newScheduledThreadPool(1, EVICTOR_THREAD_FACTORY);`。而观察者的切换到主线程的实现是通过主线程Looper实现，核心代码为`new HandlerScheduler(new Handler(Looper.getMainLooper()), false);`。

## 背压
```Java
public enum BackpressureStrategy {
    MISSING,
    ERROR,
    BUFFER,
    DROP,
    LATEST
}
```