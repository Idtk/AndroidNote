/*
 * Copyright (C) 2016 Square, Inc.
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
package okhttp3.internal.http;

import java.io.IOException;
import java.net.ProtocolException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.Util;
import okhttp3.internal.connection.StreamAllocation;
import okio.BufferedSink;
import okio.Okio;
import okio.Sink;

/** This is the last interceptor in the chain. It makes a network call to the server. */
public final class CallServerInterceptor implements Interceptor {
  private final boolean forWebSocket;

  public CallServerInterceptor(boolean forWebSocket) {
    this.forWebSocket = forWebSocket;
  }

  @Override public Response intercept(Chain chain) throws IOException {
    HttpCodec httpCodec = ((RealInterceptorChain) chain).httpStream();
    StreamAllocation streamAllocation = ((RealInterceptorChain) chain).streamAllocation();
    Request request = chain.request();

    long sentRequestMillis = System.currentTimeMillis();// 发送前标记时间点
    httpCodec.writeRequestHeaders(request);// 请求头设置HTTP编码

    Response.Builder responseBuilder = null; // 清空响应
    if (HttpMethod.permitsRequestBody(request.method()) && request.body() != null) { // 校验请求体及其方法是否可以携带请求体
      // If there's a "Expect: 100-continue" header on the request, wait for a "HTTP/1.1 100
      // Continue" response before transmitting the request body. If we don't get that, return what
      // we did get (such as a 4xx response) without ever transmitting the request body.
      // 在发送请求体之前，先发送一个请求, 包含一个Expect:100-continue, 询问Server使用愿意接受数据
      // 接收到Server返回的100-continue应答以后, 才把数据发送给Server
      if ("100-continue".equalsIgnoreCase(request.header("Expect"))) {
        httpCodec.flushRequest();
        responseBuilder = httpCodec.readResponseHeaders(true);
      }

      // Write the request body, unless an "Expect: 100-continue" expectation failed.
      // 配置请求体
      if (responseBuilder == null) {
        Sink requestBodyOut = httpCodec.createRequestBody(request, request.body().contentLength());
        BufferedSink bufferedRequestBody = Okio.buffer(requestBodyOut);// 新建缓冲区
        request.body().writeTo(bufferedRequestBody);// 写入请求体
        bufferedRequestBody.close();// 完成请求体
      }
    }

    httpCodec.finishRequest();// 去完成请求，之后将关闭连接

    if (responseBuilder == null) {
      responseBuilder = httpCodec.readResponseHeaders(false);// HTTP解码响应
    }

    // 构建响应
    Response response = responseBuilder
        .request(request) // 请求
        .handshake(streamAllocation.connection().handshake())// 握手
        .sentRequestAtMillis(sentRequestMillis) // 请求时间
        .receivedResponseAtMillis(System.currentTimeMillis())// 应答时间
        .build();

    int code = response.code(); // 响应码
    if (forWebSocket && code == 101) { // 101 server通知client更换交互协议
      // Connection is upgrading, but we need to ensure interceptors see a non-null response body.
      response = response.newBuilder()
          .body(Util.EMPTY_RESPONSE)
          .build();
    } else {
      response = response.newBuilder()
          .body(httpCodec.openResponseBody(response))
          .build();// 获取响应
    }

    if ("close".equalsIgnoreCase(response.request().header("Connection"))
        || "close".equalsIgnoreCase(response.header("Connection"))) {
      streamAllocation.noNewStreams(); // 关闭连接
    }

    // 无响应body
    if ((code == 204 || code == 205) && response.body().contentLength() > 0) {
      throw new ProtocolException(
          "HTTP " + code + " had non-zero Content-Length: " + response.body().contentLength());
    }

    // 返回响应给ConnectInterceptor
    return response;
  }
}
