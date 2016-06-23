package com.savevolley.okhttp3.agera;

import android.support.annotation.NonNull;
import com.camnter.savevolley.okhttp3.volley.Request;
import com.google.android.agera.Reservoir;

/**
 * Description：SaveVolley
 * Created by：CaMnter
 * Time：2016-06-23 16:46
 */

public class SaveVolley {
    private final int requestMethod;
    @NonNull
    private final String requestUrl;
    private final int requestParseStyle;
    private final Class requestTypeClass;

    private final Request<?> request;
    private final Reservoir<Object> reservoir;


    public SaveVolley(int requestMethod, @NonNull
        String requestUrl, int requestParseStyle, Class requestTypeClass, Request<?> request, Reservoir<Object> reservoir) {
        this.requestMethod = requestMethod;
        this.requestUrl = requestUrl;
        this.requestParseStyle = requestParseStyle;
        this.requestTypeClass = requestTypeClass;
        this.request = request;
        this.reservoir = reservoir;
    }


    public int getRequestMethod() {
        return requestMethod;
    }


    @NonNull public String getRequestUrl() {
        return requestUrl;
    }


    public int getRequestParseStyle() {
        return requestParseStyle;
    }


    public Class getRequestTypeClass() {
        return requestTypeClass;
    }


    public Request<?> getRequest() {
        return request;
    }


    public Reservoir<Object> getReservoir() {
        return reservoir;
    }
}
