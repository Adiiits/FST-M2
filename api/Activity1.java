package org.example;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Activity1 {

    final static String ROOT_URI = "https://petstore.swagger.io/v2/pet";

    @Test(priority = 1)
    void addNewPet(){
        String reqBody = "{\n" +
                "  \"id\": 77232,\n" +
                "  \"name\": \"Riley\",\n" +
                "  \"status\": \"alive\"\n" +
                "}";

        Response response = given().contentType(ContentType.JSON).body(reqBody).when().post(ROOT_URI);

        //assertions
        response.then().body("id",equalTo(77232));
        response.then().body("name",equalTo("Riley"));
        response.then().body("status",equalTo("alive"));
    }

    @Test(priority = 2)
    void getPet(){

       // Response response = given().contentType(ContentType.JSON).when().pathParam("petId", "77232") // Set path parameter
        //        .get(ROOT_URI + "/{petId}");
        Response response = given().contentType(ContentType.JSON).when().get(ROOT_URI + "/77232");
        response.then().body("id", equalTo(77232));
        response.then().body("name", equalTo("Riley"));
        response.then().body("status", equalTo("alive"));
        System.out.println(response.getBody().asPrettyString());

    }

    @Test(priority = 3)
    void deletePet(){

        // Response response = given().contentType(ContentType.JSON).when().pathParam("petId", "77232") // Set path parameter
        //        .delete(ROOT_URI + "/{petId}");
        Response response = given().contentType(ContentType.JSON).when().delete(ROOT_URI + "/77232");
        response.then().body("code", equalTo(200));
        response.then().body("message", equalTo("77232"));

        response = given().contentType(ContentType.JSON).when().get(ROOT_URI + "/77232");
        System.out.println(response.getBody().asPrettyString());

        response.then().body("code", equalTo(1));
        response.then().body("message", equalTo("Pet not found"));

    }
}
