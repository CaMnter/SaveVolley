package com.camnter.easyvolley.network.adapter.core;

import com.camnter.easyvolley.network.core.http.EasyStatusLine;

/**
 * Description：EasyStatusLineAdapter
 * Created by：CaMnter
 * Time：2016-05-27 14:15
 */
public interface EasyStatusLineAdapter<T> {
    EasyStatusLine adaptiveStatusLine(T t);
}
