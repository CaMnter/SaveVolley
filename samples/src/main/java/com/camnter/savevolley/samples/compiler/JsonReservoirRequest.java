package com.camnter.savevolley.samples.compiler;

import com.camnter.savevolley.okhttp3.volley.NetworkResponse;
import com.camnter.savevolley.okhttp3.volley.ParseError;
import com.camnter.savevolley.okhttp3.volley.Request;
import com.camnter.savevolley.okhttp3.volley.Response;
import com.camnter.savevolley.okhttp3.volley.VolleyError;
import com.camnter.savevolley.okhttp3.volley.toolbox.HttpHeaderParser;
import com.google.android.agera.Reservoir;
import com.google.android.agera.Reservoirs;
import java.io.UnsupportedEncodingException;
import org.json.JSONException;
import org.json.JSONObject;

import static com.camnter.savevolley.samples.compiler.GsonReservoirRequest.PROTOCOL_CHARSET;

/**
 * Description：JsonReservoirRequest
 * Created by：CaMnter
 * Time：2016-06-23 16:00
 */

public class JsonReservoirRequest extends Request<JSONObject>
    implements Response.Listener<JSONObject>, Response.ErrorListener {

    private final Response.Listener<JSONObject> mResponseListener;
    private final Reservoir<Object> mReservoir;


    public Reservoir<Object> getReservoir() {
        return this.mReservoir;
    }


    public JsonReservoirRequest(String url) {
        this(Method.GET, url);
    }


    public JsonReservoirRequest(int method, String url) {
        super(method, url, null);
        this.mResponseListener = this;
        this.mReservoir = Reservoirs.reservoir();
    }


    @Override protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data,
                HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));
            return Response.success(new JSONObject(jsonString),
                HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }


    @Override protected void deliverResponse(JSONObject response) {
        this.mResponseListener.onResponse(response);
    }


    @Override public void deliverError(VolleyError error) {
        this.onErrorResponse(error);
    }


    @Override public void onErrorResponse(VolleyError error) {
        this.mReservoir.accept(error);
    }


    @Override public void onResponse(JSONObject response) {
        this.mReservoir.accept(response);
    }

}
