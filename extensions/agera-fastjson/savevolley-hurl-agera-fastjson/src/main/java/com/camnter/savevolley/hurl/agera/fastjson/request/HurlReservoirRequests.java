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

import com.camnter.savevolley.hurl.agera.core.HurlJsonArrayReservoirRequest;
import com.camnter.savevolley.hurl.agera.core.HurlJsonReservoirRequest;

/**
 * Description：HurlReservoirRequests
 * Created by：CaMnter
 * Time：2016-06-29 17:05
 */

public class HurlReservoirRequests {

    public static <T> HurlFastjsonReservoirRequest<T> gsonReservoirRequest(int method, String url, Class<T> clazz) {
        return new HurlFastjsonReservoirRequest<>(method, url, clazz);
    }


    public static HurlJsonReservoirRequest jsonReservoirRequest(int method, String url) {
        return new HurlJsonReservoirRequest(method, url);
    }


    public static HurlJsonArrayReservoirRequest jsonArrayReservoirRequest(int method, String url) {
        return new HurlJsonArrayReservoirRequest(method, url);
    }

}
