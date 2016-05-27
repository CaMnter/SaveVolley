package com.camnter.easyvolley.okhttp3.adapter;

import com.camnter.easyvolley.network.adapter.core.EasyProtocolVersionAdapter;
import com.camnter.easyvolley.network.adapter.core.EasyStatusLineAdapter;
import com.camnter.easyvolley.network.core.http.EasyStatusLine;

/**
 * Description：OkHttp3StatusLineAdapter
 * Created by：CaMnter
 * Time：2016-05-27 16:24
 */
public class OkHttp3StatusLineAdapter implements EasyStatusLineAdapter<okhttp3.Response> {

    private volatile static OkHttp3StatusLineAdapter instance = null;


    private OkHttp3StatusLineAdapter() {
    }


    public static OkHttp3StatusLineAdapter getInstance() {
        if (instance == null) {
            synchronized (OkHttp3StatusLineAdapter.class) {
                if (instance == null) instance = new OkHttp3StatusLineAdapter();
            }
        }
        return instance;
    }


    @Override
    public EasyStatusLine adaptiveStatusLine(EasyProtocolVersionAdapter<okhttp3.Response> easyProtocolVersionAdapter, okhttp3.Response response) {
        return new EasyStatusLine(easyProtocolVersionAdapter.adaptiveProtocolVersion(response),
                response.code(), response.message());
    }
}
