package com.camnter.savevolley.hurl.fastjson.request;

import com.alibaba.fastjson.JSON;
import com.camnter.savevolley.hurl.NetworkResponse;
import com.camnter.savevolley.hurl.ParseError;
import com.camnter.savevolley.hurl.Request;
import com.camnter.savevolley.hurl.Response;
import com.camnter.savevolley.hurl.VolleyError;
import com.camnter.savevolley.hurl.toolbox.HttpHeaderParser;
import java.io.UnsupportedEncodingException;

/**
 * Description：HurlFastJsonRequest
 * Created by：CaMnter
 * Time：2016-06-27 12:15
 */

public abstract class HurlFastjsonRequest<T> extends Request<T>
    implements Response.Listener<T>, Response.ErrorListener {

    protected static final String PROTOCOL_CHARSET = "utf-8";

    private Response.Listener<T> mResponseListener;
    private Class<T> mClass;


    public HurlFastjsonRequest(String url, Class<T> clazz) {
        this(Method.GET, url, clazz);
    }


    public HurlFastjsonRequest(int method, String url, Class<T> clazz) {
        super(method, url, null);
        this.mClass = clazz;
        this.mResponseListener = this;
    }


    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {

        try {
            String jsonString = new String(response.data,
                HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));
            return Response.success(
                JSON.parseObject(jsonString, this.mClass),
                HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }


    @Override protected void deliverResponse(T response) {
        this.mResponseListener.onResponse(response);
    }


    /**
     * @return this request's {@link Response.ErrorListener}.
     */
    public Response.ErrorListener getErrorListener() {
        return this;
    }


    /**
     * Delivers error message to the ErrorListener that the Request was
     * initialized with.
     *
     * @param error Error details
     */
    @Override public void deliverError(VolleyError error) {
        this.onErrorResponse(error);
    }


    /**
     * Called when a response is received.
     */
    public abstract void onResponse(T response);

    /**
     * Callback method that an error has been occurred with the
     * provided error code and optional user-readable message.
     */
    public abstract void onErrorResponse(VolleyError error);
}
