package com.savevolley.okhttp3.agera;

import com.camnter.savevolley.okhttp3.volley.NetworkResponse;
import com.camnter.savevolley.okhttp3.volley.ParseError;
import com.camnter.savevolley.okhttp3.volley.Request;
import com.camnter.savevolley.okhttp3.volley.Response;
import com.camnter.savevolley.okhttp3.volley.VolleyError;
import com.camnter.savevolley.okhttp3.volley.toolbox.HttpHeaderParser;
import com.google.android.agera.Reservoir;
import com.google.android.agera.Reservoirs;
import com.google.gson.Gson;
import java.io.UnsupportedEncodingException;

/**
 * Description：GsonReservoirRequest
 * Created by：CaMnter
 * Time：2016-06-23 11:47
 */

public class GsonReservoirRequest<T> extends Request<T>
    implements Response.Listener<T>, Response.ErrorListener {

    protected static final String PROTOCOL_CHARSET = "utf-8";

    private final Gson mGson;
    private final Response.Listener<T> mResponseListener;
    private final Class<T> mClass;
    private final Reservoir<Object> mReservoir;


    public Reservoir<Object> getReservoir() {
        return this.mReservoir;
    }


    public GsonReservoirRequest(String url, Class<T> clazz) {
        this(Method.GET, url, clazz);
    }


    public GsonReservoirRequest(int method, String url, Class<T> clazz) {
        super(method, url, null);
        this.mGson = new Gson();
        this.mClass = clazz;
        this.mResponseListener = this;
        this.mReservoir = Reservoirs.reservoir();
    }


    @Override protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data,
                HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));
            return Response.success(this.mGson.fromJson(jsonString, this.mClass),
                HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }


    @Override protected void deliverResponse(T response) {
        this.mResponseListener.onResponse(response);
    }


    @Override public void deliverError(VolleyError error) {
        this.onErrorResponse(error);
    }


    @Override public void onErrorResponse(VolleyError error) {
        this.mReservoir.accept(error);
    }


    @Override public void onResponse(T response) {
        this.mReservoir.accept(response);
    }
}
