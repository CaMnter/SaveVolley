package com.camnter.savevolley.samples.fastjson;

import android.util.Log;
import android.widget.Toast;
import com.camnter.savevolley.hurl.RequestQueue;
import com.camnter.savevolley.hurl.VolleyError;
import com.camnter.savevolley.hurl.fastjson.request.HurlFastjsonRequest;
import com.camnter.savevolley.hurl.toolbox.Volley;
import com.camnter.savevolley.samples.bean.GankData;
import com.camnter.savevolley.samples.gson.Okhttp3GsonActivity;

/**
 * Description：HurlFastJsonActivity
 * Created by：CaMnter
 * Time：2016-06-27 13:08
 */

public class HurlFastjsonActivity extends Okhttp3GsonActivity {
    @Override protected void initData() {
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(new HurlFastjsonRequest<GankData>(TEST_URL,
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
                Toast.makeText(HurlFastjsonActivity.this, error.getMessage(), Toast.LENGTH_LONG)
                    .show();
                Log.d("GsonRequest", error.getMessage());
            }
        });
    }
}
