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

# 显示系统
