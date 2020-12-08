# Context
## Context类型
<img src="‪Context.png"/></br>

## getContext()
* Fragment#getContext()/getActivity 返回为FragmentActivity
* Window#getContext() 返回为Activity 或 ContextThemeWrapper
* Dialog#getContext() 返回为ContextThemeWrapper
* View#getContext() 返回为Activity 或 ContextThemeWrapper
* Application#getContext 返回为ContextThemeWrapper
## Context使用
* glide 用于创建fragment来绑定生命周期，不要使用ApplicationContext
* SP的获取实际上是调用了ContextImpl.getSharedPreferences(),注意大文件存取可能造成的卡顿、ANR
* Server 因为本身没有Activity栈，所以在启动Activity时候需添加FLAG_ACTIVITY_NEW_TASK


# Glide
## 优点
* 图片类型的判断
* 图片压缩
* 生命周期管理
* 缓存策略

# 事件分发
## 队列
1. mInboundQueue // 需要InputDispatcher分发的事件队列
2. outboundQueue // 需要被发布到connection的事件队列
3. waitQueue // 已发布到connection，但还没有收到来自应用的“finished”响应的事件队列

## InputReader
将EventHub事件处理封装成InputEvent事件

# RxJava

## 被观察者
```Java
public interface ObservableSource<T> {
    void subscribe(@NonNull Observer<? super T> observer);
}
```
## 观察者
```Java
public interface Observer<T> {
    void onSubscribe(@NonNull Disposable d);
    void onNext(@NonNull T t);
    void onError(@NonNull Throwable e);
    void onComplete();
}
```
## 切换线程示例
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
被观察者的实现类未Observable,这里列举subscribeOn与observeOn方法的实现
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
可以看到调用subscribeOn与observeOn方法后，分别将被观察者包装成了ObservableSubscribeOn与ObservableObserveOn类型，其余的map等方法也是类似，每次的链式调用都是对被观察者的再一次包装，直到调用subscribe方法，进行订阅。

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
    .subscribe(ObservableObserveOn.ObserveOnObserver)
    .subscribe(ObservableMap.MapObserver)
    .subscribe(LambdaObserver)
```
`Observable<Stirng>`这里即模拟的网络请求返回的jsonString数据。从上面的代码可以看出，当Retrofit的请求完成时，其RxJava2CallAdapter将使用的CallEnqueueObservable，作为被观者者在得到请求返回时会调用`observer.onNext(response);`方法，随后则按照调用链的顺序进行数据的传递。