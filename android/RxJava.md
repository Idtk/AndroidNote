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
## 示例

```Java
HttpUtil.Builder("pro/v1/detection/submit")
    .Params("order_id", orderId)
    .Obpost()
    .subscribeOn(Schedulers.io())// 多次调用的情况下，仅第一次有效
    .unsubscribeOn(Schedulers.io()) // 多次调用的情况下，每次都有效
    .observeOn(AndroidSchedulers.mainThread())
    .subscribe({

    }){}
```

## 订阅流程
我们先来看一下subscribe方法。
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
可以看到subscribe最终调用了被观察者的subscribeActual方法，查看subscribeOn与observeOn方法的源码，可以看到分别将被观察者包装成了ObservableSubscribeOn与ObservableObserveOn类型，其它方法也是类似，每次的链式调用都是对被观察者的再一次包装，直到调用subscribe方法，进行订阅。结合各个被观察中的实现一般都带有语句`ObservableSource.subscribe(observer);`,最终将形成一条链式调用。
```Java
Observable<Stirng>
    .subscribe(ObservableSubscribeOn.SubscribeOnObserver)
    .subscribe(ObservableUnsubscribeOn.UnsubscribeObserver)
    .subscribe(ObservableObserveOn.ObserveOnObserver)
    .subscribe(ObservableMap.MapObserver)
    .subscribe(LambdaObserver)
```

## 数据传递流程
上面的`Observable<Stirng>`这里即模拟的网络请求返回的jsonString数据。当Retrofit的请求完成时，其RxJava2CallAdapter将使用的CallEnqueueObservable发出事务，CallEnqueueObservable作为被观者，在其得到请求返回时会调用`observer.onNext(response);`方法，随后则按照调用链的顺序进行数据的传递。
```Java
@Override
public void onNext(T t) {
    downstream.onNext(t);
}
```

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

## 线程切换的多次执行
这里代码为subscribeOn与observeOn方法的相关实现
```Java
// subscribeOn
public void subscribeActual(final Observer<? super T> observer) {
    final SubscribeOnObserver<T> parent = new SubscribeOnObserver<T>(observer);
    observer.onSubscribe(parent);
    parent.setDisposable(scheduler.scheduleDirect(new SubscribeTask(parent)));
}

final class SubscribeTask implements Runnable {
    @Override
    public void run() {
        source.subscribe(parent);
    }
}

// observeOn
protected void subscribeActual(Observer<? super T> observer) {
    if (scheduler instanceof TrampolineScheduler) {
        source.subscribe(observer);
    } else {
        Scheduler.Worker w = scheduler.createWorker();
        source.subscribe(new ObserveOnObserver<T>(observer, w, delayError, bufferSize));
    }
}

@Override
public void onNext(T t) {
    if (done) {
        return;
    }
    if (sourceMode != QueueDisposable.ASYNC) {
        queue.offer(t);
    }
    schedule();
}

```
可以看到subscribeOn的线程切换是在订阅之前，所以只有第一个生效；而observeOn在每次onNext是都切换线程，所以每一次都会生效。</bn>

## 背压

我们在异步执行的场景中，时常会出现一些，被观察者发送数据的速度，远高于观察者处理速度的情况，这时候就需要背压了，背压可以降低观察者数据接收的速度。下面来看看背压有不同的策略</br>

```Java
public enum BackpressureStrategy {
    MISSING,
    ERROR,
    BUFFER,
    DROP,
    LATEST
}
```

MISSING的效果和不使用背压策略时的效果类似，只有有一个更友好的报错。ERROR与DROP策略类似，不同之处在于超出数量后，ERROR会有报错提示而DROP没有。BUFFER的缓存是`SpscLinkedArrayQueue<T> queue`，当在onNext的时候会不断的`offer`数据，之后通过`for (;;)`去不断的取出其中的数据，因为其容量长度不限，所以在内存允许的范围内，下游将可以完整的有序的获取到所有数据。LATEST的缓存是`AtomicReference<T> queue`，当在onNext的时候，会去不断刷新，之后有一个循环不断去取，所以最后的一个值一定是可以取到的。</br>

BUFFER、LATEST策略两者都持有了一个存储被观察者发送的数据的缓存，只不过可以缓存的大小不同罢了。并且会通过`for (;;)`去取缓存中的数据发送给下游，以此完成对数据速度的降速处理。另外除了MISSING外，其余的策略都在onNext像下游发送数据的时候做了数据量的阈值校验,以提供对`request(long n)`的支持。</br>

一般在使用时RxJava时都会切换线程，Flowable/Subscriber也不例外，使用的`observeOn(AndroidSchedulers.mainThread())`方法，其Subscriber实现为`ObserveOnSubscriber`类。其内部通过`queue = new SpscArrayQueue<T>(prefetch);`队列控制了可以输出的数据大小，这里的refetch就是从observeOn中传入的。可以看出背压的设计模式其实就是我们平时很常见的生产者————消费者模式。</br>

