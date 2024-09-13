package com.globant.utils.baseRequest;

import com.globant.models.headers.HeadersEnum;
import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

import java.util.Map;

/**
 * Class implements the most basic request.
 *
 * @author Carlos Sandoval <c.sandoval@globant.com>
 * @version 0.1
 * @since 0.1
 */
public class BaseRequest {
    protected String endpoint;

    /**
     * This is a function to read elements using rest-assured
     *
     * @param endpoint api url
     * @param headers  a map of headers
     * @return Response of the request
     */
    protected Response requestGet(String endpoint, Map<String, String> headers) {
        return RestAssured.given()
                .contentType(HeadersEnum.CONTENT_TYPE.getValue())
                .headers(headers)
                .when()
                .get(endpoint);
    }

    /**
     * This is a function to create a new element using rest-assured
     *
     * @param endpoint api url
     * @param headers  a map of headers
     * @param body     model object
     * @return Response of the request
     */
    protected Response requestPost(String endpoint, Map<String, String> headers, Object body) {
        return RestAssured.given()
                .contentType(HeadersEnum.CONTENT_TYPE.getValue())
                .headers(headers)
                .body(body)
                .when()
                .post(endpoint);
    }

    /**
     * This is a function to update an element using rest-assured
     *
     * @param endpoint api url
     * @param headers  a map of headers
     * @param body     model object
     * @return Response of the request
     */
    protected Response requestPut(String endpoint, Map<String, String> headers, Object body) {
        return RestAssured.given()
                .contentType(HeadersEnum.CONTENT_TYPE.getValue())
                .headers(headers)
                .body(body)
                .when()
                .put(endpoint);
    }

    /**
     * This is a function to delete an element using rest-assured
     *
     * @param endpoint api url
     * @param headers  a map of headers
     * @return Response of the response
     */
    protected Response requestDelete(String endpoint, Map<String, String> headers) {
        return RestAssured.given()
                .contentType(HeadersEnum.CONTENT_TYPE.getValue())
                .headers(headers)
                .when()
                .delete(endpoint);
    }

    /**
     * Validates whether a response complies with a specific schema.
     * <p>
     * This method takes a response and a schema path as parameters,
     * and checks if the response is valid according to the provided schema.
     *
     * @param response   The response to be validated.
     * @param schemaPath The path to the schema that will be used for validation.
     * @return true if the response is valid according to the schema; false otherwise.
     */
    public boolean isValidSchema(Response response, String schemaPath) {
        try {
            response.then()
                    .assertThat()
                    .body(JsonSchemaValidator.matchesJsonSchemaInClasspath(schemaPath));
            return true;
        } catch (AssertionError e) {
            return false;
        }
    }
}
