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

package com.camnter.savevolley.hurl.gson.request;

import android.support.annotation.NonNull;
import com.camnter.savevolley.hurl.NetworkResponse;
import com.camnter.savevolley.hurl.ParseError;
import com.camnter.savevolley.hurl.Request;
import com.camnter.savevolley.hurl.Response;
import com.camnter.savevolley.hurl.VolleyError;
import com.camnter.savevolley.hurl.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import java.io.UnsupportedEncodingException;

/**
 * Description：HurlGsonRequest
 * Created by：CaMnter
 * Time：2016-05-29 22:14
 */
public abstract class HurlGsonRequest<T> extends Request<T>
    implements Response.Listener<T>, Response.ErrorListener {

    private static final String PROTOCOL_CHARSET = "utf-8";

    private Gson mGson;
    private Response.Listener<T> mResponseListener;
    @NonNull
    private Class<T> mClass;


    public HurlGsonRequest(@NonNull String url,
                           @NonNull Class<T> clazz) {
        this(Request.Method.GET, url, clazz);
    }


    public HurlGsonRequest(int method,
                           @NonNull String url,
                           @NonNull Class<T> clazz) {
        super(method, url, null);
        this.mGson = new Gson();
        this.mClass = clazz;
        this.mResponseListener = this;
    }


    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.getResultData(),
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
     * @return this request's {@link com.camnter.savevolley.hurl.Response.ErrorListener}.
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
