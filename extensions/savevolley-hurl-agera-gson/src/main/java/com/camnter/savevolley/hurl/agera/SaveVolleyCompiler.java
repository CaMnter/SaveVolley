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

package com.camnter.savevolley.hurl.agera;

import android.content.Context;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.camnter.savevolley.hurl.Request;
import com.camnter.savevolley.hurl.Request.Method;
import com.camnter.savevolley.hurl.RequestQueue;
import com.camnter.savevolley.hurl.agera.gson.request.HurlGsonReservoirRequest;
import com.camnter.savevolley.hurl.agera.gson.request.HurlJsonArrayReservoirRequest;
import com.camnter.savevolley.hurl.agera.gson.request.HurlJsonReservoirRequest;
import com.camnter.savevolley.hurl.toolbox.Volley;
import com.google.android.agera.Reservoir;
import java.util.HashMap;
import java.util.Map;

import static com.camnter.savevolley.hurl.agera.Preconditions.checkArgument;
import static com.camnter.savevolley.hurl.agera.Preconditions.checkNotNull;

/**
 * Description：SaveVolleyCompiler
 * Created by：CaMnter
 * Time：2016-06-23 21:05
 */

public final class SaveVolleyCompiler<RType> implements
    SaveVolleyCompilerStates.VRequestState<RType>,
    SaveVolleyCompilerStates.VRequestQueue<RType>,
    SaveVolleyCompilerStates.VTermination<RType> {

    private int requestMethod;
    @NonNull
    private String requestUrl;
    private int requestParseStyle;
    private Class requestClassOf;
    private Map<String, String> requestParams;

    private Request<?> request;

    private Reservoir<Object> reservoir;

    private static final ThreadLocal<SaveVolleyCompiler> compilers = new ThreadLocal<>();
    private static final ThreadLocal<RequestQueue> queue = new ThreadLocal<>();


    public static RequestQueue requestQueue(Context context) {
        if (queue.get() == null) {
            queue.set(Volley.newRequestQueue(context));
        }
        return queue.get();
    }


    private static void recycle(@NonNull final SaveVolleyCompiler compiler) {
        compilers.set(compiler);
    }


    @NonNull
    static <RType> SaveVolleyCompilerStates.VRequestState<RType> request(
        @NonNull final String url) {
        checkNotNull(Looper.myLooper());
        checkNotNull(url, "The url was null, url == null.");
        SaveVolleyCompiler compiler = compilers.get();
        if (compiler == null) {
            compiler = new SaveVolleyCompiler();
        } else {
            compilers.set(null);
        }
        return compiler.url(url);
    }


    /*****************
     * VRequestState *
     *****************/

    @NonNull @Override public SaveVolleyCompilerStates.VRequestState<RType> url(
        @NonNull String url) {
        this.requestUrl = url;
        return this;
    }


    @NonNull @Override
    public SaveVolleyCompilerStates.VRequestState<RType> method(@Nullable Integer method) {
        this.requestMethod = method != null ? method : Method.GET;
        return this;
    }


    @NonNull @Override
    public SaveVolleyCompilerStates.VRequestState<RType> addParam(
        @NonNull String key, @NonNull String value) {
        if (this.requestParams == null) {
            this.requestParams = new HashMap<>();
        }
        this.requestParams.put(key, value);
        return this;
    }


    @NonNull @Override
    public SaveVolleyCompilerStates.VRequestState<RType> resetParams(
        @Nullable Map<String, String> params) {
        this.requestParams = params;
        return this;
    }


    @NonNull @Override
    public SaveVolleyCompilerStates.VRequestState<RType> parseStyle(
        @Nullable @SaveVolleyCompilerStates.ParseStyle Integer parseStyle) {
        this.requestParseStyle = parseStyle != null ? parseStyle : SaveVolleyCompilerStates.GSON;
        return this;
    }


    @NonNull @Override
    public SaveVolleyCompilerStates.VRequestState<RType> classOf(@Nullable Class<RType> classOf) {
        this.requestClassOf = classOf;
        return this;
    }


    @NonNull @Override public SaveVolleyCompilerStates.VRequestQueue<RType> createRequest() {
        if (this.requestMethod != Method.GET) {
            checkNotNull(this.requestParams,
                "The params of request was null, params == null.");
            checkArgument(this.requestParams.isEmpty(),
                "The params map of request was empty, requestParams.isEmpty().");
            this.request.setParams(this.requestParams);
        }
        switch (this.requestParseStyle) {
            case SaveVolleyCompilerStates.GSON:
                checkNotNull(this.requestClassOf,
                    "The parse style of response was null, requestTypeClass == null.");
                this.request = new HurlGsonReservoirRequest<>(this.requestMethod, this.requestUrl,
                    this.requestClassOf);
                break;
            case SaveVolleyCompilerStates.JSON_OBJECT:
                this.request = new HurlJsonReservoirRequest(this.requestMethod, this.requestUrl);
                break;
            case SaveVolleyCompilerStates.JSON_ARRAY:
                this.request = new HurlJsonArrayReservoirRequest(this.requestMethod,
                    this.requestUrl);
                break;
        }
        return this;
    }


    /*****************
     * VRequestQueue *
     *****************/

    @Override
    public SaveVolleyCompilerStates.VTermination<RType> context(@NonNull Context context) {
        checkNotNull(this.request, "The request was null, request == null");
        requestQueue(context).add(this.request);
        if (this.request instanceof HurlGsonReservoirRequest) {
            this.reservoir = ((HurlGsonReservoirRequest) this.request).getReservoir();
        } else if (this.request instanceof HurlJsonReservoirRequest) {
            this.reservoir = ((HurlJsonReservoirRequest) this.request).getReservoir();
        } else if (this.request instanceof HurlJsonArrayReservoirRequest) {
            this.reservoir = ((HurlJsonArrayReservoirRequest) this.request).getReservoir();
        }
        return this;
    }


    /*****************
     * VTermination *
     *****************/

    @Override public SaveVolley compile() {
        SaveVolley saveVolley = new SaveVolley(this.requestMethod, this.requestUrl,
            this.requestParseStyle, this.requestClassOf, this.request, this.reservoir);
        recycle(this);
        return saveVolley;
    }

}
