/*
 * Copyright (C) 2016 CaMnter yuanyu.camnter@gmail.com
 * Copyright (C) 2008 The Apache Software Foundation (ASF)
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

package com.camnter.savevolley.network.core.http;

import com.camnter.savevolley.network.core.http.core.StatusLine;

/**
 * Represents a status line as returned from a HTTP server.
 * See <a href="http://www.ietf.org/rfc/rfc2616.txt">RFC2616</a> section 6.1.
 * This class is immutable and therefore inherently thread safe.
 *
 * @author <a href="mailto:jsdever@apache.org">Jeff Dever</a>
 * @author <a href="mailto:mbowler@GargoyleSoftware.com">Mike Bowler</a>
 * @version $Id: BasicStatusLine.java 604625 2007-12-16 14:11:11Z olegk $
 * @since 4.0
 */
public class SaveStatusLine implements StatusLine, Cloneable {

    // ----------------------------------------------------- Instance Variables

    /** The protocol version. */
    private final SaveProtocolVersion protoVersion;

    /** The status code. */
    private final int statusCode;

    /** The reason phrase. */
    private final String reasonPhrase;

    // ----------------------------------------------------------- Constructors


    /**
     * Creates a new status line with the given version, status, and reason.
     *
     * @param version the protocol version of the response
     * @param statusCode the status code of the response
     * @param reasonPhrase the reason phrase to the status code, or
     * <code>null</code>
     */
    public SaveStatusLine(final SaveProtocolVersion version, int statusCode, final String reasonPhrase) {
        super();
        if (version == null) {
            throw new IllegalArgumentException("Protocol version may not be null.");
        }
        if (statusCode < 0) {
            throw new IllegalArgumentException("Status code may not be negative.");
        }
        this.protoVersion = version;
        this.statusCode = statusCode;
        this.reasonPhrase = reasonPhrase;
    }

    // --------------------------------------------------------- Public Methods


    /**
     * @return the Status-Code
     */
    public int getStatusCode() {
        return this.statusCode;
    }


    /**
     * @return the HTTP-Version
     */
    public SaveProtocolVersion getProtocolVersion() {
        return this.protoVersion;
    }


    /**
     * @return the Reason-Phrase
     */
    public String getReasonPhrase() {
        return this.reasonPhrase;
    }


    public String toString() {
        // no need for non-default formatting in toString()
        return SaveLineFormatter.DEFAULT.formatStatusLine(null, this).toString();
    }


    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
