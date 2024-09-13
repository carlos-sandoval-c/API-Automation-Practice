package com.globant.utils.baseRequest;

import com.globant.headers.HeadersEnum;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.Map;

public class BaseRequest {
    protected Response requestGet(String endpoint, Map<String, String> headers) {
        return RestAssured.given()
                .contentType(HeadersEnum.CONTENT_TYPE.getValue())
                .headers(headers)
                .when()
                .get(endpoint);
    }

    protected Response requestPost(String endpoint, Map<String, String> headers, Object body) {
        return RestAssured.given()
                .contentType(HeadersEnum.CONTENT_TYPE.getValue())
                .headers(headers)
                .body(body)
                .when()
                .post(endpoint);
    }

    protected Response requestPut(String endpoint, Map<String, String> headers, Object body) {
        return RestAssured.given()
                .contentType(HeadersEnum.CONTENT_TYPE.getValue())
                .headers(headers)
                .body(body)
                .when()
                .put(endpoint);
    }

    protected Response requestPost(String endpoint, Map<String, String> headers) {
        return RestAssured.given()
                .contentType(HeadersEnum.CONTENT_TYPE.getValue())
                .headers(headers)
                .when()
                .delete(endpoint);
    }
}
