/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package okhttp3.internal.cache;

import java.io.IOException;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.Internal;
import okhttp3.internal.Util;
import okhttp3.internal.http.HttpCodec;
import okhttp3.internal.http.HttpHeaders;
import okhttp3.internal.http.HttpMethod;
import okhttp3.internal.http.RealResponseBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import okio.Sink;
import okio.Source;
import okio.Timeout;

import static java.net.HttpURLConnection.HTTP_NOT_MODIFIED;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static okhttp3.internal.Util.closeQuietly;
import static okhttp3.internal.Util.discard;

/** Serves requests from the cache and writes responses to the cache. */

/**
 * 根据本地硬盘缓存和请求设置，生成缓存策略。
 * 根据生成的缓存策略，来确定是否可以直接使用上一次相用url、方法、部分头属性返回的响应，
 * 已达到节省带宽和时间的目的。
 * 如果使用需要进行网络求情，则继续调用ConnectInterceptor
 * 获取返回后更新本地缓存
 */
public final class CacheInterceptor implements Interceptor {
  final InternalCache cache;

  public CacheInterceptor(InternalCache cache) {
    this.cache = cache;
  }

  @Override public Response intercept(Chain chain) throws IOException {
    // 如果配置了cache，则从缓存中获取
    Response cacheCandidate = cache != null
        ? cache.get(chain.request())
        : null;

    long now = System.currentTimeMillis();

    CacheStrategy strategy = new CacheStrategy.Factory(now, chain.request(), cacheCandidate).get();// 生成缓存策略
    Request networkRequest = strategy.networkRequest;// 请求
    Response cacheResponse = strategy.cacheResponse;// 响应

    if (cache != null) {
      cache.trackResponse(strategy);//响应追踪
    }

    if (cacheCandidate != null && cacheResponse == null) {
      closeQuietly(cacheCandidate.body()); // The cache candidate wasn't applicable. Close it.
    }

    // 没有响应缓存，并且禁止网络，则直接返回504
    // If we're forbidden from using the network and the cache is insufficient, fail.
    if (networkRequest == null && cacheResponse == null) {
      return new Response.Builder()
          .request(chain.request())
          .protocol(Protocol.HTTP_1_1)
          .code(504)
          .message("Unsatisfiable Request (only-if-cached)")
          .body(Util.EMPTY_RESPONSE)
          .sentRequestAtMillis(-1L)
          .receivedResponseAtMillis(System.currentTimeMillis())
          .build();
    }

    // 缓存有效，直接从返回中获取，不需要网络
    // If we don't need the network, we're done.
    if (networkRequest == null) {
      return cacheResponse.newBuilder()
          .cacheResponse(stripBody(cacheResponse))
          .build();
    }

    // 缓存无效，执行ConnectInterceptor
    Response networkResponse = null;
    try {
      // 执行ConnectInterceptor
      networkResponse = chain.proceed(networkRequest);
    } finally {
      // If we're crashing on I/O or otherwise, don't leak the cache body.
      // 无响应与响应缓存
      if (networkResponse == null && cacheCandidate != null) {
        closeQuietly(cacheCandidate.body());
      }
    }

    // 本地也有缓存，网络缓存和本地缓存二选一
    // If we have a cache response too, then we're doing a conditional get.
    if (cacheResponse != null) {
      if (networkResponse.code() == HTTP_NOT_MODIFIED) {// 返回304，服务器数据在一定时间段内未更新，并且上次返回的缓存可用
        Response response = cacheResponse.newBuilder() // 通过本地缓存构造新的返回
            .headers(combine(cacheResponse.headers(), networkResponse.headers())) // 先添加本地缓存响应的header键值对，再添加网络响应的header键值对，其中相同的将被覆盖
            .sentRequestAtMillis(networkResponse.sentRequestAtMillis())
            .receivedResponseAtMillis(networkResponse.receivedResponseAtMillis())
            .cacheResponse(stripBody(cacheResponse))
            .networkResponse(stripBody(networkResponse))
            .build();
        networkResponse.body().close();

        // Update the cache after combining headers but before stripping the
        // Content-Encoding header (as performed by initContentStream()).
        cache.trackConditionalCacheHit();
        cache.update(cacheResponse, response);// 更新硬盘响应缓存
        return response;
      } else {
        closeQuietly(cacheResponse.body());
      }
    }

    // 构建网络响应
    Response response = networkResponse.newBuilder()
        .cacheResponse(stripBody(cacheResponse))
        .networkResponse(stripBody(networkResponse))
        .build();

    // 保存缓存到本地
    if (HttpHeaders.hasBody(response)) { // 如果需要响应体
      CacheRequest cacheRequest = maybeCache(response, networkResponse.request(), cache); // 缓存请求
      response = cacheWritingResponse(cacheRequest, response); // 将请求写入响应对象
    }

    return response;
  }

  private static Response stripBody(Response response) {
    return response != null && response.body() != null
        ? response.newBuilder().body(null).build()
        : response;
  }

  /**
   * 尝试将响应缓存添加到硬盘缓存中
   * @param userResponse
   * @param networkRequest
   * @param responseCache
   * @return
   * @throws IOException
   */
  private CacheRequest maybeCache(Response userResponse, Request networkRequest,
      InternalCache responseCache) throws IOException {
    if (responseCache == null) return null; // 无响应缓存，当然也无请求缓存

    // Should we cache this response for this request?
    // 是否支持缓存
    if (!CacheStrategy.isCacheable(userResponse, networkRequest)) {
      // 根据请求的方法，判断是否支持缓存
      if (HttpMethod.invalidatesCache(networkRequest.method())) {
        try {
          // 如果不支持缓存，则移除
          responseCache.remove(networkRequest);
        } catch (IOException ignored) {
          // The cache cannot be written.
        }
      }
      return null;
    }

    // Offer this request to the cache.
    // 添加缓存
    return responseCache.put(userResponse);
  }

  /**
   * Returns a new source that writes bytes to {@code cacheRequest} as they are read by the source
   * consumer. This is careful to discard bytes left over when the stream is closed; otherwise we
   * may never exhaust the source stream and therefore not complete the cached response.
   *
   * 将缓存请求读到网络响应中
   */
  private Response cacheWritingResponse(final CacheRequest cacheRequest, Response response)
      throws IOException {
    // Some apps return a null body; for compatibility we treat that like a null cache request.
    // 缓存请求为null或缓存请求的body为null，直接返回网络响应
    if (cacheRequest == null) return response;
    Sink cacheBodyUnbuffered = cacheRequest.body();
    if (cacheBodyUnbuffered == null) return response;

    final BufferedSource source = response.body().source();// read inputStream
    final BufferedSink cacheBody = Okio.buffer(cacheBodyUnbuffered);// write outputStream

    // 将cacheRequestBody读取到cacheBody中
    Source cacheWritingSource = new Source() {
      boolean cacheRequestClosed;

      @Override public long read(Buffer sink, long byteCount) throws IOException {
        long bytesRead;
        try {
          bytesRead = source.read(sink, byteCount);// 读取写缓冲区中的cacheBody
        } catch (IOException e) {
          if (!cacheRequestClosed) {
            cacheRequestClosed = true;
            cacheRequest.abort(); // Failed to write a complete cache response.
          }
          throw e;
        }

        if (bytesRead == -1) {
          if (!cacheRequestClosed) {
            cacheRequestClosed = true;
            cacheBody.close(); // The cache response is complete!
          }
          return -1;
        }

        sink.copyTo(cacheBody.buffer(), sink.size() - bytesRead, bytesRead);// 复制cacheBody到写缓冲区
        cacheBody.emitCompleteSegments();
        return bytesRead;
      }

      @Override public Timeout timeout() {
        return source.timeout();
      }

      @Override public void close() throws IOException {
        if (!cacheRequestClosed
            && !discard(this, HttpCodec.DISCARD_STREAM_TIMEOUT_MILLIS, MILLISECONDS)) {
          cacheRequestClosed = true;
          cacheRequest.abort();
        }
        source.close();
      }
    };

    return response.newBuilder()
        .body(new RealResponseBody(response.headers(), Okio.buffer(cacheWritingSource)))
        .build();
  }

  /** Combines cached headers with a network headers as defined by RFC 2616, 13.5.3. */
  /**
   * 先写本地缓存的头属性，再写网络缓存的头属性，相同则被网络属性覆盖
   * @param cachedHeaders
   * @param networkHeaders
   * @return
   */
  private static Headers combine(Headers cachedHeaders, Headers networkHeaders) {
    Headers.Builder result = new Headers.Builder();

    for (int i = 0, size = cachedHeaders.size(); i < size; i++) {
      String fieldName = cachedHeaders.name(i);
      String value = cachedHeaders.value(i);
      if ("Warning".equalsIgnoreCase(fieldName) && value.startsWith("1")) {
        continue; // Drop 100-level freshness warnings.
      }
      if (!isEndToEnd(fieldName) || networkHeaders.get(fieldName) == null) {
        Internal.instance.addLenient(result, fieldName, value);
      }
    }

    for (int i = 0, size = networkHeaders.size(); i < size; i++) {
      String fieldName = networkHeaders.name(i);
      if ("Content-Length".equalsIgnoreCase(fieldName)) {
        continue; // Ignore content-length headers of validating responses.
      }
      if (isEndToEnd(fieldName)) {
        Internal.instance.addLenient(result, fieldName, networkHeaders.value(i));
      }
    }

    return result.build();
  }

  /**
   * Returns true if {@code fieldName} is an end-to-end HTTP header, as defined by RFC 2616,
   * 13.5.1.
   */
  static boolean isEndToEnd(String fieldName) {
    return !"Connection".equalsIgnoreCase(fieldName)
        && !"Keep-Alive".equalsIgnoreCase(fieldName)
        && !"Proxy-Authenticate".equalsIgnoreCase(fieldName)
        && !"Proxy-Authorization".equalsIgnoreCase(fieldName)
        && !"TE".equalsIgnoreCase(fieldName)
        && !"Trailers".equalsIgnoreCase(fieldName)
        && !"Transfer-Encoding".equalsIgnoreCase(fieldName)
        && !"Upgrade".equalsIgnoreCase(fieldName);
  }
}
