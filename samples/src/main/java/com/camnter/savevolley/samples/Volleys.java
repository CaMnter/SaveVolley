package com.camnter.savevolley.samples;

import android.content.Context;
import com.camnter.savevolley.okhttp3.volley.RequestQueue;
import com.camnter.savevolley.okhttp3.volley.toolbox.Volley;
import com.savevolley.okhttp3.agera.GsonReservoirRequest;

/**
 * Description：Volleys
 * Created by：CaMnter
 * Time：2016-06-22 18:59
 */

public class Volleys {

    private static final ThreadLocal<RequestQueue> requestQueueLocal = new ThreadLocal<>();


    public static RequestQueue requestQueue(Context context) {
        if (requestQueueLocal.get() == null) {
            requestQueueLocal.set(Volley.newRequestQueue(context));
        }
        return requestQueueLocal.get();
    }


    public static <T> GsonReservoirRequest<T> okHttp3ReservoirRequest(String url, Class<T> clazz) {
        return new GsonReservoirRequest<>(url, clazz);
    }

}
