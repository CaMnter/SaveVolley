package com.camnter.easyvolley.network.adapter.core;

import com.camnter.easyvolley.network.core.http.EasyHeader;

/**
 * Description：EasyHeaderAdapter
 * Created by：CaMnter
 * Time：2016-05-27 14:01
 */
public interface EasyHeaderAdapter<T> {
    EasyHeader adaptiveHeader(T t);
}
