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

package com.camnter.savevolley.hurl.agera.gson;

import android.support.annotation.NonNull;

/**
 * Description：SaveVolleys
 * Created by：CaMnter
 * Time：2016-06-27 00:31
 */

public final class SaveVolleys {

    @NonNull
    public static <T> SaveVolleyCompilerStates.VRequestState<T> request(final String url) {
        return SaveVolleyCompiler.request(url);
    }


    private SaveVolleys() {}

}
