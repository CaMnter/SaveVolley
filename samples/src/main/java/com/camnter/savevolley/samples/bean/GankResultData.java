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

import com.alibaba.fastjson.annotation.JSONField;
import com.google.gson.annotations.SerializedName;

/**
 * Description：GankResultData
 * Created by：CaMnter
 * Time：2016-06-22 23:47
 */

public class GankResultData {
    private static final String TAG = "GankResultData";

    /* for gson */
    @SerializedName("_id")
    /* for fastjson */
    @JSONField(name = "_id")
    private String id;
    private String createdAt;
    private String desc;
    private String publishedAt;
    private String source;
    private String type;
    private String url;
    private boolean used;
    private String who;


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


    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }


    public String getCreatedAt() {
        return createdAt;
    }


    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }


    public String getDesc() {
        return desc;
    }


    public void setDesc(String desc) {
        this.desc = desc;
    }


    public String getPublishedAt() {
        return publishedAt;
    }


    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }


    public String getSource() {
        return source;
    }


    public void setSource(String source) {
        this.source = source;
    }


    public String getType() {
        return type;
    }


    public void setType(String type) {
        this.type = type;
    }


    public String getUrl() {
        return url;
    }


    public void setUrl(String url) {
        this.url = url;
    }


    public boolean isUsed() {
        return used;
    }


    public void setUsed(boolean used) {
        this.used = used;
    }


    public String getWho() {
        return who;
    }


    public void setWho(String who) {
        this.who = who;
    }
}
