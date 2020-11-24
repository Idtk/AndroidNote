# SharedPreferences常见问题

## 不要使用SharedPreferences存储过大的值

ContextImpl对象首次获取SP时，需要实例化SharedPreferencesImpl，此时会将mLoaded置为false，然后读取对应文件内容，直到读取完成后，才会将mLoaded设置为true。如果在此过程中调用诸如getString的获取资源的方法，因为当前mLoaded为false，直接mLock.wait()进入等待，直到SP文件读取完成，调用mLock.notifyAll()才能获取资源。如果读取的SP文件过大，则会造成当前线程的卡顿。
```Java
SharedPreferencesImpl(File file, int mode) {
    mLoaded = false;

    str = new BufferedInputStream(new FileInputStream(mFile), 16 * 1024);
    map = (Map<String, Object>) XmlUtils.readMapXml(str);

    synchronized (mLock) {
            mLoaded = true;
            try {

            } catch (Throwable t) {
                mThrowable = t;
            } finally {
                mLock.notifyAll();
            }
        }
}

public String getString(String key, @Nullable String defValue) {
        synchronized (mLock) {
            awaitLoadedLocked();
            String v = (String)mMap.get(key);
            return v != null ? v : defValue;
        }
}

private void awaitLoadedLocked() {
        while (!mLoaded) {
            try {
                mLock.wait();
            } catch (InterruptedException unused) {
            }
        }
}

```

一个减少等待时间的方法是，可以提前调用getSharedPreferences方法
``` Kotlin
val sp = getSharedPreferences("name", MODE_PRIVATE)
// 做一些别的事情...
sp.getString("key","")
```
不过这个方法也只是取巧，最终的解决方法还是不要在使用中造成一个巨大的SP文件。

另外SharedPreferences初始化后会在ContextImpl#sSharedPrefsCache属性是静态中保存使用的SP文件资源，占用App的运行时内存。因为系统给予APP运行时规定的内存大小有限，如果SP文件资源过大，占用过多的运行是内存，容易造成频繁GC，界面卡顿。

```Java

private static ArrayMap<String, ArrayMap<File, SharedPreferencesImpl>> sSharedPrefsCache;

private ArrayMap<File, SharedPreferencesImpl> getSharedPreferencesCacheLocked() {
        if (sSharedPrefsCache == null) {
            sSharedPrefsCache = new ArrayMap<>();
        }

        final String packageName = getPackageName();
        ArrayMap<File, SharedPreferencesImpl> packagePrefs = sSharedPrefsCache.get(packageName);
        if (packagePrefs == null) {
            packagePrefs = new ArrayMap<>();
            sSharedPrefsCache.put(packageName, packagePrefs);
        }

        return packagePrefs;
}    
```

所以对于一些大的值，比如我们项目中的城市数据缓存，并不应该使用SP来存储，而应该直接使用文件进行存储。这样一个是可以减小SP文件的大小，加快SharedPreferences初始化，降低APP运行时内存的使用量，另一个就是可以减少存储在xml中字符串的转义。

## apply与commit都造成的ANR

我们都知道commit提交数据，会在当前线程阻塞直到数据写入完成，容易造成ANR，所以一般都建议使用apply方法提交数据，因为它的写入是异步的，但真的使用apply方法就可以完成放心了嘛？
```Java
public void apply() {
            final long startTime = System.currentTimeMillis();

            final MemoryCommitResult mcr = commitToMemory();
            final Runnable awaitCommit = new Runnable() {
                    @Override
                    public void run() {
                        try {
                            mcr.writtenToDiskLatch.await();
                        } catch (InterruptedException ignored) {
                        }
                    }
                };
            // 添加awaitCommit
            QueuedWork.addFinisher(awaitCommit);

            Runnable postWriteRunnable = new Runnable() {
                    @Override
                    public void run() {
                        awaitCommit.run();
                        // 移除awaitCommit
                        QueuedWork.removeFinisher(awaitCommit);
                    }
                };

            SharedPreferencesImpl.this.enqueueDiskWrite(mcr, postWriteRunnable);
            notifyListeners(mcr);
        }


public void handleStopActivity(IBinder token, boolean show, int configChanges,
            PendingTransactionActions pendingActions, boolean finalStateRequest, String reason) {
        final ActivityClientRecord r = mActivities.get(token);
        r.activity.mConfigChangeFlags |= configChanges;

        final StopInfo stopInfo = new StopInfo();
        performStopActivityInner(r, stopInfo, show, true /* saveState */, finalStateRequest,
                reason);

        updateVisibility(r, show);

        // Make sure any pending writes are now committed.
        if (!r.isPreHoneycomb()) {
            // 等待数据写入
            QueuedWork.waitToFinish();
        }
}       

private static final LinkedList<Runnable> sFinishers = new LinkedList<>();

public static void waitToFinish() {
        try {
            while (true) {
                Runnable finisher;

                synchronized (sLock) {
                    // 这里是之前添加的awaitCommit
                    finisher = sFinishers.poll();
                }

                if (finisher == null) {
                    break;
                }

                finisher.run();
            }
        } finally {
            sCanDelay = true;
        }
    }
```

可以看到apply虽然异步执行了SP数据的写入操作，但在ActiviyThread#handleStopActivity中会调用QueuedWork.waitToFinish()方法等待写入执行完成。项目中如果像如下代码多次调用项目中封装进行数据提交，也可能会在stop时造成阻塞甚至ANR。

```Java
putString("a")
putString("b")
putString("c")
putString("d")
putString("e")
putString("f")
putString("g")

public static void putString(Context context, String key, String value) {
		SharedPreferences sharedPreferences = getDefaultSharedPreferences(context);
		Editor edit = sharedPreferences.edit();
		edit.putString(key, value);
		edit.apply();
	}
```

## 项目中的问题和处理建议

项目中使用的城市选择器数据缓存，当前是保存在项目统一的SP文件中的，这样会在首次打开对应SP文件时，增加初始化的时间，同事也会因为SP自身内存缓存的原因过多的占用APP的运行时内存。因此建议的做法是，城市选择器的直接使用文件存取方式的硬盘缓存来保存数据。
```Java
// 磁盘缓存
    private fun getCityFile(): String {
        val path = ContextGetter.getContext().cacheDir.path+City_FILE
        val exists = FileUtil.createOrExistsFile(path)
        if (exists){
            return FileIOUtils.readFile2String(path)
        }
        return ""
    }

    private fun setCityFile(cityJson: String) {
        val path = ContextGetter.getContext().cacheDir.path+City_FILE
        val exists = FileUtil.createOrExistsFile(path)
        if (exists){
            FileIOUtils.writeFileFromString(path, cityJson)
        }
    }
```

另一个问题是项目中封装的SharedUtil工具类，其提交方法直接使用了commit()的方式，这样如果在主线程调用，将可能会造成主线程的卡顿，应将其提交数据的方式改为apply()。另外其putXXX()方法内部直接进行了数据的提交，如果一个页面多次调用了putXXX()方法，将可能在stop时卡顿或直接ANR，可以通过修改源码的apply实现方式，将其多次提交的写操作改为内存数据合并来解决。

## 总结

* 不要在SP中存放过大的Key和Value，这样会占用内存，更易频繁GC，造成卡顿。
* 一些大的独立的数据可以直接存储在单独的文件中，最起码是单独的SP中。
* SP的数据提交方法，不要在一个页面每次修改edit的时候都进行提交，而应该尽可能的批量提交。
* 最好将互相独立的业务存放在不同的SP文件中，以减少首次读取SP，进行初始化时的耗时。