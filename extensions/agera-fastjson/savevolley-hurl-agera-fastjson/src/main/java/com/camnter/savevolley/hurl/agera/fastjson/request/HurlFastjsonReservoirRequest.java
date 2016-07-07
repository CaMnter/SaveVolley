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

package com.camnter.savevolley.hurl.agera.fastjson.request;

import android.support.annotation.NonNull;
import com.alibaba.fastjson.JSON;
import com.camnter.savevolley.hurl.NetworkResponse;
import com.camnter.savevolley.hurl.ParseError;
import com.camnter.savevolley.hurl.Response;
import com.camnter.savevolley.hurl.VolleyError;
import com.camnter.savevolley.hurl.agera.core.HurlReservoirRequest;
import com.camnter.savevolley.hurl.toolbox.HttpHeaderParser;
import java.io.UnsupportedEncodingException;

/**
 * Description：HurlFastjsonReservoirRequest
 * Created by：CaMnter
 * Time：2016-06-29 16:55
 */

public class HurlFastjsonReservoirRequest<T> extends HurlReservoirRequest<T>
    implements Response.Listener<T>, Response.ErrorListener {

    private static final String PROTOCOL_CHARSET = "utf-8";

    private final Response.Listener<T> mResponseListener;
    @NonNull
    private final Class<T> mClass;


    public HurlFastjsonReservoirRequest(@NonNull String url,
                                        @NonNull Class<T> clazz) {
        this(Method.GET, url, clazz);
    }


    public HurlFastjsonReservoirRequest(int method,
                                        @NonNull String url,
                                        @NonNull Class<T> clazz) {
        super(method, url, null);
        this.mClass = clazz;
        this.mResponseListener = this;
    }


    @Override protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.getResultData(),
                HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));
            return Response.success(JSON.parseObject(jsonString, this.mClass),
                HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }


    @Override protected void deliverResponse(T response) {
        this.mResponseListener.onResponse(response);
    }


    @Override public void deliverError(VolleyError error) {
        this.onErrorResponse(error);
    }


    @Override public void onErrorResponse(VolleyError error) {
        this.mReservoir.accept(error);
    }


    @Override public void onResponse(T response) {
        this.mReservoir.accept(response);
    }
}
