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

## 循环
1. InputReaderThread 
调用getEvents获取事件并处理,输入事件调用`mQueuedListener->notifyConfigurationChanged`装填事件，当前事件所有处理完成后，调用`mQueuedListener->flush`将事件添加至InputDispatcher的mInboundQueue队列中（输入类型分为TYPE_KEY、TYPE_MOTION）。如果需要（比如线程在等待状态），则唤醒InputDispatcherThread执行。

2. InputDispatcherThread
开启循环后将会去mInboundQueue队列头部获取一个EventEntry，之后根据事件类型（TYPE_CONFIGURATION_CHANGED、TYPE_DEVICE_RESET、TYPE_KEY、TYPE_MOTION）进行处理，假设事件类型为触摸TYPE_MOTION,将会根据事件的坐标去查找事件的目标窗口。获取窗口的handle后，根据窗口来查找需要发送的事件，将其添加到outboundQueue队列。而后将从outboundQueue队列的头部取出事件发送给对应的窗口，将事件加入到waitQueue队列中等待App内事件处理完成后将会通过回调来删除waitQueue队列中的事件。</br>
上面要查找的窗口就是在ViewRootImpl中调用`mWindowSession.addToDisplay`，最终调用`mService.mInputManager.registerInputChannel(mInputChannel, mInputWindowHandle)`注册的窗口。它将作为事件输出的接收者，而这个接收者又和ViewRootImpl中创建的InputChannel相连，而InputChannel的另一端与WindowInputEventReceiver相连，通过以上的流程就可以将数据发送到App内了。</br>

3. MainLooper

## 事件对象
EventHub
NotifyArgs
EventEntry
InputEvent

## InputReader
将EventHub事件处理封装成InputEvent事件

# 显示系统
