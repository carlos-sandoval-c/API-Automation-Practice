package com.globant.models.requests;

import com.github.javafaker.Book;
import com.github.javafaker.Faker;
import com.globant.models.common.Resource;
import com.globant.models.headers.HeadersBuilder;
import com.globant.utils.baseRequest.BaseRequest;
import com.globant.utils.constants.Constants;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.util.List;

/**
 * This class will be in charge of implementing all the requests that are related to Resources.
 *
 * @author Carlos Sandoval <c.sandoval@globant.com>
 * @version 0.1
 * @since 0.1
 */
public class ResourcesRequest extends BaseRequest {
    /**
     * Get Resource list
     *
     * @return rest-assured response
     */
    public Response getResources() {
        endpoint = String.format(Constants.API_URL, Constants.RESOURCES_ENDPOINT);
        return requestGet(endpoint, HeadersBuilder.getBaseHeaders());
    }

    /**
     * Get Resource by Id
     *
     * @param resourceId string
     * @return rest-assured response
     */
    public Response getResource(String resourceId) {
        endpoint = String.format(Constants.API_URL_WITH_PARAM, Constants.RESOURCES_ENDPOINT, resourceId);
        return requestGet(endpoint, HeadersBuilder.getBaseHeaders());
    }

    /**
     * Create a new resource
     *
     * @param resource model
     * @return rest-assured response
     */
    public Response createResource(Resource resource) {
        endpoint = String.format(Constants.API_URL, Constants.RESOURCES_ENDPOINT);
        return requestPost(endpoint, HeadersBuilder.getBaseHeaders(), resource);
    }

    /**
     * Update resource using Id
     *
     * @param resourceId string
     * @param resource   model
     * @return rest-assured response
     */
    public Response updateResource(String resourceId, Resource resource) {
        endpoint = String.format(Constants.API_URL_WITH_PARAM, Constants.RESOURCES_ENDPOINT, resourceId);
        return requestPut(endpoint, HeadersBuilder.getBaseHeaders(), resource);
    }

    /**
     * Delete Resource by Id
     *
     * @param resourceId string
     * @return rest-assured response
     */
    public Response deleteResource(String resourceId) {
        endpoint = String.format(Constants.API_URL_WITH_PARAM, Constants.RESOURCES_ENDPOINT, resourceId);
        return requestDelete(endpoint, HeadersBuilder.getBaseHeaders());
    }

    /**
     * Create a new resource (model) with random data
     *
     * @return resource model
     */
    public Resource generateRandomResource() {
        Faker faker = new Faker();
        Resource resource = new Resource();

        Book book = faker.book();

        resource.setName(book.title());
        resource.setTrademark(book.publisher());
        resource.setStock(faker.number().numberBetween(0, 100));
        resource.setPrice(faker.number().randomDouble(2, 15000, 300000));
        resource.setDescription("Author: " + book.author() + " Genre: " + book.genre() + " Publisher: " + book.publisher());
        resource.setTags(book.genre());
        resource.setActive(true);
        return resource;
    }

    /**
     * Parse response as List of Resources using the Resource model
     *
     * @param response rest-assured
     * @return List<Resource> list of resources (model)
     */
    public List<Resource> getResourcesEntity(Response response) {
        JsonPath jsonPath = response.jsonPath();
        return jsonPath.getList("", Resource.class);
    }
}
