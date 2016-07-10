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

package com.camnter.savevolley.hurl.agera.core;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.camnter.savevolley.hurl.Request;
import com.google.android.agera.Reservoir;

/**
 * Description：SaveVolley
 * Created by：CaMnter
 * Time：2016-06-29 17:02
 */

public class SaveVolley {

    private final int requestMethod;

    @NonNull
    private final String requestUrl;

    private final int requestParseStyle;

    @Nullable
    private final Class requestClassOf;

    @NonNull
    private final Request<?> request;

    @NonNull
    private final Reservoir<Object> reservoir;


    public SaveVolley(final int requestMethod,
                      @NonNull final String requestUrl,
                      final int requestParseStyle,
                      @Nullable final Class requestClassOf,
                      @NonNull final Request<?> request,
                      @NonNull final Reservoir<Object> reservoir) {
        this.requestMethod = requestMethod;
        this.requestUrl = requestUrl;
        this.requestParseStyle = requestParseStyle;
        this.requestClassOf = requestClassOf;
        this.request = request;
        this.reservoir = reservoir;
    }


    public int getRequestMethod() {
        return requestMethod;
    }


    @NonNull public String getRequestUrl() {
        return requestUrl;
    }


    public int getRequestParseStyle() {
        return requestParseStyle;
    }


    @Nullable public Class getRequestClassOf() {
        return requestClassOf;
    }


    @NonNull public Request<?> getRequest() {
        return request;
    }


    @NonNull public Reservoir<Object> getReservoir() {
        return reservoir;
    }
}
