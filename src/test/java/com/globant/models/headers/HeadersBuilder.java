package com.globant.models.headers;

import java.util.HashMap;
import java.util.Map;

/**
 * Class for the construction and obtaining of the headers necessary to make the requests to the API.
 *
 * @author Carlos Sandoval <c.sandoval@globant.com>
 * @version 0.1
 * @since 0.1
 */
public class HeadersBuilder {
    private static final Map<String, String> headers = new HashMap<>();

    /**
     * Get headers to basic petitions
     *
     * @return Map with the required headers
     */
    public static Map<String, String> getBaseHeaders() {
        headers.put(HeadersEnum.ACCEPT.getHeader(), HeadersEnum.ACCEPT.getValue());
        headers.put(HeadersEnum.CONTENT_TYPE.getHeader(), HeadersEnum.CONTENT_TYPE.getValue());
        return headers;
    }
}
