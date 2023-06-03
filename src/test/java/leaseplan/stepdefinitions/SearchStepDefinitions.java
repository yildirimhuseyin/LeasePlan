package leaseplan.stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import leaseplan.pojo.Cola;
import leaseplan.pojo.Colas;
import leaseplan.utilities.APIUtils;
import org.junit.Assert;

import static net.serenitybdd.rest.SerenityRest.*;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SearchStepDefinitions {


    private Response response; // create one response object to store response in it
    Colas colas = new Colas();// create object of Colas class and get all colas

    @When("user calls endpoint to {string}")
    public void userCallsEndpointTo(String endPoint) {
        response = given().when().get(endPoint);
    }

    @Then("user sees colas in the result")
    public void userSeesColasInTheResult() {
        restAssuredThat(response -> response.statusCode(200));
    }

    @And("user sees content type is {string}")
    public void userSeesContentTypeIs(String contentType) {
        restAssuredThat(response -> response.contentType(contentType));
    }

    @Then("user checks cola {string} price {string} as expected")
    public void userChecksColaPriceAsExpected(String colaUrl, String price) {

        for (Cola cola : colas.getAllColas() ) {
            if (cola.getUrl().equals(colaUrl)) {
                Assert.assertEquals(price, String.valueOf(cola.getPrice()) );
            }

        }
    }

    @Given("user have a valid API endpoint")
    public void user_have_a_valid_api_endpoint() {
        RestAssured.baseURI = "https://waarkoop-server.herokuapp.com/api/v1/search/demo/cola";
    }

    @When("user perform a {string} request to endpoint")
    public void user_perform_a_request_to_endpoint(String method) {
        if (method.equals("GET")) {
            response = get();
        } else if (method.equalsIgnoreCase("POST")) {
            response = post();
        } else if (method.equalsIgnoreCase("PUT")) {
            response = put();
        } else if (method.equalsIgnoreCase("PATCH")) {
            response = put();
        }
    }



    @Given("user sends an invalid API endpoint")
    public void userSendsAnInvalidAPIEndpoint() {
        RestAssured.baseURI = "https://waarkoop-server.herokuapp.com/api/v1/search/demo/";
    }

    @When("user perform a {string} request to {string}")
    public void userPerformARequestTo(String httpMethod, String invalidEndpoint) {
        if (httpMethod.equalsIgnoreCase("get")) {
            response = given().request(Method.GET, invalidEndpoint);
        }
    }

    @Then("user should receive a {string}")
    public void userShouldReceiveA(String expectedStatusCode) {
        int actualStatusCode = response.statusCode();
        System.out.println("actualStatusCode = " + actualStatusCode);
        assertEquals(Integer.parseInt(expectedStatusCode), actualStatusCode);
    }

    @Then("user should receive a {string} status code")
    public void user_should_receive_a_status_code(String expectedStatusCode) {
        int actualStatusCode = response.getStatusCode();
        assertEquals(Integer.parseInt(expectedStatusCode), actualStatusCode);
    }

    @Then("user sees receive a {string} in the response body")
    public void user_sees_receive_a_in_the_response_body(String expectedMessage) {
        String responseBody = response.body().asString();
        assertTrue(responseBody.contains(expectedMessage));
    }



}
