Feature: API end point testing


  @positive
  Scenario Outline: Test API GET,POST, PUT, and PATCH Method
    Given user have a valid API endpoint
    When user perform a "<http method>" request to endpoint
    Then user should receive a "<expected status code>" status code
    And user sees receive a "<Expected message>" in the response body

    Examples:
      | http method | expected status code | Expected message   |
      | GET         | 200                  | cola               |
      | POST        | 405                  | Method Not Allowed |
      | PUT         | 405                  | Method Not Allowed |
      | PATCH       | 405                  | Method Not Allowed |


  @negative
  Scenario Outline: test API with invalid endpoint
    Given user sends an invalid API endpoint
    When user perform a "<http method>" request to "<invalidEndpoints>"
    Then user should receive a "<expected_status_code>"

    Examples:
      | http method | invalidEndpoints | expected_status_code |
      | GET         | car              | 404                  |
      | GET         | leasePlan        | 404                  |
      | GET         | house            | 404                  |
      | GET         | bicycle          | 404                  |
