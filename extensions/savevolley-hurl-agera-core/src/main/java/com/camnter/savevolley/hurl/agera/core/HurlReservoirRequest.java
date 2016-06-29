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

import com.camnter.savevolley.hurl.Request;
import com.camnter.savevolley.hurl.Response;
import com.google.android.agera.Reservoir;
import com.google.android.agera.Reservoirs;

/**
 * Description：HurlReservoirRequest
 * Created by：CaMnter
 * Time：2016-06-29 16:54
 */

public abstract class HurlReservoirRequest<T> extends Request<T> {

    protected final Reservoir<Object> mReservoir;


    public Reservoir<Object> getReservoir() {
        return this.mReservoir;
    }


    /**
     * Creates a new request with the given method (one of the values from {@link Method}),
     * URL, and error listener.  Note that the normal response listener is not provided here as
     * delivery of responses is provided by subclasses, who have a better idea of how to deliver
     * an already-parsed response.
     */
    public HurlReservoirRequest(int method, String url, Response.ErrorListener listener) {
        super(method, url, listener);
        this.mReservoir = Reservoirs.reservoir();
    }
}

