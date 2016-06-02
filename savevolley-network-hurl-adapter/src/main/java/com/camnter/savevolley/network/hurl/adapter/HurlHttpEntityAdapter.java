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

package com.camnter.savevolley.network.hurl.adapter;

import com.camnter.savevolley.network.adapter.core.SaveHttpEntityAdapter;
import com.camnter.savevolley.network.core.http.SaveHttpEntity;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

/**
 * Description：HurlHttpEntityAdapter
 * Created by：CaMnter
 * Time：2016-05-29 22:59
 */
public class HurlHttpEntityAdapter implements SaveHttpEntityAdapter<HttpURLConnection> {

    private volatile static HurlHttpEntityAdapter instance = null;


    private HurlHttpEntityAdapter() {
    }


    public static HurlHttpEntityAdapter getInstance() {
        if (instance == null) {
            synchronized (HurlHttpEntityAdapter.class) {
                if (instance == null) instance = new HurlHttpEntityAdapter();
            }
        }
        return instance;
    }


    @Override public SaveHttpEntity adaptiveEntity(HttpURLConnection httpURLConnection) {
        SaveHttpEntity entity = new SaveHttpEntity();
        InputStream inputStream;
        try {
            inputStream = httpURLConnection.getInputStream();
        } catch (IOException ioe) {
            inputStream = httpURLConnection.getErrorStream();
        }
        entity.setContent(inputStream);
        entity.setContentLength(httpURLConnection.getContentLength());
        entity.setContentEncoding(httpURLConnection.getContentEncoding());
        entity.setContentType(httpURLConnection.getContentType());
        return entity;
    }
}
