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

import android.util.Log;
import android.widget.Toast;
import com.camnter.savevolley.hurl.RequestQueue;
import com.camnter.savevolley.hurl.VolleyError;
import com.camnter.savevolley.hurl.toolbox.Volley;
import com.camnter.savevolley.samples.request.HurlGsonRequest;

/**
 * Description：HurlActivity
 * Created by：CaMnter
 * Time：2016-05-29 22:10
 */
public class HurlActivity extends Okhttp3Activity {
    @Override protected void initData() {
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(new HurlGsonRequest<GankData>("http://gank.io/api/data/Android/1/1",
                GankData.class) {
            @Override public void onResponse(GankData response) {
                mGetContentText.setText(response.toString());
            }


            @Override public void onErrorResponse(VolleyError error) {
                Toast.makeText(HurlActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                Log.d("GsonRequest", error.getMessage());
            }
        });
    }
}
