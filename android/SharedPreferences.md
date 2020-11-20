## SharedPreferences使用中的一些问题

### 首次在主线程使用会增加耗时
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


### 存储大的数据会浪费内存
ContextImpl对象中的sSharedPrefsCache属性是静态的，它保存你使用的SP文件，如果文件过大，将会占用比较高的内存

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

### apply 与 commit造成的ANR

commit会在当前线程写入,我们当前封装的公共方法是commit
```Java
@Override
        public boolean commit() {
            long startTime = 0;

            if (DEBUG) {
                startTime = System.currentTimeMillis();
            }

            MemoryCommitResult mcr = commitToMemory();

            SharedPreferencesImpl.this.enqueueDiskWrite(
                mcr, null /* sync write on this thread okay */);
            try {
                mcr.writtenToDiskLatch.await();
            } catch (InterruptedException e) {
                return false;
            } finally {
                if (DEBUG) {
                    Log.d(TAG, mFile.getName() + ":" + mcr.memoryStateGeneration
                            + " committed after " + (System.currentTimeMillis() - startTime)
                            + " ms");
                }
            }
            notifyListeners(mcr);
            return mcr.writeToDiskResult;
}
```

apply提交是异步执行，但在handleStopActivity中会等待提交执行完成，所以过多的apply提交，也可能会造成阻塞

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
            QueuedWork.waitToFinish();
        }
}       

@GuardedBy("sLock")
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