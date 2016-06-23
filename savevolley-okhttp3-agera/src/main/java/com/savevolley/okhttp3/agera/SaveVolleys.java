package com.savevolley.okhttp3.agera;

/**
 * Description：SaveVolleys
 * Created by：CaMnter
 * Time：2016-06-23 16:27
 */

public class SaveVolleys {

    public static <T> SaveVolleyCompiler<T> request(final String url) {
        return SaveVolleyCompiler.request(url);
    }

}
