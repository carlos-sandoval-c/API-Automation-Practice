@active
Feature: Resources information update
  As a user of the platform,
  I want to be able to update and consult resource information easily and quickly,
  so that I can reflect the most recent data and ensure it accurate.

  @smoke
  Scenario: Get list of active resources
    Given there are at least 5 registered resources in the system
    When I retrieve all resource details
    And I set as inactive all resources
    And I send a PUT request to update the resources list
    And I retrieve all resource details
    Then the resource response should have a status code of 200
    And validates the response with resources list JSON schema