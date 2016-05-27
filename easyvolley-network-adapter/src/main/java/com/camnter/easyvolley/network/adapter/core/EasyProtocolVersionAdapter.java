package com.camnter.easyvolley.network.adapter.core;

import com.camnter.easyvolley.network.core.http.EasyProtocolVersion;

/**
 * Description：EasyProtocolVersionAdapter
 * Created by：CaMnter
 * Time：2016-05-27 14:14
 */
public interface EasyProtocolVersionAdapter<T> {
    EasyProtocolVersion adaptiveProtocolVersion(T t);
}
