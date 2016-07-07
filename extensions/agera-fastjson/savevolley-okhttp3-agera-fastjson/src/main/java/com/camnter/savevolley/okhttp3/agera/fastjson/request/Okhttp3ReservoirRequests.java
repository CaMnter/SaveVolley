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

package com.camnter.savevolley.okhttp3.agera.fastjson.request;

import android.support.annotation.NonNull;
import com.camnter.savevolley.okhttp3.agera.core.OkHttp3JsonArrayReservoirRequest;
import com.camnter.savevolley.okhttp3.agera.core.OkHttp3JsonReservoirRequest;

/**
 * Description：Okhttp3ReservoirRequests
 * Created by：CaMnter
 * Time：2016-07-01 11:38
 */

public final class Okhttp3ReservoirRequests {

    @NonNull
    public static <T> OkHttp3FastjsonReservoirRequest<T> fastjsonReservoirRequest(
        int method,
        @NonNull String url,
        @NonNull Class<T> clazz) {
        return new OkHttp3FastjsonReservoirRequest<>(method, url, clazz);
    }


    @NonNull
    public static OkHttp3JsonReservoirRequest jsonReservoirRequest(int method,
                                                                   @NonNull String url) {
        return new OkHttp3JsonReservoirRequest(method, url);
    }


    @NonNull
    public static OkHttp3JsonArrayReservoirRequest jsonArrayReservoirRequest(int method,
                                                                             @NonNull String url) {
        return new OkHttp3JsonArrayReservoirRequest(method, url);
    }


    private Okhttp3ReservoirRequests() {}

}
