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

package com.camnter.easyvolley.network.okhttp3.adapter;

import com.camnter.easyvolley.network.adapter.core.EasyProtocolVersionAdapter;
import com.camnter.easyvolley.network.core.http.EasyProtocolVersion;
import okhttp3.Protocol;

/**
 * Description：OkHttp3ProtocolVersionAdapter
 * Created by：CaMnter
 * Time：2016-05-27 16:16
 */
public class OkHttp3ProtocolVersionAdapter implements EasyProtocolVersionAdapter<okhttp3.Response> {

    private volatile static OkHttp3ProtocolVersionAdapter instance = null;


    private OkHttp3ProtocolVersionAdapter() {
    }


    public static OkHttp3ProtocolVersionAdapter getInstance() {
        if (instance == null) {
            synchronized (OkHttp3ProtocolVersionAdapter.class) {
                if (instance == null) instance = new OkHttp3ProtocolVersionAdapter();
            }
        }
        return instance;
    }


    @Override public EasyProtocolVersion adaptiveProtocolVersion(okhttp3.Response response) {
        if (response == null || response.protocol() == null) return null;
        Protocol protocol = response.protocol();
        if (protocol == Protocol.HTTP_1_0) {
            return new EasyProtocolVersion("HTTP", 1, 0);
        } else if (protocol == Protocol.HTTP_1_1) {
            return new EasyProtocolVersion("HTTP", 1, 1);
        } else if (protocol == Protocol.SPDY_3) {
            return new EasyProtocolVersion("SPDY", 3, 1);
        } else if (protocol == Protocol.HTTP_2) {
            return new EasyProtocolVersion("HTTP", 2, 0);
        } else {
            throw new IllegalStateException("Unknown protocol type.");
        }
    }
}
