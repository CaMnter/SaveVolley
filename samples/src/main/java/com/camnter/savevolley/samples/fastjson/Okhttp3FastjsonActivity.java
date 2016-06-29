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

package com.camnter.savevolley.samples.fastjson;

import android.util.Log;
import android.widget.Toast;
import com.camnter.savevolley.okhttp3.fastjson.request.OkHttp3FastjsonRequest;
import com.camnter.savevolley.okhttp3.volley.RequestQueue;
import com.camnter.savevolley.okhttp3.volley.VolleyError;
import com.camnter.savevolley.okhttp3.volley.toolbox.Volley;
import com.camnter.savevolley.samples.BaseTestActivity;
import com.camnter.savevolley.samples.bean.GankData;

/**
 * Description：Okhttp3FastjsonActivity
 * Created by：CaMnter
 * Time：2016-06-27 15:04
 */

public class Okhttp3FastjsonActivity extends BaseTestActivity {
    @Override protected void initData() {
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(new OkHttp3FastjsonRequest<GankData>(TEST_URL, GankData.class) {
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
                Toast.makeText(Okhttp3FastjsonActivity.this,
                    error != null && error.getMessage() != null
                    ? error.getMessage()
                    : "No error message", Toast.LENGTH_LONG)
                    .show();
                Log.d("GsonRequest", error != null && error.getMessage() != null
                                     ? error.getMessage()
                                     : "No error message");
            }
        });
    }
}
