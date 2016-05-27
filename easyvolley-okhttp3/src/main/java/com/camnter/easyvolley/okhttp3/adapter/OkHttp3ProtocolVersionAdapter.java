package com.camnter.easyvolley.okhttp3.adapter;

import com.camnter.easyvolley.network.adapter.core.EasyProtocolVersionAdapter;
import com.camnter.easyvolley.network.core.http.EasyProtocolVersion;

/**
 * Description：OkHttp3ProtocolVersionAdapter
 * Created by：CaMnter
 * Time：2016-05-27 16:16
 */
public class OkHttp3ProtocolVersionAdapter implements EasyProtocolVersionAdapter<okhttp3.Response> {

    private volatile static OkHttp3ProtocolVersionAdapter instance = null;


    private OkHttp3ProtocolVersionAdapter() {
    }


    public static OkHttp3ProtocolVersionAdapter getInstance() {
        if (instance == null) {
            synchronized (OkHttp3ProtocolVersionAdapter.class) {
                if (instance == null) instance = new OkHttp3ProtocolVersionAdapter();
            }
        }
        return instance;
    }


    @Override public EasyProtocolVersion adaptiveProtocolVersion(okhttp3.Response response) {
        if (response == null || response.protocol() == null) return null;
        switch (response.protocol()) {
            case HTTP_1_0:
                return new EasyProtocolVersion("HTTP", 1, 0);
            case HTTP_1_1:
                return new EasyProtocolVersion("HTTP", 1, 1);
            case SPDY_3:
                return new EasyProtocolVersion("SPDY", 3, 1);
            case HTTP_2:
                return new EasyProtocolVersion("HTTP", 2, 0);
            default:
                throw new IllegalStateException("Unknown protocol type.");
        }
    }
}
