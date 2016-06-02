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

import com.camnter.savevolley.network.adapter.core.SaveHeaderAdapter;
import com.camnter.savevolley.network.core.http.SaveHeader;
import com.camnter.savevolley.network.core.http.SaveHttpResponse;
import com.camnter.savevolley.network.core.http.core.Header;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;

/**
 * Description：HurlHeaderAdapter
 * Created by：CaMnter
 * Time：2016-05-29 22:45
 */
public class HurlHeaderAdapter implements SaveHeaderAdapter<HttpURLConnection> {

    private volatile static HurlHeaderAdapter instance = null;


    private HurlHeaderAdapter() {
    }


    public static HurlHeaderAdapter getInstance() {
        if (instance == null) {
            synchronized (HurlHeaderAdapter.class) {
                if (instance == null) instance = new HurlHeaderAdapter();
            }
        }
        return instance;
    }


    @Override
    public void adaptiveHeader(SaveHttpResponse saveHttpResponse, HttpURLConnection httpURLConnection) {
        for (Map.Entry<String, List<String>> header : httpURLConnection.getHeaderFields()
                .entrySet()) {
            if (header.getKey() != null) {
                Header h = new SaveHeader(header.getKey(), header.getValue().get(0));
                saveHttpResponse.addHeader(h);
            }
        }
    }
}
