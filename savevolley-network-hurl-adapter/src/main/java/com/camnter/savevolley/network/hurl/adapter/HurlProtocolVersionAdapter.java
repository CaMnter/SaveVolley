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

import com.camnter.savevolley.network.adapter.core.SaveProtocolVersionAdapter;
import com.camnter.savevolley.network.core.http.SaveProtocolVersion;

/**
 * Description：HurlProtocolVersionAdapter
 * Created by：CaMnter
 * Time：2016-05-29 22:53
 */
public class HurlProtocolVersionAdapter implements SaveProtocolVersionAdapter {

    private volatile static HurlProtocolVersionAdapter instance = null;


    private HurlProtocolVersionAdapter() {
    }


    public static HurlProtocolVersionAdapter getInstance() {
        if (instance == null) {
            synchronized (HurlProtocolVersionAdapter.class) {
                if (instance == null) instance = new HurlProtocolVersionAdapter();
            }
        }
        return instance;
    }


    @Override public SaveProtocolVersion adaptiveProtocolVersion(Object o) {
        return new SaveProtocolVersion("HTTP", 1, 1);
    }
}
