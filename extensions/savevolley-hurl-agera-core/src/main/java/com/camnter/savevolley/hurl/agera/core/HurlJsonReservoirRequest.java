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

package com.camnter.savevolley.hurl.agera.core;

import android.support.annotation.NonNull;
import com.camnter.savevolley.hurl.NetworkResponse;
import com.camnter.savevolley.hurl.ParseError;
import com.camnter.savevolley.hurl.Request;
import com.camnter.savevolley.hurl.Response;
import com.camnter.savevolley.hurl.VolleyError;
import com.camnter.savevolley.hurl.toolbox.HttpHeaderParser;
import java.io.UnsupportedEncodingException;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Description：HurlJsonReservoirRequest
 * Created by：CaMnter
 * Time：2016-06-29 16:59
 */

public class HurlJsonReservoirRequest extends HurlReservoirRequest<JSONObject>
    implements Response.Listener<JSONObject>, Response.ErrorListener {

    private static final String PROTOCOL_CHARSET = "utf-8";

    private final Response.Listener<JSONObject> mResponseListener;


    public HurlJsonReservoirRequest(@NonNull String url) {
        this(Request.Method.GET, url);
    }


    public HurlJsonReservoirRequest(int method,
                                    @NonNull String url) {
        super(method, url, null);
        this.mResponseListener = this;
    }


    @Override protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.getResultData(),
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
