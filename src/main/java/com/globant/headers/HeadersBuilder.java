package com.globant.headers;

import java.util.HashMap;
import java.util.Map;

public class HeadersBuilder {
    private static final Map<String, String> headers = new HashMap<>();

    public static Map<String, String> getBaseHeaders() {
        headers.put(HeadersEnum.ACCEPT.getHeader(), HeadersEnum.ACCEPT.getValue());
        headers.put(HeadersEnum.CONTENT_TYPE.getHeader(), HeadersEnum.CONTENT_TYPE.getValue());
        return headers;
    }
}
