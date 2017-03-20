/*
 * Copyright (C) 2014 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package okhttp3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import okhttp3.internal.NamedRunnable;
import okhttp3.internal.cache.CacheInterceptor;
import okhttp3.internal.connection.ConnectInterceptor;
import okhttp3.internal.connection.StreamAllocation;
import okhttp3.internal.http.BridgeInterceptor;
import okhttp3.internal.http.CallServerInterceptor;
import okhttp3.internal.http.RealInterceptorChain;
import okhttp3.internal.http.RetryAndFollowUpInterceptor;
import okhttp3.internal.platform.Platform;

import static okhttp3.internal.platform.Platform.INFO;

/**
 * okhttp3.Call的实现类
 */
final class RealCall implements Call {
  final OkHttpClient client;
  final RetryAndFollowUpInterceptor retryAndFollowUpInterceptor;

  /** The application's original request unadulterated by redirects or auth headers. */
  final Request originalRequest;
  final boolean forWebSocket;

  // Guarded by this.
  private boolean executed;

  RealCall(OkHttpClient client, Request originalRequest, boolean forWebSocket) {
    this.client = client;
    this.originalRequest = originalRequest;// 传递进入的Request对象
    this.forWebSocket = forWebSocket;
    this.retryAndFollowUpInterceptor = new RetryAndFollowUpInterceptor(client, forWebSocket);
  }

  @Override public Request request() {
    return originalRequest;
  }

  /**
   * 立即执行请求
   * @return
   * @throws IOException
   * 1、检查请求是否已经被执行，每个Call只能执行一次
   * 2、通过调用dispatcher类,将Call加入到执行的同步请求列表(Dispatcher.runningSyncCalls)中
   * 3、获取返回的拦截链
   * 4、通知dispatcher已完成
   */
  @Override public Response execute() throws IOException {
    synchronized (this) {
      if (executed) throw new IllegalStateException("Already Executed");
      executed = true;
    }
    captureCallStackTrace();
    try {
      client.dispatcher().executed(this);
      Response result = getResponseWithInterceptorChain();
      if (result == null) throw new IOException("Canceled");
      return result;
    } finally {
      client.dispatcher().finished(this);
    }
  }

  /**
   * 捕获异常
   */
  private void captureCallStackTrace() {
    Object callStackTrace = Platform.get().getStackTraceForCloseable("response.body().close()");
    retryAndFollowUpInterceptor.setCallStackTrace(callStackTrace);
  }

  @Override public void enqueue(Callback responseCallback) {
    synchronized (this) {
      if (executed) throw new IllegalStateException("Already Executed");
      executed = true;
    }
    captureCallStackTrace();
    /**
     * 如果还可以并发，则增加到异步执行的列表，否则增加到异步执行的准备列表（即缓存这个异步回调）
     */
    client.dispatcher().enqueue(new AsyncCall(responseCallback));
  }

  @Override public void cancel() {
    retryAndFollowUpInterceptor.cancel();
  }

  @Override public synchronized boolean isExecuted() {
    return executed;
  }

  @Override public boolean isCanceled() {
    return retryAndFollowUpInterceptor.isCanceled();
  }

  @SuppressWarnings("CloneDoesntCallSuperClone") // We are a final type & this saves clearing state.
  @Override public RealCall clone() {
    return new RealCall(client, originalRequest, forWebSocket);
  }

  StreamAllocation streamAllocation() {
    return retryAndFollowUpInterceptor.streamAllocation();
  }

  final class AsyncCall extends NamedRunnable {
    private final Callback responseCallback;

    AsyncCall(Callback responseCallback) {
      super("OkHttp %s", redactedUrl());
      // 用户填入的callback，用于返回结果的回调
      this.responseCallback = responseCallback;
    }

    String host() {
      return originalRequest.url().host();
    }

    Request request() {
      return originalRequest;
    }

    RealCall get() {
      return RealCall.this;
    }

    @Override protected void execute() {
      boolean signalledCallback = false;
      try {
        // 拦截器链
        Response response = getResponseWithInterceptorChain();
        if (retryAndFollowUpInterceptor.isCanceled()) {
          signalledCallback = true;
          responseCallback.onFailure(RealCall.this, new IOException("Canceled"));
        } else {
          signalledCallback = true;
          responseCallback.onResponse(RealCall.this, response);
        }
      } catch (IOException e) {
        if (signalledCallback) {
          // Do not signal the callback twice!
          Platform.get().log(INFO, "Callback failure for " + toLoggableString(), e);
        } else {
          responseCallback.onFailure(RealCall.this, e);
        }
      } finally {
        // 执行完成调用finished，间接调用Dispatcher.promoteCalls()
        client.dispatcher().finished(this);
      }
    }
  }

  /**
   * Returns a string that describes this call. Doesn't include a full URL as that might contain
   * sensitive information.
   */
  String toLoggableString() {
    return (isCanceled() ? "canceled " : "")
        + (forWebSocket ? "web socket" : "call")
        + " to " + redactedUrl();
  }

  String redactedUrl() {
    return originalRequest.url().redact();
  }

  /**
   * 构建一个完整的堆栈拦截器
   * @return
   * @throws IOException
   */
  Response getResponseWithInterceptorChain() throws IOException {
    // Build a full stack of interceptors.
    List<Interceptor> interceptors = new ArrayList<>();
    // client中配置的interceptors
    interceptors.addAll(client.interceptors());
    // 重定向与失败重试
    interceptors.add(retryAndFollowUpInterceptor);
    // 用户的请求头处理，响应处理
    // (Cookie持久性策略)
    interceptors.add(new BridgeInterceptor(client.cookieJar()));
    // 缓存请求、响应缓存的写入 (客户端设置的缓存策略)
    interceptors.add(new CacheInterceptor(client.internalCache()));
    // 与服务器建立连接
    interceptors.add(new ConnectInterceptor(client));
    if (!forWebSocket) {
      // client配置的networkInterceptors，用于观察请求和响应
      interceptors.addAll(client.networkInterceptors());
    }
    // 发送请求，读取服务器的响应
    interceptors.add(new CallServerInterceptor(forWebSocket));
    // 设置完整的OkHttp拦截链
    Interceptor.Chain chain = new RealInterceptorChain(
        interceptors, null, null, null, 0, originalRequest);
    // 调用链中的下一个拦截器，在这里是开始调用第一个拦截器
    return chain.proceed(originalRequest);
  }
}
