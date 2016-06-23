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

import android.util.Log;
import android.widget.Toast;
import com.camnter.savevolley.hurl.RequestQueue;
import com.camnter.savevolley.hurl.VolleyError;
import com.camnter.savevolley.hurl.gson.request.HurlGsonRequest;
import com.camnter.savevolley.hurl.toolbox.Volley;
import com.camnter.savevolley.samples.bean.GankData;

/**
 * Description：HurlGsonActivity
 * Created by：CaMnter
 * Time：2016-05-29 22:10
 */
public class HurlGsonActivity extends Okhttp3GsonActivity {
    @Override protected void initData() {
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(new HurlGsonRequest<GankData>(TEST_URL,
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
                Toast.makeText(HurlGsonActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                Log.d("GsonRequest", error.getMessage());
            }
        });
    }
}
