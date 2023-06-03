Feature: Price validation

  @positive
  Scenario: Ah cola zero price validation
    When user calls endpoint to "https://waarkoop-server.herokuapp.com/api/v1/search/demo/cola"
    And user sees content type is "application/json"
    Then user sees colas in the result
    Then user checks cola "https://www.ah.nl/producten/product/wi476710/ah-cola-zero" price "0.99" as expected



