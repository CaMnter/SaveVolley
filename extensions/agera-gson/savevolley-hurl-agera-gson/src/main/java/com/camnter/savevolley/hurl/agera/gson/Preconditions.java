/*
 * Copyright (C) 2016 CaMnter yuanyu.camnter@gmail.com
 * Copyright (C) 2007 The Guava Authors
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
import android.support.annotation.Nullable;

/**
 * @author Kevin Bourrillion
 * @since 2.0 (imported from Google Collections Library)
 */
public final class Preconditions {
    private Preconditions() {}


    /**
     * Ensures that an object reference passed as a parameter to the calling method is not null.
     *
     * @param reference an object reference
     * @return the non-null reference that was validated
     * @throws NullPointerException if {@code reference} is null
     */
    public static <T> T checkNotNull(T reference) {
        if (reference == null) {
            throw new NullPointerException();
        }
        return reference;
    }


    /**
     * Ensures that an object reference passed as a parameter to the calling method is not null.
     *
     * @param reference an object reference
     * @param errorMessage the exception message to use if the check fails; will be converted to a
     * string using {@link String#valueOf(Object)}
     * @return the non-null reference that was validated
     * @throws NullPointerException if {@code reference} is null
     */
    public static <T> T checkNotNull(T reference, @Nullable Object errorMessage) {
        if (reference == null) {
            throw new NullPointerException(String.valueOf(errorMessage));
        }
        return reference;
    }


    /**
     * Ensures that an object reference passed as a parameter to the calling method is not null.
     *
     * @param reference an object reference
     * @param errorMessageTemplate a template for the exception message should the check fail. The
     * message is formed by replacing each {@code %s} placeholder in the template with an
     * argument. These are matched by position - the first {@code %s} gets {@code
     * errorMessageArgs[0]}, etc.  Unmatched arguments will be appended to the formatted message
     * in square braces. Unmatched placeholders will be left as-is.
     * @param errorMessageArgs the arguments to be substituted into the message template. Arguments
     * are converted to strings using {@link String#valueOf(Object)}.
     * @return the non-null reference that was validated
     * @throws NullPointerException if {@code reference} is null
     */
    public static <T> T checkNotNull(T reference,
                                     @Nullable String errorMessageTemplate,
                                     @Nullable Object... errorMessageArgs) {
        if (reference == null) {
            // If either of these parameters is null, the right thing happens anyway
            throw new NullPointerException(format(errorMessageTemplate, errorMessageArgs));
        }
        return reference;
    }


    public static void checkArgument(final boolean expression, @NonNull final String errorMessage) {
        if (!expression) {
            throw new IllegalArgumentException(errorMessage);
        }
    }


    /**
     * Substitutes each {@code %s} in {@code template} with an argument. These are matched by
     * position: the first {@code %s} gets {@code args[0]}, etc.  If there are more arguments than
     * placeholders, the unmatched arguments will be appended to the end of the formatted message
     * in
     * square braces.
     *
     * @param template a non-null string containing 0 or more {@code %s} placeholders.
     * @param args the arguments to be substituted into the message template. Arguments are
     * converted
     * to strings using {@link String#valueOf(Object)}. Arguments can be null.
     */
    // Note that this is somewhat-improperly used from Verify.java as well.
    static String format(String template, @Nullable Object... args) {
        template = String.valueOf(template); // null -> "null"

        // start substituting the arguments into the '%s' placeholders
        StringBuilder builder = new StringBuilder(template.length() + 16 * args.length);
        int templateStart = 0;
        int i = 0;
        while (i < args.length) {
            int placeholderStart = template.indexOf("%s", templateStart);
            if (placeholderStart == -1) {
                break;
            }
            builder.append(template.substring(templateStart, placeholderStart));
            builder.append(args[i++]);
            templateStart = placeholderStart + 2;
        }
        builder.append(template.substring(templateStart));

        // if we run out of placeholders, append the extra args in square braces
        if (i < args.length) {
            builder.append(" [");
            builder.append(args[i++]);
            while (i < args.length) {
                builder.append(", ");
                builder.append(args[i++]);
            }
            builder.append(']');
        }

        return builder.toString();
    }
}
