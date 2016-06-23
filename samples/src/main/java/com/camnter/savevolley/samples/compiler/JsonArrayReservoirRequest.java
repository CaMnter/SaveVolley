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
import org.json.JSONArray;
import org.json.JSONException;

import static com.camnter.savevolley.samples.compiler.GsonReservoirRequest.PROTOCOL_CHARSET;

/**
 * Description：JsonArrayReservoirRequest
 * Created by：CaMnter
 * Time：2016-06-23 16:09
 */

public class JsonArrayReservoirRequest extends Request<JSONArray>
    implements Response.Listener<JSONArray>, Response.ErrorListener {

    private final Response.Listener<JSONArray> mResponseListener;
    private final Reservoir<Object> mReservoir;


    public Reservoir<Object> getReservoir() {
        return this.mReservoir;
    }


    public JsonArrayReservoirRequest(String url) {
        this(Method.GET, url);
    }


    public JsonArrayReservoirRequest(int method, String url) {
        super(method, url, null);
        this.mResponseListener = this;
        this.mReservoir = Reservoirs.reservoir();
    }


    @Override protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data,
                HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));
            return Response.success(new JSONArray(jsonString),
                HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }


    @Override protected void deliverResponse(JSONArray response) {
        this.mResponseListener.onResponse(response);
    }


    @Override public void deliverError(VolleyError error) {
        this.onErrorResponse(error);
    }


    @Override public void onErrorResponse(VolleyError error) {
        this.mReservoir.accept(error);
    }


    @Override public void onResponse(JSONArray response) {
        this.mReservoir.accept(response);
    }

}
