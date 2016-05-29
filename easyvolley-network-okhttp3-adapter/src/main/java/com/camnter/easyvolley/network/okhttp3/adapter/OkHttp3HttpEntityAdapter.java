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

import com.camnter.easyvolley.network.adapter.core.EasyHttpEntityAdapter;
import com.camnter.easyvolley.network.core.http.EasyHttpEntity;
import okhttp3.MediaType;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Description：OkHttp3HttpEntityAdapter
 * Created by：CaMnter
 * Time：2016-05-27 16:43
 */
public class OkHttp3HttpEntityAdapter implements EasyHttpEntityAdapter<okhttp3.Response> {

    private volatile static OkHttp3HttpEntityAdapter instance = null;


    private OkHttp3HttpEntityAdapter() {
    }


    public static OkHttp3HttpEntityAdapter getInstance() {
        if (instance == null) {
            synchronized (OkHttp3StatusLineAdapter.class) {
                if (instance == null) instance = new OkHttp3HttpEntityAdapter();
            }
        }
        return instance;
    }


    @Override public EasyHttpEntity adaptiveEntity(Response response) {
        EasyHttpEntity entity = new EasyHttpEntity();
        ResponseBody body = response.body();
        if (body != null) {
            entity.setContent(body.byteStream());
            entity.setContentLength(body.contentLength());
            entity.setContentEncoding(response.header("Content-Encoding"));
            MediaType contentType = body.contentType();
            entity.setContentType(contentType != null ? contentType.type() : null);
        }
        return entity;
    }
}
