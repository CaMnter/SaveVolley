/*
 * Copyright (C) 2016 CaMnter yuanyu.camnter@gmail.com
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

package com.camnter.savevolley.okhttp3.volley.toolbox;

import com.camnter.savevolley.network.core.http.SaveHttpResponse;
import com.camnter.savevolley.network.core.http.SaveStatusLine;
import com.camnter.savevolley.network.core.http.core.HttpResponse;
import com.camnter.savevolley.network.okhttp3.adapter.OkHttp3HeaderAdapter;
import com.camnter.savevolley.network.okhttp3.adapter.OkHttp3HttpEntityAdapter;
import com.camnter.savevolley.network.okhttp3.adapter.OkHttp3ProtocolVersionAdapter;
import com.camnter.savevolley.network.okhttp3.adapter.OkHttp3StatusLineAdapter;
import com.camnter.savevolley.okhttp3.volley.AuthFailureError;
import com.camnter.savevolley.okhttp3.volley.Request;
import com.camnter.savevolley.okhttp3.volley.Request.Method;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

/**
 * Description：OkHttp3Stack
 * Created by：CaMnter
 * Time：2016-05-27 15:16
 */
public class OkHttp3Stack implements HttpStack {

    private OkHttpClient okHttpClient;


    public OkHttp3Stack(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
    }


    @SuppressWarnings("deprecation")
    static void setConnectionParametersForRequest(okhttp3.Request.Builder requestBuilder, Request<?> request)
            throws IOException, AuthFailureError {
        switch (request.getMethod()) {
            case Method.DEPRECATED_GET_OR_POST:
                byte[] postBody = request.getPostBody();
                if (postBody != null) {
                    requestBuilder.post(
                            RequestBody.create(MediaType.parse(request.getPostBodyContentType()),
                                    postBody));
                }
                break;
            case Method.GET:
                requestBuilder.get();
                break;
            case Method.DELETE:
                requestBuilder.delete();
                break;
            case Method.POST:
                requestBuilder.post(createRequestBody(request));
                break;
            case Method.PUT:
                requestBuilder.put(createRequestBody(request));
                break;
            case Method.HEAD:
                requestBuilder.head();
                break;
            case Method.OPTIONS:
                requestBuilder.method("OPTIONS", null);
                break;
            case Method.TRACE:
                requestBuilder.method("TRACE", null);
                break;
            case Method.PATCH:
                requestBuilder.patch(createRequestBody(request));
                break;
            default:
                throw new IllegalStateException("Unknown method type.");
        }
    }


    private static RequestBody createRequestBody(Request<?> request) throws AuthFailureError {
        byte[] body = request.getBody();
        if (body == null) return null;
        return RequestBody.create(MediaType.parse(request.getBodyContentType()), body);
    }


    /**
     * Performs an HTTP request with the given parameters.
     *
     * <p>A GET request is sent if request.getPostBody() == null. A POST request is sent otherwise,
     * and the Content-Type header is set to request.getPostBodyContentType().</p>
     *
     * @param request the request to perform
     * @param additionalHeaders additional headers to be sent together with
     * {@link Request#getHeaders()}
     * @return the HTTP response
     */
    @Override
    public HttpResponse performRequest(Request<?> request, Map<String, String> additionalHeaders)
            throws IOException, AuthFailureError {
        HashMap<String, String> map = new HashMap<>();
        map.putAll(request.getHeaders());
        map.putAll(additionalHeaders);

        okhttp3.Request.Builder okHttpRequestBuilder = new okhttp3.Request.Builder();
        for (final String name : map.keySet()) {
            okHttpRequestBuilder.addHeader(name, map.get(name));
        }

        int timeoutMs = request.getTimeoutMs();
        OkHttpClient okHttpClient = this.okHttpClient.newBuilder()
                .connectTimeout(timeoutMs,
                        TimeUnit.MILLISECONDS)
                .readTimeout(timeoutMs, TimeUnit.MILLISECONDS)
                .writeTimeout(timeoutMs, TimeUnit.MILLISECONDS)
                .build();
        setConnectionParametersForRequest(okHttpRequestBuilder, request);

        okhttp3.Request okHttp3Request = okHttpRequestBuilder.url(request.getUrl()).build();
        okhttp3.Response okHttpResponse = okHttpClient.newCall(okHttp3Request).execute();

        SaveStatusLine saveStatusLine = OkHttp3StatusLineAdapter.getInstance()
                .adaptiveStatusLine(
                        OkHttp3ProtocolVersionAdapter
                                .getInstance(),
                        okHttpResponse);

        SaveHttpResponse saveHttpResponse = new SaveHttpResponse(saveStatusLine);
        saveHttpResponse.setEntity(
                OkHttp3HttpEntityAdapter.getInstance().adaptiveEntity(okHttpResponse));
        OkHttp3HeaderAdapter.getInstance().adaptiveHeader(saveHttpResponse, okHttpResponse);
        return saveHttpResponse;
    }
}
