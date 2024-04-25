Feature: Test Exchange Rate API

  Scenario: Verify Successful API Call
    Given I make an API call to the URL "https://open.er-api.com/v6/latest/USD"
    Then the response status code should be 200
    And the response body should contain "result" with value "success"

  Scenario: Verify USD Price Against AED
    Given I make an API call to the URL "https://open.er-api.com/v6/latest/USD"
    Then the response status code should be 200
    And the response body should contain "rates.AED" with an appropriate value
    And the USD price against AED should be within range 3.6 to 3.7

  Scenario: Verify Response Time
    Given I make an API call to the URL "https://open.er-api.com/v6/latest/USD"
    Then the response status code should be 200
    And the response time should not be less than 3 seconds

  Scenario: Verify Number of Currency Pairs
    Given I make an API call to the URL "https://open.er-api.com/v6/latest/USD"
    Then the response status code should be 200
    And the number of currency pairs should be 162

  Scenario: Validate API response against JSON schema
    Given I make an API call to the URL "https://open.er-api.com/v6/latest/USD"
    Then the response should match the JSON schema "https://open.er-api.com/v6/latest/USD"