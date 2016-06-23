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

import android.support.annotation.NonNull;
import com.camnter.savevolley.hurl.Request;
import com.google.android.agera.Reservoir;

/**
 * Description：SaveVolley
 * Created by：CaMnter
 * Time：2016-06-23 16:46
 */

public class SaveVolley {
    private final int requestMethod;
    @NonNull
    private final String requestUrl;
    private final int requestParseStyle;
    private final Class requestTypeClass;

    private final Request<?> request;
    private final Reservoir<Object> reservoir;


    public SaveVolley(int requestMethod, @NonNull
        String requestUrl, int requestParseStyle, Class requestTypeClass, Request<?> request, Reservoir<Object> reservoir) {
        this.requestMethod = requestMethod;
        this.requestUrl = requestUrl;
        this.requestParseStyle = requestParseStyle;
        this.requestTypeClass = requestTypeClass;
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


    public Class getRequestTypeClass() {
        return requestTypeClass;
    }


    public Request<?> getRequest() {
        return request;
    }


    public Reservoir<Object> getReservoir() {
        return reservoir;
    }
}
