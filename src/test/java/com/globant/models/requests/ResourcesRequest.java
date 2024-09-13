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

public class ResourcesRequest extends BaseRequest {
    public Response getResources() {
        endpoint = String.format(Constants.API_URL, Constants.RESOURCES_ENDPOINT);
        return requestGet(endpoint, HeadersBuilder.getBaseHeaders());
    }

    public Response getResource(String resourceId) {
        endpoint = String.format(Constants.API_URL_WITH_PARAM, Constants.RESOURCES_ENDPOINT, resourceId);
        return requestGet(endpoint, HeadersBuilder.getBaseHeaders());
    }

    public Response createResource(Resource resource) {
        endpoint = String.format(Constants.API_URL, Constants.RESOURCES_ENDPOINT);
        return requestPost(endpoint, HeadersBuilder.getBaseHeaders(), resource);
    }

    public Response updateResource(String resourceId, Resource resource) {
        endpoint = String.format(Constants.API_URL_WITH_PARAM, Constants.RESOURCES_ENDPOINT, resourceId);
        return requestPut(endpoint, HeadersBuilder.getBaseHeaders(), resource);
    }

    public Response deleteResource(String resourceId) {
        endpoint = String.format(Constants.API_URL_WITH_PARAM, Constants.RESOURCES_ENDPOINT, resourceId);
        return requestDelete(endpoint, HeadersBuilder.getBaseHeaders());
    }

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

    public Resource getResourceEntity(Response response) {
        return response.as(Resource.class);
    }

    public List<Resource> getResourcesEntity(Response response) {
        JsonPath jsonPath = response.jsonPath();
        return jsonPath.getList("", Resource.class);
    }
}
