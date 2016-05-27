package com.camnter.easyvolley.network.adapter.core;

import com.camnter.easyvolley.network.core.http.EasyHttpEntity;

/**
 * Description：EasyHttpEntityAdapter
 * Created by：CaMnter
 * Time：2016-05-27 14:10
 */
public interface EasyHttpEntityAdapter<T> {
    EasyHttpEntity adaptiveEntity(T t);
}
