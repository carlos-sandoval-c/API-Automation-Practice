package com.globant.models.requests;

import com.globant.models.common.Resource;
import com.globant.models.headers.HeadersBuilder;
import com.globant.utils.baseRequest.BaseRequest;
import com.globant.utils.constants.Constants;
import io.restassured.response.Response;

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
}
