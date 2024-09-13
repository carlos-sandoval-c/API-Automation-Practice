package com.globant.models.requests;

import com.github.javafaker.Address;
import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import com.globant.models.common.Client;
import com.globant.models.headers.HeadersBuilder;
import com.globant.utils.baseRequest.BaseRequest;
import com.globant.utils.constants.Constants;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.util.List;

public class ClientRequest extends BaseRequest {
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

    public Client generateRandomClient() {
        Faker faker = new Faker();
        Client client = new Client();

        Name fakeName = faker.name();
        Address fakeAddress = faker.address();

        client.setName(fakeName.firstName());
        client.setLastName(fakeName.lastName());
        client.setCountry(fakeAddress.country());
        client.setCity(fakeAddress.city());
        client.setEmail(faker.internet().emailAddress());
        client.setPhone(faker.phoneNumber().cellPhone());
        return client;
    }

    public Client getClientEntity(Response response) {
        return response.as(Client.class);
    }

    public List<Client> getClientsEntity(Response response) {
        JsonPath jsonPath = response.jsonPath();
        return jsonPath.getList("", Client.class);
    }

}
