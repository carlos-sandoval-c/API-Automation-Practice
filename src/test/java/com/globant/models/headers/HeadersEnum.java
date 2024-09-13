package com.globant.models.headers;

import lombok.Getter;

/**
 * Enum that allows mapping the name of the headers and adjusting their values to
 * facilitate the builder process.
 *
 * @author Carlos Sandoval <c.sandoval@globant.com>
 * @version 0.1
 * @since 0.1
 */
@Getter
public enum HeadersEnum {
    ACCEPT("Accept", "application/json"),
    CONTENT_TYPE("Content-Type", "application/json");

    private final String header;
    private final String value;

    HeadersEnum(String headerName, String headerValue) {
        this.header = headerName;
        this.value = headerValue;
    }
}
