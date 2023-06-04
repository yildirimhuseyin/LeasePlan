package leaseplan.utilities;

import io.cucumber.java.it.Ma;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import leaseplan.pojo.*;

import java.util.List;
import java.util.Map;

public class APIUtils {



    /**
     * This method accepts one argument which should be a valid endpoint
     * and stores all the response body inside a  List<Map<String, Object>> object
     * @return
     */

    public static List<Map<String,Object>> getsAllObjects(String endPoint){

        if (!(endPoint.equals("cola") || endPoint.equals("orange")
                || endPoint.equals("pasta") || endPoint.equals("apple"))) {
            throw new RuntimeException("Invalid endpoint");
        }

        Response response = RestAssured.get("https://waarkoop-server.herokuapp.com/api/v1/search/demo/" +endPoint );
        List<Map<String, Object>> allObject = response.body().as(List.class);


        return allObject;

    }

    /**
     * This method gets all colas and stores them inside a List<Cola> object
     * @return
     */
    public static List<Cola> getAllColas() {
        Response response = RestAssured.get("https://waarkoop-server.herokuapp.com/api/v1/search/demo/cola" );
        List<Cola> allColas = response.jsonPath().getList("",Cola.class);

        return allColas;

    }


    /**
     * This method gets all colas and stores them inside a List<Apple> object
     * @return
     */
    public static List<Apple> getAllApples() {
        Response response = RestAssured.get("https://waarkoop-server.herokuapp.com/api/v1/search/demo/apple" );
        List<Apple> allApples = response.jsonPath().getList("", Apple.class);

        return allApples;

    }


    /**
     * This method gets all oranges and stores them inside a List<Orange> object
     * @return
     */
    public static List<Orange> getAllOrange() {
        Response response = RestAssured.get("https://waarkoop-server.herokuapp.com/api/v1/search/demo/orange" );
        List<Orange> allOrange = response.jsonPath().getList("",Orange.class);

        return allOrange;

    }



    /**
     * This method gets all pastas and stores them inside a List<Pasta> object
     * @return
     */
    public static List<Pasta> getAllPasta() {
        Response response = RestAssured.get("https://waarkoop-server.herokuapp.com/api/v1/search/demo/pasta" );
        List<Pasta> allPastas = response.jsonPath().getList("",Pasta.class);

        return allPastas;

    }




}
