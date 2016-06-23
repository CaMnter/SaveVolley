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

package com.camnter.savevolley.okhttp3.agera;

import android.content.Context;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.camnter.savevolley.okhttp3.volley.Request;
import com.camnter.savevolley.okhttp3.volley.Request.Method;
import com.camnter.savevolley.okhttp3.volley.RequestQueue;
import com.camnter.savevolley.okhttp3.volley.toolbox.Volley;
import com.google.android.agera.Reservoir;
import java.util.HashMap;
import java.util.Map;

import static com.camnter.savevolley.okhttp3.agera.Preconditions.checkArgument;
import static com.camnter.savevolley.okhttp3.agera.Preconditions.checkNotNull;

/**
 * Description：SaveVolleyCompiler
 * Created by：CaMnter
 * Time：2016-06-23 13:52
 */

public final class SaveVolleyCompiler<RType> implements
    SaveVolleyCompilerStates.VRequestState<RType>,
    SaveVolleyCompilerStates.VRequestQueue {

    private int requestMethod;
    @NonNull
    private String requestUrl;
    private int requestParseStyle;
    private Class requestTypeClass;
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


    @NonNull @Override public SaveVolleyCompiler<RType> url(@NonNull String url) {
        this.requestUrl = url;
        return this;
    }


    @NonNull
    static <RType> SaveVolleyCompiler<RType> request(
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


    @NonNull @Override public SaveVolleyCompiler<RType> method(@Nullable Integer method) {
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
    public SaveVolleyCompiler<RType> parseStyle(
        @Nullable @SaveVolleyCompilerStates.ParseStyle Integer parseStyle) {
        this.requestParseStyle = parseStyle != null ? parseStyle : SaveVolleyCompilerStates.GSON;
        return this;
    }


    @NonNull @Override public SaveVolleyCompiler<RType> jsonBean(@Nullable Class clazz) {
        this.requestTypeClass = clazz;
        return this;
    }


    @NonNull @Override public SaveVolleyCompiler<RType> create() {
        if (this.requestMethod != Method.GET) {
            checkNotNull(this.requestParams,
                "The params of request was null, params == null.");
            checkArgument(this.requestParams.isEmpty(),
                "The params map of request was empty, requestParams.isEmpty().");
            this.request.setParams(this.requestParams);
        }
        switch (this.requestParseStyle) {
            case SaveVolleyCompilerStates.GSON:
                checkNotNull(this.requestTypeClass,
                    "The parse style of response was null, requestTypeClass == null.");
                this.request = new GsonReservoirRequest<>(this.requestMethod, this.requestUrl,
                    this.requestTypeClass);
                break;
            case SaveVolleyCompilerStates.JSON_OBJECT:
                this.request = new JsonReservoirRequest(this.requestMethod, this.requestUrl);
                break;
            case SaveVolleyCompilerStates.JSON_ARRAY:
                this.request = new JsonArrayReservoirRequest(this.requestMethod,
                    this.requestUrl);
                break;
        }
        return this;
    }


    @Override public SaveVolley execute(@NonNull Context context) {
        checkNotNull(this.request, "The request was null, request == null");
        requestQueue(context).add(this.request);
        if (this.request instanceof GsonReservoirRequest) {
            this.reservoir = ((GsonReservoirRequest) this.request).getReservoir();
        } else if (this.request instanceof JsonReservoirRequest) {
            this.reservoir = ((JsonReservoirRequest) this.request).getReservoir();
        } else if (this.request instanceof JsonArrayReservoirRequest) {
            this.reservoir = ((JsonArrayReservoirRequest) this.request).getReservoir();
        }
        SaveVolley saveVolley = new SaveVolley(this.requestMethod, this.requestUrl,
            this.requestParseStyle, this.requestTypeClass, this.request, this.reservoir);
        recycle(this);
        return saveVolley;
    }

}
