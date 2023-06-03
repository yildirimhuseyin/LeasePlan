# Getting started with Serenity and Cucumber

Serenity BDD is a library that makes it easier to write high quality automated acceptance tests, with powerful reporting and living documentation features. It has strong support for both web testing with Selenium, and API testing using RestAssured.

Serenity strongly encourages good test automation design, and supports several design patterns, including classic Page Objects, the newer Lean Page Objects/ Action Classes approach, and the more sophisticated and flexible Screenplay pattern.

The latest version of Serenity supports Cucumber 5.5.

## The lease plan project
The best place to start with Serenity and Cucumber is to clone or download the LeasePlan project on Github ([https://github.com/yildirimhuseyin/LeasePlan.git]. 
### The project directory structure
The project has build scripts for Maven, and follows the standard directory structure used in most Serenity projects:
```Gherkin
src
  + github
    + workflows
      + maven.yml
  + test
    + java
      + pojo
      + Test runners  
      + stepDefinitions
        + SearchStepDefinitions
    + resources
      + features
        + Search
          + search_by_keyword.feature
  
             
```

Serenity 2.2.13 introduced integration with WebdriverManager to download webdriver binaries.

## The sample positive scenarios
In first scenario, user validates ahh colas price as expected. In second sample scenarios user test API methods, In third scenarios user test API with invalid end point  

```Gherkin
Feature: Price validation

  @positive
  Scenario: Ah cola zero price validation
    When user calls endpoint to "https://waarkoop-server.herokuapp.com/api/v1/search/demo/cola"
    And user sees content type is "application/json"
    Then user sees colas in the result
    Then user checks cola "https://www.ah.nl/producten/product/wi476710/ah-cola-zero" price "0.99" as expected

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

    

### The implementation of the code is inside the StepDefinitions class you can see sample implementaion of the scenarios below
    
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
```

Screenplay classes emphasise reusable components and a very readable declarative style, whereas Lean Page Objects and Action Classes (that you can see further down) opt for a more imperative style.

The `API utils` class stores reusable methods and variables. This methods used to gets all data with in given endpoints to store them in pojo classes you can find one example of the methods below
```java
    /**
 * This class gets all colas and stores them inside a List<Cola> object
 * @return
 */
public static List<Cola> getAllColas() {
        Response response = RestAssured.get("https://waarkoop-server.herokuapp.com/api/v1/search/demo/cola" );
        List<Cola> allColas = response.jsonPath().getList("",Cola.class);

        return allColas;

        }

```

The `pojo classes` allow us to create reusable and maintainable framework structure. To implement pojo(Custom java classes) classes add lombok and Jakson dependency which are below:

```
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.20</version>
        </dependency>
        <!-- https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind -->
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-databind</artifactId>
            <version>2.12.4</version>
        </dependency>
```
For the implementations of pojo create a class and initialize private of the instance that you want to store with exactly same name and same dataType that you want to store. Use reusable methods which are created in APIUtils to store data inside new created pojo. Examples below: 
```java

@Getter
@Setter //These comes from lombok library and autogenerated methods
@ToString
public class Cola {
    // this pojo class allow us to store single cola details in a java custom object
    private String provider;
    private String title;
    private String url;
    private String brand;
    private double price;
    private String unit;
    @JsonProperty("isPromo")
    private boolean isPromo;
    private String promoDetails;
    private String image;

}

```

to store with in a better it can be store inside another pojo classes. Example is below. Attentions!! You need to initialize class instance with methods that you created 
```java
@Getter
@Setter //These comes from lombok library and autogenerated methods
@ToString
public class Colas {
    public Colas() {
        this.allColas = APIUtils.getAllColas();// method that need to be created in ApiUtils
    }

    private List<Cola> allColas;// returns all the Colas. 

}
```

The Screenplay DSL is rich and flexible, and well suited to teams working on large test automation projects with many team members, and who are reasonably comfortable with Java and design patterns. 

### testRunnerClass.

 TestRunner class used to trigger scenarios. With the tags options it allows to choose which specific scenarios to run. Secondly, html cucumber report added to plugging to generate report:
```java
@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        plugin = {
                // below plugin allows to create html report
                "html:target/reports/cucumber-report.html",
        },
        features = "src/test/resources/features/search",
        glue = "leaseplan/stepdefinitions",
        // tags used to run specific scenarios like @smoke, @regression, @positive
        tags = "")

public class TestRunner {}
```
## serenity.properties file
This file used to store important source code like username, password or environments. It stores data in pairs format: 
```
serenity.project.name=leasePan demo project
```

### CI/CD implementations

Under the github.workflows package maven.yml filed designed to trigger test on GitHub Actions, and it will triggered each time when new codes added to master branch
It will also upload html reports to GitHub. It can be seen under artifacts in gitHub. maven yml file: 
```java
# This workflow will build a Java project with Maven
        # For more information see: https://help.github.com/actions/language-and-framework-guides/building-and-testing-java-with-maven

        name: Maven Build

        on:
        push:
        branches: [ master ]
        pull_request:
        branches: [ master ]

        jobs:
        build:

        runs-on: ubuntu-latest

        steps:
        - uses: actions/checkout@v2
      - name: Set up JDK 1.8
              uses: actions/setup-java@v1
        with:
                java-version: 1.8
                - name: Build with Maven
                run: mvn -B clean verify serenity:aggregate --file pom.xml

                - name: Run Tests
                run: mvn verify

                - name: Upload HTML Report
                uses: actions/upload-artifact@v2
        with:
                name: Html Cucumber Report
                path: target/reports/cucumber-report.html

```

## Pom.xml file 
This file used to store all dependency and libraries to run framework add following dependency to pom.xml file: 


```
             
    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <serenity.version>2.6.0</serenity.version>
        <serenity.maven.version>2.6.0</serenity.maven.version>
        <serenity.cucumber.version>2.6.0</serenity.cucumber.version>
        <encoding>UTF-8</encoding>
        <tags></tags>
        <webdriver.base.url></webdriver.base.url>
    </properties>

    <dependencies>
        <dependency>
            <groupId>net.serenity-bdd</groupId>
            <artifactId>serenity-core</artifactId>
            <version>${serenity.version}</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>net.serenity-bdd</groupId>
            <artifactId>serenity-cucumber6</artifactId>
            <version>${serenity.cucumber.version}</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>net.serenity-bdd</groupId>
            <artifactId>serenity-screenplay</artifactId>
            <version>${serenity.version}</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>net.serenity-bdd</groupId>
            <artifactId>serenity-screenplay-webdriver</artifactId>
            <version>${serenity.version}</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>net.serenity-bdd</groupId>
            <artifactId>serenity-ensure</artifactId>
            <version>${serenity.version}</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.13.1</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>ch.qos.logback</groupId>
            <artifactId>logback-classic</artifactId>
            <version>1.0.13</version>
        </dependency>
        <dependency>
            <groupId>org.assertj</groupId>
            <artifactId>assertj-core</artifactId>
            <version>3.6.2</version>
            <scope>test</scope>
        </dependency>

        <dependency>
            <groupId>net.serenity-bdd</groupId>
            <artifactId>serenity-rest-assured</artifactId>
            <version>2.6.0</version>
        </dependency>

        <dependency>
            <groupId>org.hamcrest</groupId>
            <artifactId>hamcrest-all</artifactId>
            <version>1.3</version>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.20</version>
        </dependency>
        <!-- https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind -->
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-databind</artifactId>
            <version>2.12.4</version>
        </dependency>


    </dependencies>

    <distributionManagement>
        <repository>
            <id>central</id>
            <name>a0jfsyiy82n4q-artifactory-primary-0-releases</name>
            <url>${env.MAVEN_REPO_URL}/default-maven-local</url>
        </repository>
        <snapshotRepository>
            <id>snapshots</id>
            <name>a0jfsyiy82n4q-artifactory-primary-0-snapshots</name>
            <url>${env.MAVEN_REPO_URL}/default-maven-local</url>
        </snapshotRepository>
    </distributionManagement>
```
