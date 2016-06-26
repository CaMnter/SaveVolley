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
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.camnter.savevolley.okhttp3.agera.gson.request.OkHttp3GsonReservoirRequest;
import com.camnter.savevolley.okhttp3.agera.gson.request.OkHttp3JsonArrayReservoirRequest;
import com.camnter.savevolley.okhttp3.agera.gson.request.OkHttp3JsonReservoirRequest;
import com.camnter.savevolley.okhttp3.volley.Request;
import com.camnter.savevolley.okhttp3.volley.Request.Method;
import com.camnter.savevolley.okhttp3.volley.RequestQueue;
import com.camnter.savevolley.okhttp3.volley.toolbox.Volley;
import com.google.android.agera.Reservoir;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;
import java.util.Map;

import static com.camnter.savevolley.okhttp3.agera.Preconditions.checkArgument;
import static com.camnter.savevolley.okhttp3.agera.Preconditions.checkNotNull;
import static com.google.android.agera.Preconditions.checkState;

/**
 * Description：SaveVolleyCompiler
 * Created by：CaMnter
 * Time：2016-06-27 00:03
 */

public final class SaveVolleyCompiler<RType> implements
    SaveVolleyCompilerStates.VRequestState<RType>,
    SaveVolleyCompilerStates.VRequestQueue<RType>,
    SaveVolleyCompilerStates.VTermination<RType> {

    @IntDef({ IDLE, REQUEST, REQUEST_QUEUE, TERMINATION })
    @Retention(RetentionPolicy.SOURCE)
    private @interface Expect {}


    private static final int IDLE = 260;
    private static final int REQUEST = 261;
    private static final int REQUEST_QUEUE = 262;
    private static final int TERMINATION = 263;

    private int requestMethod;
    @NonNull
    private String requestUrl;
    private int requestParseStyle;
    private Class requestClassOf;
    private Map<String, String> requestParams;

    @Expect
    private int expect;

    private Request<?> request;

    private Reservoir<Object> reservoir;

    private static final ThreadLocal<SaveVolleyCompiler> compilers = new ThreadLocal<>();
    private static final ThreadLocal<RequestQueue> queue = new ThreadLocal<>();


    private SaveVolleyCompiler() {}


    private void checkExpect(@Expect final int accept) {
        checkState(expect == accept, "Unexpected compiler state");
    }


    public static RequestQueue requestQueue(final Context context) {
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
        @NonNull final String url) {
        checkNotNull(url, "The url was null, url == null.");
        this.requestUrl = url;
        this.expect = REQUEST;
        return this;
    }


    @NonNull @Override
    public SaveVolleyCompilerStates.VRequestState<RType> method(@Nullable final Integer method) {
        this.requestMethod = method != null ? method : Method.GET;
        this.expect = REQUEST;
        return this;
    }


    @NonNull @Override
    public SaveVolleyCompilerStates.VRequestState<RType> addParam(
        @NonNull final String key, @NonNull final String value) {
        checkNotNull(key, "The key was null, key == null.");
        checkNotNull(value, "The value was null, value == null.");
        if (this.requestParams == null) {
            this.requestParams = new HashMap<>();
        }
        this.requestParams.put(key, value);
        this.expect = REQUEST;
        return this;
    }


    @NonNull @Override
    public SaveVolleyCompilerStates.VRequestState<RType> resetParams(
        @Nullable final Map<String, String> params) {
        this.requestParams = params;
        this.expect = REQUEST;
        return this;
    }


    @NonNull @Override
    public SaveVolleyCompilerStates.VRequestState<RType> parseStyle(
        @Nullable @SaveVolleyCompilerStates.ParseStyle final Integer parseStyle) {
        this.requestParseStyle = parseStyle != null ? parseStyle : SaveVolleyCompilerStates.GSON;
        this.expect = REQUEST;
        return this;
    }


    @NonNull @Override
    public SaveVolleyCompilerStates.VRequestState<RType> classOf(
        @Nullable final Class<RType> classOfT) {
        this.requestClassOf = classOfT;
        this.expect = REQUEST;
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
                this.request = new OkHttp3GsonReservoirRequest<>(this.requestMethod,
                    this.requestUrl,
                    this.requestClassOf);
                break;
            case SaveVolleyCompilerStates.JSON_OBJECT:
                this.request = new OkHttp3JsonReservoirRequest(this.requestMethod, this.requestUrl);
                break;
            case SaveVolleyCompilerStates.JSON_ARRAY:
                this.request = new OkHttp3JsonArrayReservoirRequest(this.requestMethod,
                    this.requestUrl);
                break;
        }
        this.expect = REQUEST;
        return this;
    }


    /*****************
     * VRequestQueue *
     *****************/

    @Override
    public SaveVolleyCompilerStates.VTermination<RType> context(@NonNull final Context context) {
        checkNotNull(this.request, "The request was null, request == null");
        checkNotNull(context, "The context was null, context == null");
        requestQueue(context).add(this.request);
        if (this.request instanceof OkHttp3GsonReservoirRequest) {
            this.reservoir = ((OkHttp3GsonReservoirRequest) this.request).getReservoir();
        } else if (this.request instanceof OkHttp3JsonReservoirRequest) {
            this.reservoir = ((OkHttp3JsonReservoirRequest) this.request).getReservoir();
        } else if (this.request instanceof OkHttp3JsonArrayReservoirRequest) {
            this.reservoir = ((OkHttp3JsonArrayReservoirRequest) this.request).getReservoir();
        }
        this.expect = REQUEST_QUEUE;
        return this;
    }


    /*****************
     * VTermination *
     *****************/

    @Override public SaveVolley compile() {
        SaveVolley saveVolley = new SaveVolley(this.requestMethod, this.requestUrl,
            this.requestParseStyle, this.requestClassOf, this.request, this.reservoir);
        recycle(this);
        this.expect = TERMINATION;
        return saveVolley;
    }

}
