package com.globant.models.headers;

import lombok.Getter;

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
