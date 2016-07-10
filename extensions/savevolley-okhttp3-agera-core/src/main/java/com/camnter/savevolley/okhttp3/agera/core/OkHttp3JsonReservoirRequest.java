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

package com.camnter.savevolley.okhttp3.agera.core;

import android.support.annotation.NonNull;
import com.camnter.savevolley.okhttp3.volley.NetworkResponse;
import com.camnter.savevolley.okhttp3.volley.ParseError;
import com.camnter.savevolley.okhttp3.volley.Request;
import com.camnter.savevolley.okhttp3.volley.Response;
import com.camnter.savevolley.okhttp3.volley.VolleyError;
import com.camnter.savevolley.okhttp3.volley.toolbox.HttpHeaderParser;
import java.io.UnsupportedEncodingException;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Description：OkHttp3JsonReservoirRequest
 * Created by：CaMnter
 * Time：2016-06-23 16:00
 */

public class OkHttp3JsonReservoirRequest extends Okhttp3ReservoirRequest<JSONObject>
    implements Response.Listener<JSONObject>, Response.ErrorListener {

    private static final String PROTOCOL_CHARSET = "utf-8";

    private final Response.Listener<JSONObject> mResponseListener;


    public OkHttp3JsonReservoirRequest(@NonNull String url) {
        this(Request.Method.GET, url);
    }


    public OkHttp3JsonReservoirRequest(int method,
                                       @NonNull String url) {
        super(method, url, null);
        this.mResponseListener = this;
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
