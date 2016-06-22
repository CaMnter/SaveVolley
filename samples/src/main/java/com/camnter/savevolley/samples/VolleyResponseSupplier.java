package com.camnter.savevolley.samples;

import android.content.Context;
import android.support.annotation.NonNull;
import com.camnter.savevolley.okhttp3.volley.RequestQueue;
import com.camnter.savevolley.okhttp3.volley.Response;
import com.camnter.savevolley.okhttp3.volley.VolleyError;
import com.camnter.savevolley.okhttp3.volley.toolbox.Volley;
import com.camnter.savevolley.samples.bean.GankData;
import com.camnter.savevolley.samples.request.OkHttp3GsonRequest;
import com.google.android.agera.Result;
import com.google.android.agera.Supplier;

import static com.camnter.savevolley.samples.Okhttp3Activity.TEST_URL;

/**
 * Description：
 * Created by：CaMnter
 * Time：2016-06-22 18:43
 */

public class VolleyResponseSupplier<T> extends OkHttp3GsonRequest<T>
    implements Supplier<Result<Response<T>>> {

    private Context context;
    private T result;
    private VolleyError error;



    public VolleyResponseSupplier(String url, Class<T> clazz) {
        super(url, clazz);
    }


    public VolleyResponseSupplier(int method, String url, Class<T> clazz) {
        super(method, url, clazz);
    }


    /**
     * Called when a response is received.
     */
    @Override public void onResponse(T response) {
        this.result = response;
    }


    /**
     * Callback method that an error has been occurred with the
     * provided error code and optional user-readable message.
     */
    @Override public void onErrorResponse(VolleyError error) {
        this.error = error;
    }


    /**
     * Returns an instance of the appropriate type. The returned object may or may not be a new
     * instance, depending on the implementation.
     */
    @NonNull @Override public Result<Response<T>> get() {
        RequestQueue queue = Volley.newRequestQueue(this.context);
        queue.add(new OkHttp3GsonRequest<GankData>(TEST_URL,
            GankData.class) {
            /**
             * Called when a response is received.
             */
            @Override public void onResponse(GankData response) {

            }


            /**
             * Callback method that an error has been occurred with the
             * provided error code and optional user-readable message.
             */
            @Override public void onErrorResponse(VolleyError error) {
            }
        });
        return null;
    }
}
