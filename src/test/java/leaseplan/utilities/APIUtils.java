package leaseplan.utilities;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import leaseplan.pojo.*;

import java.util.List;

public class APIUtils {


    /**
     * This class gets all colas and stores them inside a List<Cola> object
     * @return
     */
    public static List<Cola> getAllColas() {
        Response response = RestAssured.get("https://waarkoop-server.herokuapp.com/api/v1/search/demo/cola" );
        List<Cola> allColas = response.jsonPath().getList("",Cola.class);

        return allColas;

    }


    /**
     * This class gets all colas and stores them inside a List<Apple> object
     * @return
     */
    public static List<Apple> getAllApples() {
        Response response = RestAssured.get("https://waarkoop-server.herokuapp.com/api/v1/search/demo/apple" );
        List<Apple> allApples = response.jsonPath().getList("", Apple.class);

        return allApples;

    }


    /**
     * This class gets all oranges and stores them inside a List<Orange> object
     * @return
     */
    public static List<Orange> getAllOrange() {
        Response response = RestAssured.get("https://waarkoop-server.herokuapp.com/api/v1/search/demo/orange" );
        List<Orange> allOrange = response.jsonPath().getList("",Orange.class);

        return allOrange;

    }



    /**
     * This class gets all pastas and stores them inside a List<Pasta> object
     * @return
     */
    public static List<Pasta> getAllPasta() {
        Response response = RestAssured.get("https://waarkoop-server.herokuapp.com/api/v1/search/demo/pasta" );
        List<Pasta> allPastas = response.jsonPath().getList("",Pasta.class);

        return allPastas;

    }




}
