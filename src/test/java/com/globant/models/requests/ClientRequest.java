package com.globant.models.requests;

import com.globant.models.common.Client;
import com.globant.models.headers.HeadersBuilder;
import com.globant.utils.baseRequest.BaseRequest;
import com.globant.utils.constants.Constants;
import io.restassured.response.Response;

public class ClientRequest extends BaseRequest {
    private String endpoint;

    public Response getClients() {
        endpoint = String.format(Constants.API_URL, Constants.CLIENTS_ENDPOINT);
        return requestGet(endpoint, HeadersBuilder.getBaseHeaders());
    }

    public Response getClient(String clientId) {
        endpoint = String.format(Constants.API_URL_WITH_PARAM, Constants.CLIENTS_ENDPOINT, clientId);
        return requestGet(endpoint, HeadersBuilder.getBaseHeaders());
    }

    public Response createClient(Client client) {
        endpoint = String.format(Constants.API_URL, Constants.CLIENTS_ENDPOINT);
        return requestPost(endpoint, HeadersBuilder.getBaseHeaders(), client);
    }

    public Response updateClient(String clientId, Client client) {
        endpoint = String.format(Constants.API_URL_WITH_PARAM, Constants.CLIENTS_ENDPOINT, clientId);
        return requestPut(endpoint, HeadersBuilder.getBaseHeaders(), client);
    }

    public Response deleteClient(String clientId) {
        endpoint = String.format(Constants.API_URL_WITH_PARAM, Constants.CLIENTS_ENDPOINT, clientId);
        return requestDelete(endpoint, HeadersBuilder.getBaseHeaders());
    }
}
