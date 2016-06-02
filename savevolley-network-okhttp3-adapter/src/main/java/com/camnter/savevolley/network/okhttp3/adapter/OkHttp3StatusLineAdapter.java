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

package com.camnter.savevolley.network.okhttp3.adapter;

import com.camnter.savevolley.network.adapter.core.SaveProtocolVersionAdapter;
import com.camnter.savevolley.network.adapter.core.SaveStatusLineAdapter;
import com.camnter.savevolley.network.core.http.SaveStatusLine;
import okhttp3.Response;

/**
 * Description：OkHttp3StatusLineAdapter
 * Created by：CaMnter
 * Time：2016-05-27 16:24
 */
public class OkHttp3StatusLineAdapter implements SaveStatusLineAdapter<Response> {

    private volatile static OkHttp3StatusLineAdapter instance = null;


    private OkHttp3StatusLineAdapter() {
    }


    public static OkHttp3StatusLineAdapter getInstance() {
        if (instance == null) {
            synchronized (OkHttp3StatusLineAdapter.class) {
                if (instance == null) instance = new OkHttp3StatusLineAdapter();
            }
        }
        return instance;
    }


    @Override
    public SaveStatusLine adaptiveStatusLine(SaveProtocolVersionAdapter<Response> saveProtocolVersionAdapter, okhttp3.Response response) {
        return new SaveStatusLine(saveProtocolVersionAdapter.adaptiveProtocolVersion(response),
                response.code(), response.message());
    }
}
