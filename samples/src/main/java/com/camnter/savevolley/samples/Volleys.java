package com.camnter.savevolley.samples;

import android.content.Context;
import com.camnter.savevolley.okhttp3.volley.RequestQueue;
import com.camnter.savevolley.okhttp3.volley.Response;
import com.camnter.savevolley.okhttp3.volley.toolbox.Volley;
import com.google.android.agera.Result;
import com.google.android.agera.Supplier;

/**
 * Description：Volleys
 * Created by：CaMnter
 * Time：2016-06-22 18:59
 */

public class Volleys {
    private static final ThreadLocal<RequestQueue> requestQueueLocal = new ThreadLocal<>();


    public static RequestQueue getRequestQueue(Context context) {
        if (requestQueueLocal.get() == null) {
            requestQueueLocal.set(Volley.newRequestQueue(context));
        }
        return requestQueueLocal.get();
    }


    public static <T> Supplier<Result<Response<?>>> getVolleySupplier(Context context,T t) {
        return null;
    }
}
