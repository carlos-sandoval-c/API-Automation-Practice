package com.globant.steps;

import com.globant.models.common.Client;
import com.globant.models.common.Resource;
import com.globant.models.requests.ResourcesRequest;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;

import java.util.List;

public class ResourceSteps {
    private static final Logger logger = LogManager.getLogger(ResourceSteps.class);
    private final ResourcesRequest resourcesRequest = new ResourcesRequest();

    private Response response;
    private Resource resource;
    private List<Resource> resourcesList;

    private List<Resource> secureGetResourcesList() {
        response = resourcesRequest.getResources();
        logger.info(response.jsonPath().prettify());
        Assert.assertNotNull(response);
        Assert.assertEquals(200, response.statusCode());

        List<Resource> resourcesList = resourcesRequest.getResourcesEntity(response);
        Assert.assertNotNull(resourcesList);
        return resourcesList;
    }

    @Given("there are at least {int} registered resources in the system")
    public void verifyAtLeastClientsRegistered(int minResource) {
        int currentRegisteredResource = secureGetResourcesList().size();
        while (currentRegisteredResource < minResource) {
            response = resourcesRequest.createResource(resourcesRequest.generateRandomResource());
            logger.info("trying to create new resource");
            logger.info(response.statusCode());
            Assert.assertEquals(201, response.statusCode());
            currentRegisteredResource++;
        }
    }

    @When("I retrieve all resource details")
    public void iRetrieveAllResourceDetails() {
        response = resourcesRequest.getResources();
        logger.info(response.getBody());
        Assert.assertEquals(200, response.statusCode());
        resourcesList = resourcesRequest.getResourcesEntity(response);
    }

    @When("I set as inactive all resources")
    public void iSetAsInactiveAllResources() {
        Assert.assertNotNull(resourcesList);
        for (Resource r : resourcesList) {
            Assert.assertNotNull(r);
            r.setActive(false);
        }
    }

    @When("I send a PUT request to update the resources list")
    public void iSendAPUTRequestToUpdateTheResourcesList() {
        Assert.assertNotNull(resourcesList);
        for (Resource r : resourcesList) {
            Assert.assertNotNull(r);
            response = resourcesRequest.updateResource(r.getId(), r);
            Assert.assertEquals(200, response.getStatusCode());
        }
    }

    @When("I get the latest added resource")
    public void iGetTheLatestAddedResource() {
        resourcesList = secureGetResourcesList();
        Resource latestAddedResource = null;
        for (Resource r : resourcesList) {
            if (latestAddedResource == null || Integer.parseInt(r.getId()) > Integer.parseInt(latestAddedResource.getId()))
                latestAddedResource = r;
        }

        resource = latestAddedResource;
    }

    @When("I update all parameters of this resource")
    public void iUpdateAllParametersOfThisResource() {
        Resource newResource = resourcesRequest.generateRandomResource();
        newResource.setId(resource.getId());
        resource = newResource;
    }

    @When("I send a PUT request to update the resource")
    public void iSendAPUTRequestToUpdateTheResource() {
        response = resourcesRequest.updateResource(resource.getId(), resource);
        Assert.assertEquals(200, response.getStatusCode());
    }

    @Then("the resource response should have a status code of {int}")
    public void theResponseShouldHaveAStatusCodeOf(int statusCode) {
        Assert.assertEquals(statusCode, response.statusCode());
    }

    @Then("validates the response with resource JSON schema")
    public void userValidatesResponseWithResourceJSONSchema() {
        String path = "schemas/resource/resourcesSchema.json";
        Assert.assertNotNull(response);
        Assert.assertTrue(resourcesRequest.isValidSchema(response, path));
        logger.info("Successfully Validated schema from Resource object");
    }

    @Then("validates the response with resources list JSON schema")
    public void userValidatesResponseWithClientListJSONSchema() {
        String path = "schemas/resource/resourcesListSchema.json";
        Assert.assertNotNull(response);
        Assert.assertTrue(resourcesRequest.isValidSchema(response, path));
        logger.info("Successfully Validated schema from Resource List object");
    }

    @Then("delete all the registered resources")
    public void deleteAllTheRegisteredResources() {
        List<Resource> allResourceList = secureGetResourcesList();
        for (Resource r : allResourceList) {
            response = resourcesRequest.deleteResource(r.getId());
            Assert.assertEquals(200, response.statusCode());
        }
    }
}
