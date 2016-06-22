package com.camnter.savevolley.samples.bean;

import java.util.ArrayList;

/**
 * Description：GankData
 * Created by：CaMnter
 * Time：2016-06-22 17:47
 */

public class GankData {
    private static final String TAG = "GankData";

    public boolean error;
    public ArrayList<GankResultData> results;


    @Override public String toString() {
        StringBuilder builder = new StringBuilder(TAG).append("\n\n");
        for (GankResultData result : results) {
            builder.append(result.toString());
            builder.append("\n\n");
        }
        return builder.toString();
    }



}
