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

/**
 * This class will be in charge of implementing all the requests that are related to Clients.
 *
 * @author Carlos Sandoval <c.sandoval@globant.com>
 * @version 0.1
 * @since 0.1
 */
public class ClientRequest extends BaseRequest {
    /**
     * Get Clients list
     *
     * @return rest-assured response
     */
    public Response getClients() {
        endpoint = String.format(Constants.API_URL, Constants.CLIENTS_ENDPOINT);
        return requestGet(endpoint, HeadersBuilder.getBaseHeaders());
    }

    /**
     * Get Client by Id
     *
     * @param clientId string
     * @return rest-assured response
     */
    public Response getClient(String clientId) {
        endpoint = String.format(Constants.API_URL_WITH_PARAM, Constants.CLIENTS_ENDPOINT, clientId);
        return requestGet(endpoint, HeadersBuilder.getBaseHeaders());
    }

    /**
     * Create a new client
     *
     * @param client model
     * @return rest-assured response
     */
    public Response createClient(Client client) {
        endpoint = String.format(Constants.API_URL, Constants.CLIENTS_ENDPOINT);
        return requestPost(endpoint, HeadersBuilder.getBaseHeaders(), client);
    }

    /**
     * Update client using Id
     *
     * @param clientId string
     * @param client   model
     * @return rest-assured response
     */
    public Response updateClient(String clientId, Client client) {
        endpoint = String.format(Constants.API_URL_WITH_PARAM, Constants.CLIENTS_ENDPOINT, clientId);
        return requestPut(endpoint, HeadersBuilder.getBaseHeaders(), client);
    }

    /**
     * Delete Client by Id
     *
     * @param clientId string
     * @return rest-assured response
     */
    public Response deleteClient(String clientId) {
        endpoint = String.format(Constants.API_URL_WITH_PARAM, Constants.CLIENTS_ENDPOINT, clientId);
        return requestDelete(endpoint, HeadersBuilder.getBaseHeaders());
    }

    /**
     * Create a new client (model) with random data
     *
     * @return client model
     */
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

    /**
     * Parse response as Client model
     *
     * @param response rest-assured
     * @return Client model
     */
    public Client getClientEntity(Response response) {
        return response.as(Client.class);
    }

    /**
     * Parse response as List of Clients using the Client model
     *
     * @param response rest-assured
     * @return List<Client> list of clients (model)
     */
    public List<Client> getClientsEntity(Response response) {
        JsonPath jsonPath = response.jsonPath();
        return jsonPath.getList("", Client.class);
    }

}
