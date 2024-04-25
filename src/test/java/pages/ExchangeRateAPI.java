package pages;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.Map;

public class ExchangeRateAPI {

    private Response response;

    public void makeAPICall(String url) {
        response = RestAssured.get(url);
    }

    public int getStatusCode() {
        return response.getStatusCode();
    }

    public String getResponseBody(String key) {
        return response.jsonPath().getString(key);
    }

    public double getUSDPriceAgainstAED() {
        return response.jsonPath().getDouble("rates.AED");
    }

    public long getResponseTime() {
        return response.time();
    }
    public int getNumberOfCurrencyPairs() {

        Map<String, Double> rates = response.jsonPath().getMap("rates");
        return rates.size();
    }

    public String getApiResponse(String url) {
        Response response = RestAssured.get(url);
        return response.getBody().asString();

    }



}
