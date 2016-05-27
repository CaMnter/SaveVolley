package com.camnter.easyvolley.okhttp3.adapter;

import com.camnter.easyvolley.network.adapter.core.EasyHttpEntityAdapter;
import com.camnter.easyvolley.network.core.http.EasyHttpEntity;
import okhttp3.MediaType;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Description：OkHttp3HttpEntityAdapter
 * Created by：CaMnter
 * Time：2016-05-27 16:43
 */
public class OkHttp3HttpEntityAdapter implements EasyHttpEntityAdapter<okhttp3.Response> {

    private volatile static OkHttp3HttpEntityAdapter instance = null;


    private OkHttp3HttpEntityAdapter() {
    }


    public static OkHttp3HttpEntityAdapter getInstance() {
        if (instance == null) {
            synchronized (OkHttp3StatusLineAdapter.class) {
                if (instance == null) instance = new OkHttp3HttpEntityAdapter();
            }
        }
        return instance;
    }


    @Override public EasyHttpEntity adaptiveEntity(Response response) {
        EasyHttpEntity entity = new EasyHttpEntity();
        ResponseBody body = response.body();
        if (body != null) {
            entity.setContent(body.byteStream());
            entity.setContentLength(body.contentLength());
            entity.setContentEncoding(response.header("Content-Encoding"));
            MediaType contentType = body.contentType();
            entity.setContentType(contentType != null ? contentType.type() : null);
        }
        return entity;
    }
}
