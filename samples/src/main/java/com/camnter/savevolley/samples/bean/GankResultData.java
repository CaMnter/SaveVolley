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

package com.camnter.savevolley.samples.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Description：GankResultData
 * Created by：CaMnter
 * Time：2016-06-22 23:47
 */

public class GankResultData {
    private static final String TAG = "GankResultData";

    @SerializedName("_id") public String id;
    public String createdAt;
    public String desc;
    public String publishedAt;
    public String source;
    public String type;
    public String url;
    public boolean used;
    public String who;


    @Override public String toString() {
        return TAG + "id: " +
            this.id +
            "\n" +
            "createdAt: " +
            this.createdAt +
            "\n" +
            "desc: " +
            this.desc +
            "\n" +
            "publishedAt: " +
            this.publishedAt +
            "\n" +
            "source: " +
            this.source +
            "\n" +
            "type: " +
            this.type +
            "\n" +
            "url: " +
            this.url +
            "\n" +
            "used: " +
            this.used +
            "\n" +
            "who: " +
            this.who;
    }
}
