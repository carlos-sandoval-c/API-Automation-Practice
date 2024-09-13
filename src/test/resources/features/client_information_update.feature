@active
Feature: Client information update
  As a user of the platform,
  I want to be able to update my personal information easily and quickly,
  so that I can reflect my most recent data and ensure it accurate.

  @smoke
  Scenario: Update client phone number
    Given there are at least 10 registered clients in the system
    When I retrieve the details of the client with name "Laura"
    And I save the current client phone number
    And I send a PUT request to update the saved client
    Then the response should have a status code of 200
    And validates the response with client JSON schema
    And delete all the registered clients

  @smoke @regression
  Scenario: Update and delete new client
    Given that the system is active
    And I have random user information
    When I send a POST request to create a client
    And retrieve the details of last client created
    And change email parameter of created client
    When I send a PUT request to update the saved client
    And delete the created client
    Then the response should have a status code of 200