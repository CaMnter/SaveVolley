package com.camnter.easyvolley.network.adapter.core;

import com.camnter.easyvolley.network.core.http.EasyHttpResponse;

/**
 * Description：EasyHttpResponseApdater
 * Created by：CaMnter
 * Time：2016-05-27 14:13
 */
public interface EasyHttpResponseApdater<T> {
    EasyHttpResponse adaptiveResponse(T t);
}
