package com.camnter.savevolley.samples.compiler;

import android.content.Context;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Description：SaveVolleyCompilerStates
 * Created by：CaMnter
 * Time：2016-06-23 13:44
 */

public interface SaveVolleyCompilerStates {

    int GSON = 260;
    int JSON_OBJECT = 261;
    int JSON_ARRAY = 262;


    @IntDef({ GSON, JSON_OBJECT, JSON_ARRAY })
    @Retention(RetentionPolicy.SOURCE) @interface ParseStyle {}


    interface VRequestState<RType> {

        @NonNull VRequestState<RType> method(@Nullable Integer method);

        @NonNull VRequestState<RType> url(@NonNull String url);

        @NonNull VRequestState<RType> parseStyle(@Nullable @ParseStyle Integer parseStyle);

        @NonNull VRequestState<RType> type(@Nullable Class<RType> clazz);

        @NonNull
        VRequestQueue create();
    }


    interface VRequestQueue {
        SaveVolley execute(@NonNull Context context);
    }

}
