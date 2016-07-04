/*
 * Copyright (C) 2016 CaMnter yuanyu.camnter@gmail.com
 * Copyright (C) 2011 The Android Open Source Project
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

package com.camnter.savevolley.hurl;

import com.camnter.savevolley.network.core.http.core.HttpStatus;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.Map;
import java.util.zip.GZIPInputStream;

/**
 * Data and headers returned from {@link Network#performRequest(Request)}.
 */
public class NetworkResponse {
    /** The HTTP status code. */
    public final int statusCode;
    /** Raw data from this response. */
    public final byte[] data;
    /** Response headers. */
    public final Map<String, String> headers;
    /** True if the server returned a 304 (Not Modified). */
    public final boolean notModified;
    /** Network roundtrip time in milliseconds. */
    public final long networkTimeMs;
    /** Raw data from this response by gzip parse */
    private byte[] gzipData;


    /**
     * Creates a new network response.
     *
     * @param statusCode the HTTP status code
     * @param data Response body
     * @param headers Headers returned with this response, or null for none
     * @param notModified True if the server returned a 304 and the data was already in cache
     * @param networkTimeMs Round-trip network time to receive network response
     */
    public NetworkResponse(int statusCode, byte[] data, Map<String, String> headers, boolean notModified, long networkTimeMs) {
        this.statusCode = statusCode;
        this.data = data;
        this.headers = headers;
        this.notModified = notModified;
        this.networkTimeMs = networkTimeMs;
    }


    public NetworkResponse(int statusCode, byte[] data, Map<String, String> headers, boolean notModified) {
        this(statusCode, data, headers, notModified, 0);
    }


    public NetworkResponse(byte[] data) {
        this(HttpStatus.SC_OK, data, Collections.<String, String>emptyMap(), false, 0);
    }


    public NetworkResponse(byte[] data, Map<String, String> headers) {
        this(HttpStatus.SC_OK, data, headers, false, 0);
    }


    private String getContentEncoding() {
        return this.headers.get("Content-Encoding");
    }


    /**
     * Is gzipped?
     *
     * @param contentEncoding Content-Encoding
     * @return true or false
     */
    private boolean gzipped(String contentEncoding) {
        return contentEncoding != null && contentEncoding.toLowerCase().contains("gzip");
    }


    /**
     * Get the raw data from this response by gzip parse
     *
     * @return gzip byte[]
     */
    private byte[] getGzipData() {
        if (gzipData == null && this.gzipped(this.getContentEncoding())) {
            try {
                String result = "";
                GZIPInputStream gzipInputStream = new GZIPInputStream(
                    new ByteArrayInputStream(this.data));
                InputStreamReader inputStreamReader = new InputStreamReader(gzipInputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String read;
                while ((read = bufferedReader.readLine()) != null) {
                    result += read;
                }
                bufferedReader.close();
                inputStreamReader.close();
                gzipInputStream.close();

                this.gzipData = result.getBytes();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return this.gzipData;
    }


    /**
     * gzip data or normal data
     *
     * @return the finally byte[] data
     */
    public byte[] getResultData() {
        return this.getGzipData() != null ? this.gzipData : this.data;
    }

}

