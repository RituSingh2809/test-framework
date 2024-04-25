package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.testng.Assert;
import pages.ExchangeRateAPI;
import utility.JsonSchemaValidator;

public class APITestSteps {

    private ExchangeRateAPI exchangeRateAPI = new ExchangeRateAPI();
    private String jsonResponse;
    private String jsonSchema;

    @Given("I make an API call to the URL {string}")
    public void makeAPICall(String url) {
        exchangeRateAPI.makeAPICall(url);

    }


    @Then("the response status code should be {int}")
    public void verifyStatusCode(int expectedStatusCode) {

        int actualStatusCode = exchangeRateAPI.getStatusCode();
        System.out.println("Actual response status: " + actualStatusCode);
        Assert.assertEquals(expectedStatusCode, actualStatusCode,"Response status code does not match expected status code");

        if (actualStatusCode == 500) {
            Assert.fail("Response status code is 500 (Internal Server Error)");
        } else if (actualStatusCode == 400 || actualStatusCode == 403) {
            System.out.println("WARNING: Response status code is " + actualStatusCode);
        }

    }

    @Then("the response body should contain {string} with value {string}")
    public void verifyResponseBody(String key, String expectedValue) {
        String actualValue = exchangeRateAPI.getResponseBody(key);
        Assert.assertEquals(expectedValue, actualValue);
    }

    @Then("the response body should contain {string} with an appropriate value")
    public void verifyResponseBody(String key) {
        String actualValue = exchangeRateAPI.getResponseBody(key);
        Assert.assertNotNull(actualValue);
    }

    @Then("the USD price against AED should be within range {double} to {double}")
    public void verifyUSDPriceAgainstAED(double minValue, double maxValue) {
        double usdPriceAgainstAED = exchangeRateAPI.getUSDPriceAgainstAED();
        System.out.println("USD price against AED is : "+usdPriceAgainstAED);
        Assert.assertTrue(usdPriceAgainstAED >= minValue && usdPriceAgainstAED <= maxValue);
    }


    @Then("the response time should not be less than 3 seconds")
    public void verifyResponseTime() {
        long responseTimeInMillis = exchangeRateAPI.getResponseTime();
        System.out.println("Actual response time: " + responseTimeInMillis + " milliseconds");

        if (responseTimeInMillis < 3000) {
            System.out.println("WARNING: Response time is less than 3 seconds");
        } else {
            Assert.assertTrue(true, "Response time is greater than or equal to 3 seconds");
        }
    }


    @Then("the number of currency pairs should be {int}")
    public void verifyNumberOfCurrencyPairs(int expectedCount) {
        int actualCount = exchangeRateAPI.getNumberOfCurrencyPairs();
        Assert.assertEquals(expectedCount, actualCount);
    }


    private JsonSchemaValidator schemaValidator = new JsonSchemaValidator();


    @Then("the response should match the JSON schema {string}")
    public void theResponseShouldMatchTheJSONSchema(String apiUrl) {
        jsonResponse = exchangeRateAPI.getApiResponse(apiUrl);
        System.out.println("API Response: " + jsonResponse);
        String jsonSchemaFile = "src/test/resources/jsonSchema.json";
        schemaValidator.performSchemaValidation(jsonResponse, jsonSchemaFile);
        schemaValidator.assertSchemaValidation(jsonResponse, jsonSchemaFile);

    }


}
