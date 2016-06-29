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

package com.camnter.savevolley.samples;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Description：Okhttp3GsonActivity
 * Created by：CaMnter
 * Time：2016-05-29 21:58
 */
public abstract class BaseTestActivity extends AppCompatActivity {

    public static final String TEST_URL = "http://gank.io/api/data/Android/1/1";

    protected TextView getContentText;


    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_hurl);
        this.getContentText = (TextView) this.findViewById(R.id.get_content_text);

        this.initData();
    }


    protected abstract void initData();

}
