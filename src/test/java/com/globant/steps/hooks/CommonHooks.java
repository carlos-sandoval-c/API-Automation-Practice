package com.globant.steps.hooks;

import com.globant.utils.constants.Constants;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.restassured.RestAssured;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CommonHooks {
    private static final Logger logger = LogManager.getLogger(CommonHooks.class);

    @Before
    public void testStart(Scenario scenario) {
        logger.info("*****************************************************************************************");
        logger.info("	Scenario: " + scenario.getName());
        logger.info("	Tags: " + scenario.getSourceTagNames());
        logger.info("*****************************************************************************************");
        RestAssured.baseURI = Constants.BASE_URL;
    }

    @After
    public void cleanUp(Scenario scenario){
        logger.info("*****************************************************************************************");
        logger.info("	Scenario finished: " + scenario.getName() + " | " + scenario.getStatus());
        logger.info("*****************************************************************************************");
    }
}

