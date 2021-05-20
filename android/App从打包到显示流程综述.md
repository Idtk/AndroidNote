# App打包到显示流程综述
## Apk打包
* AAPT：打包资源文件，生成R.java、resource.arsc
* AIDL：处理AIDL生成对应的java文件
* 生成class：编译源代码，生成class文件
* 生成dex：调用dx工具，将class文件转化为class.dex
* 打包Apk：资源文件与dex文件将会被apkbuilder工具打包成apk文件
* 签名：使用keystore对Apk文件进行签名
* 文件对齐:使用zipalign对签名后的文件进行对齐处理

## Apk安装
Android apk有四种安装方式：
* 系统启动时安装
* Google Play安装
* ADB命令安装
* 三方应用安装

安装的大致流程如下：
* 拷贝Apk至/data/app目录下，而系统的会在system分区下，比如/system/app、/system/vendor/app等目录
* 创建/data/data/pkg应用数据目录，存放sp文件、so文件等，将app的可执行文件dex拷贝至/data/dalvik-cache目录下缓存起来
* 解析AndroidMainfest.xml文件，提取应用包信息，注册到 packags.xml
* 调用系统installd服务安装Apk
* 桌面显示安装icon

## App启动
* 进程创建，系统在启动时将会创建Zygote进程，用户点击桌面图标后，如果应用所属的进行还未创建，AMS将会通过socket通知Zygote创建进程，Zygote通过fork方式创建应用所属进程。
* 创建进程后将会调用ActivityThread.main方法，开启主线程Looper循环，注册IApplication，创建Application、ContentProvider并执行。
* 如果应用进程已经存在，则直接调用realStartActivity方法，其将会按照顺序调用handleLaunchActivity与handleResumeActivity方法。前者创建Activity、PhoneWindow、DecorView；后者创建app与系统交互的关键类ViewRootImpl，在其中将获取与WMS交互的Session，添加Choreographer回调，创建与IMS交互的InputChannel，注册InputEventRevicer。
* WMS的Session将会创建Surface的Session，进而创建缓冲区，以及管理缓冲区的ShareClient，为之后的界面显示做准备。当垂直同步信号到来时，app进行绘制，首先会申请获取缓存区的，在其上进行绘制后，再释放缓冲区。
* 事件分发与事件读取线程，在系统启动时就会创建与运行。事件读取线程通过getEvents方法获取系统的输入事件，会将其中非驱动操作相关的事件通过QueuedListener接口传递给事件发分线程的InboundQueue队列。事件分发线程会通过窗口注册时的connection对象查找到事件对应的窗口，将其添加到connet.outboundQueue队列，之后根据事件类型进行分发。当App事件处理完成后将会调用其handleReceiveCallback方法，清除connect.waitQueue中的对应事件。

## 流程图
<img src="https://github.com/Idtk/AndroidNote/blob/master/android/img/App%E4%BB%8E%E6%89%93%E5%8C%85%E5%88%B0%E6%98%BE%E7%A4%BA%E6%B5%81%E7%A8%8B.png" title="流程图"/>