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

import com.camnter.savevolley.network.adapter.core.SaveHeaderAdapter;
import com.camnter.savevolley.network.core.http.SaveHeader;
import com.camnter.savevolley.network.core.http.SaveHttpResponse;
import okhttp3.Headers;
import okhttp3.Response;

/**
 * Description：OkHttp3HeaderAdapter
 * Created by：CaMnter
 * Time：2016-05-27 16:46
 */
public class OkHttp3HeaderAdapter implements SaveHeaderAdapter<Response> {

    private volatile static OkHttp3HeaderAdapter instance = null;


    private OkHttp3HeaderAdapter() {
    }


    public static OkHttp3HeaderAdapter getInstance() {
        if (instance == null) {
            synchronized (OkHttp3HeaderAdapter.class) {
                if (instance == null) instance = new OkHttp3HeaderAdapter();
            }
        }
        return instance;
    }


    @Override public void adaptiveHeader(SaveHttpResponse saveHttpResponse, Response response) {
        Headers headers;
        if (response == null || (headers = response.headers()) == null) return;

        for (int i = 0, len = headers.size(); i < len; i++) {
            final String name = headers.name(i), value = headers.value(i);
            if (name != null) {
                saveHttpResponse.addHeader(new SaveHeader(name, value));
            }
        }
    }
}
