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

package com.camnter.easyvolley.samples.request;

import com.camnter.easyvolley.hurl.NetworkResponse;
import com.camnter.easyvolley.hurl.ParseError;
import com.camnter.easyvolley.hurl.Request;
import com.camnter.easyvolley.hurl.Response;
import com.camnter.easyvolley.hurl.VolleyError;
import com.camnter.easyvolley.hurl.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import java.io.UnsupportedEncodingException;

/**
 * Description：HurlGsonRequest
 * Created by：CaMnter
 * Time：2016-05-29 22:14
 */
public abstract class HurlGsonRequest<T> extends Request<T>
        implements Response.Listener<T>, Response.ErrorListener {

    protected static final String PROTOCOL_CHARSET = "utf-8";

    private Gson mGson;
    private Response.Listener<T> mResponseListener;
    private Class<T> mClass;


    public HurlGsonRequest(String url, Class<T> clazz) {
        this(com.camnter.easyvolley.okhttp3.volley.Request.Method.GET, url, clazz);
    }


    public HurlGsonRequest(int method, String url, Class<T> clazz) {
        super(method, url, null);
        this.mGson = new Gson();
        this.mClass = clazz;
        this.mResponseListener = this;
    }


    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));
            return Response.success(
                    this.mGson.fromJson(jsonString, this.mClass),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }


    @Override protected void deliverResponse(T response) {
        this.mResponseListener.onResponse(response);
    }


    /**
     * @return this request's {@link com.camnter.easyvolley.hurl.Response.ErrorListener}.
     */
    public Response.ErrorListener getErrorListener() {
        return this;
    }


    /**
     * Delivers error message to the ErrorListener that the Request was
     * initialized with.
     *
     * @param error Error details
     */
    @Override public void deliverError(VolleyError error) {
        this.onErrorResponse(error);
    }


    /**
     * Called when a response is received.
     */
    public abstract void onResponse(T response);

    /**
     * Callback method that an error has been occurred with the
     * provided error code and optional user-readable message.
     */
    public abstract void onErrorResponse(VolleyError error);
}
