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

package com.camnter.savevolley.hurl.agera.fastjson;

import android.content.Context;
import android.os.Looper;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.camnter.savevolley.hurl.Request;
import com.camnter.savevolley.hurl.RequestQueue;
import com.camnter.savevolley.hurl.agera.core.HurlReservoirRequest;
import com.camnter.savevolley.hurl.agera.core.SaveVolley;
import com.camnter.savevolley.hurl.agera.fastjson.request.HurlReservoirRequests;
import com.camnter.savevolley.hurl.toolbox.Volley;
import com.google.android.agera.Reservoir;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;
import java.util.Map;

import static com.camnter.savevolley.hurl.agera.core.Preconditions.checkArgument;
import static com.camnter.savevolley.hurl.agera.core.Preconditions.checkNotNull;
import static com.camnter.savevolley.hurl.agera.fastjson.SaveVolleyCompilerStates.FASTJSON;
import static com.camnter.savevolley.hurl.agera.fastjson.SaveVolleyCompilerStates.JSON_ARRAY;
import static com.camnter.savevolley.hurl.agera.fastjson.SaveVolleyCompilerStates.JSON_OBJECT;
import static com.camnter.savevolley.hurl.agera.fastjson.SaveVolleyCompilerStates.ParseStyle;
import static com.google.android.agera.Preconditions.checkState;

/**
 * Description：SaveVolleyCompiler
 * Created by：CaMnter
 * Time：2016-06-29 17:04
 */

class SaveVolleyCompiler<RType> implements
    SaveVolleyCompilerStates.VRequestState<RType>,
    SaveVolleyCompilerStates.VRequestQueue<RType>,
    SaveVolleyCompilerStates.VTermination<RType> {

    @IntDef({ IDLE, REQUEST, REQUEST_QUEUE, TERMINATION, PRODUCT })
    @Retention(RetentionPolicy.SOURCE)
    private @interface Expect {}


    private static final int IDLE = 260;
    private static final int REQUEST = 261;
    private static final int REQUEST_QUEUE = 262;
    private static final int TERMINATION = 263;
    private static final int PRODUCT = 264;

    private int requestMethod;
    private String requestUrl;
    private int requestParseStyle;
    private Class requestClassOf;
    private Map<String, String> requestParams;

    @Expect
    private int expect;

    private Request<?> request;

    private Reservoir reservoir;

    private static final ThreadLocal<SaveVolleyCompiler> compilers = new ThreadLocal<>();
    private static final ThreadLocal<RequestQueue> queue = new ThreadLocal<>();


    private SaveVolleyCompiler() {}


    private void checkExpect(@Expect final int accept) {
        checkState(expect == accept, "Unexpected compiler state");
    }


    private static RequestQueue requestQueue(@NonNull final Context context) {
        if (queue.get() == null) {
            queue.set(Volley.newRequestQueue(context));
        }
        return queue.get();
    }


    private static void recycle(@NonNull final SaveVolleyCompiler compiler) {
        compilers.set(compiler);
    }


    @SuppressWarnings("unchecked")
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
        checkExpect(REQUEST);
        this.requestMethod = method != null ? method : Request.Method.GET;
        this.expect = REQUEST;
        return this;
    }


    @NonNull @Override
    public SaveVolleyCompilerStates.VRequestState<RType> addParam(
        @NonNull final String key, @NonNull final String value) {
        checkExpect(REQUEST);
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
        checkExpect(REQUEST);
        this.requestParams = params;
        this.expect = REQUEST;
        return this;
    }


    @NonNull @Override
    public SaveVolleyCompilerStates.VRequestState<RType> parseStyle(
        @Nullable @ParseStyle final Integer parseStyle) {
        checkExpect(REQUEST);
        this.requestParseStyle = parseStyle != null ? parseStyle : FASTJSON;
        this.expect = REQUEST;
        return this;
    }


    @NonNull @Override
    public SaveVolleyCompilerStates.VRequestState<RType> classOf(
        @Nullable final Class<RType> classOfT) {
        checkExpect(REQUEST);
        this.requestClassOf = classOfT;
        this.expect = REQUEST;
        return this;
    }


    @NonNull @Override public SaveVolleyCompilerStates.VRequestQueue<RType> createRequest() {
        checkExpect(REQUEST);
        if (this.requestMethod != Request.Method.GET) {
            checkNotNull(this.requestParams,
                "The params of request was null, params == null.");
            checkArgument(this.requestParams.isEmpty(),
                "The params map of request was empty, requestParams.isEmpty().");
            this.request.setParams(this.requestParams);
        }
        switch (this.requestParseStyle) {
            case FASTJSON:
                checkNotNull(this.requestClassOf,
                    "The parse style of response was null, requestTypeClass == null.");
                this.request = HurlReservoirRequests.fastjsonReservoirRequest(this.requestMethod,
                    this.requestUrl,
                    this.requestClassOf);
                break;
            case JSON_OBJECT:
                this.request = HurlReservoirRequests.jsonReservoirRequest(this.requestMethod,
                    this.requestUrl);
                break;
            case JSON_ARRAY:
                this.request = HurlReservoirRequests.jsonArrayReservoirRequest(
                    this.requestMethod,
                    this.requestUrl);
                break;
        }
        this.expect = REQUEST_QUEUE;
        return this;
    }


    /*****************
     * VRequestQueue *
     *****************/

    @NonNull @Override
    public SaveVolleyCompilerStates.VTermination<RType> context(@NonNull final Context context) {
        checkExpect(REQUEST_QUEUE);
        checkNotNull(this.request, "The request was null, request == null");
        checkNotNull(context, "The context was null, context == null");
        requestQueue(context).add(this.request);
        if (this.request instanceof HurlReservoirRequest) {
            this.reservoir = ((HurlReservoirRequest) this.request).getReservoir();
        }
        this.expect = TERMINATION;
        return this;
    }


    /*****************
     * VTermination *
     *****************/

    @SuppressWarnings("unchecked")
    @NonNull
    @Override public SaveVolley compile() {
        checkExpect(TERMINATION);
        SaveVolley saveVolley = new SaveVolley(this.requestMethod, this.requestUrl,
            this.requestParseStyle, this.requestClassOf, this.request, this.reservoir);
        recycle(this);
        this.expect = PRODUCT;
        return saveVolley;
    }

}
