package com.globant.steps;

import com.github.javafaker.Faker;
import com.globant.models.common.Client;
import com.globant.models.requests.ClientRequest;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;

public class ClientSteps {
    private static final Logger logger = LogManager.getLogger(ClientSteps.class);
    private final ClientRequest clientRequest = new ClientRequest();

    private Response response;
    private Client client;

    private Client oldClient;

    private List<Client> secureGetClientsList() {
        response = clientRequest.getClients();
        logger.info(response.jsonPath().prettify());
        Assert.assertNotNull(response);
        Assert.assertEquals(200, response.statusCode());

        List<Client> clientsList = clientRequest.getClientsEntity(response);
        Assert.assertNotNull(clientsList);
        return clientsList;
    }

    @Given("that the system is active")
    public void thatTheSystemIsActive() {
        response = clientRequest.getClients();
        Assert.assertEquals(200, response.statusCode());
    }

    @Given("there are at least {int} registered clients in the system")
    public void verifyAtLeastClientsRegistered(int minClients) {
        int currentRegisteredClients = secureGetClientsList().size();
        while (currentRegisteredClients < minClients) {
            response = clientRequest.createClient(clientRequest.generateRandomClient());
            logger.info("trying to create new user");
            logger.info(response.statusCode());
            Assert.assertEquals(201, response.statusCode());
            currentRegisteredClients++;
        }
    }

    @Given("I have random user information")
    public void randomUserGeneration() {
        client = clientRequest.generateRandomClient();
        Assert.assertNotNull(client);
    }

    @When("I retrieve the details of the client with name {string}")
    public void verifyAndSaveClientWithName(String firstName) {
        List<Client> clientsList = secureGetClientsList();
        List<Client> clientsWithSelectedFirstName = new ArrayList<>();

        for (Client c : clientsList) {
            if (c.getName().equalsIgnoreCase(firstName))
                clientsWithSelectedFirstName.add(c);
        }

        if (clientsWithSelectedFirstName.isEmpty()) {
            logger.info("Name not registered, creating new user");
            Client newClient = clientRequest.generateRandomClient();
            newClient.setName(firstName);

            response = clientRequest.createClient(newClient);
            Assert.assertEquals(201, response.statusCode());
            client = clientRequest.getClientEntity(response);
            return;
        }
        client = clientsWithSelectedFirstName.get(0);
    }

    @When("I save the current client phone number")
    public void savePhoneNumberOfSelectedClient() {
        Assert.assertNotNull(client);
        oldClient = client;
        logger.info("Number saved!");
        client.setPhone(new Faker().phoneNumber().cellPhone());
        logger.info("Local client has the new phone");
    }

    @When("I send a PUT request to update the saved client")
    public void updateSavedClient() {
        response = clientRequest.updateClient(oldClient.getId(), client);
        Assert.assertEquals(200, response.statusCode());
        userValidatesResponseWithClientJSONSchema();

        Client onServiceClient = clientRequest.getClientEntity(response);
        Assert.assertNotSame(oldClient.getPhone(), onServiceClient.getPhone());
    }

    @When("I send a POST request to create a client")
    public void createANewClient() {
        response = clientRequest.createClient(client);
        Assert.assertEquals(201, response.statusCode());
        logger.info("New user created");
        client = clientRequest.getClientEntity(response);
    }

    @When("retrieve the details of last client created")
    public void retrieveTheDetailsOfLastClientCreated() {
        response = clientRequest.getClient(client.getId());
        userValidatesResponseWithClientJSONSchema();
        logger.info("Created users has been retrieved");
        Assert.assertEquals(200, response.statusCode());
    }

    @When("change email parameter of created client")
    public void changeEmailParameterOfClient() {
        oldClient = client;
        client.setEmail(new Faker().internet().emailAddress());
    }

    @When("delete the created client")
    public void deleteSavedClient() {
        Assert.assertNotNull(client.getId());
        response = clientRequest.deleteClient(client.getId());
        Assert.assertEquals(200, response.statusCode());
        client = oldClient = null;
    }

    @Then("the client response should have a status code of {int}")
    public void theResponseShouldHaveAStatusCodeOf(int statusCode) {
        Assert.assertEquals(statusCode, response.statusCode());
    }

    @Then("validates the response with client JSON schema")
    public void userValidatesResponseWithClientJSONSchema() {
        String path = "schemas/client/clientSchema.json";
        Assert.assertTrue(clientRequest.isValidSchema(response, path));
        logger.info("Successfully Validated schema from Client object");
    }

    @Then("validates the response with client list JSON schema")
    public void userValidatesResponseWithClientListJSONSchema() {
        String path = "schemas/client/clientListSchema.json";
        Assert.assertTrue(clientRequest.isValidSchema(response, path));
        logger.info("Successfully Validated schema from Client List object");
    }

    @Then("delete all the registered clients")
    public void deleteAllTheRegisteredClients() {
        List<Client> allClientsList = secureGetClientsList();
        for (Client c : allClientsList) {
            response = clientRequest.deleteClient(c.getId());
            Assert.assertEquals(200, response.statusCode());
        }
    }

}
