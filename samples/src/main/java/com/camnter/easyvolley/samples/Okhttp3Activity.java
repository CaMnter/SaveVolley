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

package com.camnter.easyvolley.samples;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import com.camnter.easyvolley.okhttp3.volley.RequestQueue;
import com.camnter.easyvolley.okhttp3.volley.VolleyError;
import com.camnter.easyvolley.okhttp3.volley.toolbox.Volley;
import com.camnter.easyvolley.samples.request.OkHttp3GsonRequest;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class Okhttp3Activity extends AppCompatActivity {

    protected TextView mGetContentText;


    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp3_hurl);
        this.mGetContentText = (TextView) this.findViewById(R.id.get_content_text);

        this.initData();
    }


    protected void initData() {
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(new OkHttp3GsonRequest<GankData>("http://gank.io/api/data/Android/1/1",
                GankData.class) {
            /**
             * Called when a response is received.
             */
            @Override public void onResponse(GankData response) {
                mGetContentText.setText(response.toString());
            }


            /**
             * Callback method that an error has been occurred with the
             * provided error code and optional user-readable message.
             */
            @Override public void onErrorResponse(VolleyError error) {
                Toast.makeText(Okhttp3Activity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                Log.d("GsonRequest", error.getMessage());
            }
        });
    }


    public class GankData {
        private static final String TAG = "GankData";

        public boolean error;
        public ArrayList<GankResultData> results;


        @Override public String toString() {
            StringBuilder builder = new StringBuilder(TAG).append("\n\n");
            for (GankResultData result : results) {
                builder.append(result.toString());
                builder.append("\n\n");
            }
            return builder.toString();
        }
    }

    public class GankResultData {

        private static final String TAG = "GankResultData";

        @SerializedName("_id") public String id;
        public String createdAt;
        public String desc;
        public String publishedAt;
        public String source;
        public String type;
        public String url;
        public boolean used;
        public String who;


        @Override public String toString() {
            return TAG + "id: " +
                    this.id +
                    "\n" +
                    "createdAt: " +
                    this.createdAt +
                    "\n" +
                    "desc: " +
                    this.desc +
                    "\n" +
                    "publishedAt: " +
                    this.publishedAt +
                    "\n" +
                    "source: " +
                    this.source +
                    "\n" +
                    "type: " +
                    this.type +
                    "\n" +
                    "url: " +
                    this.url +
                    "\n" +
                    "used: " +
                    this.used +
                    "\n" +
                    "who: " +
                    this.who;
        }
    }
}
