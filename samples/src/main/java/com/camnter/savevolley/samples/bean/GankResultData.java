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
