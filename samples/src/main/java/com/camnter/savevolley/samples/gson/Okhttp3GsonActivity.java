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

package com.camnter.savevolley.samples.gson;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import com.camnter.savevolley.okhttp3.gson.request.OkHttp3GsonRequest;
import com.camnter.savevolley.okhttp3.volley.RequestQueue;
import com.camnter.savevolley.okhttp3.volley.VolleyError;
import com.camnter.savevolley.okhttp3.volley.toolbox.Volley;
import com.camnter.savevolley.samples.R;
import com.camnter.savevolley.samples.bean.GankData;

/**
 * Description：Okhttp3GsonActivity
 * Created by：CaMnter
 * Time：2016-05-29 21:58
 */
public class Okhttp3GsonActivity extends AppCompatActivity {

    public static final String TEST_URL = "http://gank.io/api/data/Android/1/1";

    protected TextView getContentText;


    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_hurl);
        this.getContentText = (TextView) this.findViewById(R.id.get_content_text);

        this.initData();
    }


    protected void initData() {
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(new OkHttp3GsonRequest<GankData>(TEST_URL,
            GankData.class) {
            /**
             * Called when a response is received.
             */
            @Override public void onResponse(GankData response) {
                getContentText.setText(response.toString());
            }


            /**
             * Callback method that an error has been occurred with the
             * provided error code and optional user-readable message.
             */
            @Override public void onErrorResponse(VolleyError error) {
                Toast.makeText(Okhttp3GsonActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                Log.d("GsonRequest", error.getMessage());
            }
        });
    }

}
