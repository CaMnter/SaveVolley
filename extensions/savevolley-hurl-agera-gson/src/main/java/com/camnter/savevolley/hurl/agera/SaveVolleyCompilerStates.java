/*
 * Copyright (C) 2016 CaMnter yuanyu.camnter@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.camnter.savevolley.hurl.agera;

import android.content.Context;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Map;

/**
 * Description：SaveVolleyCompilerStates
 * Created by：CaMnter
 * Time：2016-06-27 00:27
 */

public interface SaveVolleyCompilerStates {

    int GSON = 260;
    int JSON_OBJECT = 261;
    int JSON_ARRAY = 262;


    @IntDef({ GSON, JSON_OBJECT, JSON_ARRAY })
    @Retention(RetentionPolicy.SOURCE) @interface ParseStyle {}


    interface VRequestState<RType> {

        @NonNull
        VRequestState<RType> url(@NonNull String url);

        @NonNull
        VRequestState<RType> method(@Nullable Integer method);

        @NonNull
        VRequestState<RType> addParam(@NonNull String key, @NonNull String value);

        @NonNull
        VRequestState<RType> resetParams(@Nullable Map<String, String> params);

        @NonNull
        VRequestState<RType> parseStyle(@Nullable @ParseStyle Integer parseStyle);

        @NonNull
        VRequestState<RType> classOf(@Nullable Class<RType> classOfT);

        @NonNull
        VRequestQueue<RType> createRequest();
    }


    interface VRequestQueue<RType> {

        @NonNull
        VTermination<RType> context(@NonNull Context context);

    }


    interface VTermination<RType> {

        @NonNull
        SaveVolley compile();

    }

}
