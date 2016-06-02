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

import com.camnter.savevolley.network.adapter.core.EasyProtocolVersionAdapter;
import com.camnter.savevolley.network.adapter.core.EasyStatusLineAdapter;
import com.camnter.savevolley.network.core.http.EasyStatusLine;
import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * Description：HurlStatusLineAdapter
 * Created by：CaMnter
 * Time：2016-05-29 22:36
 */
public class HurlStatusLineAdapter implements EasyStatusLineAdapter<HttpURLConnection> {

    private volatile static HurlStatusLineAdapter instance = null;


    private HurlStatusLineAdapter() {
    }


    public static HurlStatusLineAdapter getInstance() {
        if (instance == null) {
            synchronized (HurlStatusLineAdapter.class) {
                if (instance == null) instance = new HurlStatusLineAdapter();
            }
        }
        return instance;
    }


    @Override
    public EasyStatusLine adaptiveStatusLine(EasyProtocolVersionAdapter<HttpURLConnection> easyProtocolVersionAdapter, HttpURLConnection httpURLConnection) {
        try {
            return new EasyStatusLine(
                    easyProtocolVersionAdapter.adaptiveProtocolVersion(httpURLConnection),
                    httpURLConnection.getResponseCode(), httpURLConnection.getResponseMessage());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
