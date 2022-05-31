package org.example;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Activity2 {

    final static String ROOT_URI = "https://petstore.swagger.io/v2/user";

    @Test(priority = 1)
    void addNewPet() throws IOException {

        //reading request body from external file
        File fileJson = new File("D:/FST_IBM/RestAssuredAPI/resources/userPet.json");
        FileInputStream inputJSON = new FileInputStream(fileJson);
        String reqBody = new String(inputJSON.readAllBytes());

        Response response = given().contentType(ContentType.JSON).body(reqBody).when().post(ROOT_URI);
        inputJSON.close();
        System.out.println(response.getBody().asPrettyString());

        //assertions
        response.then().body("code", equalTo(200));
        response.then().body("message", equalTo("9902"));


    }

    @Test(priority = 2)
    void getPet() throws IOException {

        String uname = "Saley";

        Response response = given().contentType(ContentType.JSON).pathParam("username", uname).when().get(ROOT_URI + "/{username}");

        //writing response body to external file
        File outputJSON = new File("D:/FST_IBM/RestAssuredAPI/resources/petOutputJSON.json");
        String resBody = response.asPrettyString();
        outputJSON.createNewFile();
        FileWriter fWriter = new FileWriter(outputJSON.getPath());
        fWriter.write(resBody);
        fWriter.close();

        //assertions
        response.then().statusCode(200);
        response.then().body("id", equalTo(9902));
        response.then().body("username", equalTo("Saley"));
        response.then().body("firstName", equalTo("Saley"));
        response.then().body("lastName", equalTo("Casa"));
        response.then().body("email", equalTo("saleycasa@mail.com"));
        response.then().body("password", equalTo("password123"));
        response.then().body("phone", equalTo("9812763450"));

    }

    @Test(priority = 3)
    void deletePet() {

        String uname = "Saley";
        Response response = given().contentType(ContentType.JSON).pathParam("username", uname).when().delete(ROOT_URI + "/{username}");
        System.out.println("del res" + response.getBody().asPrettyString());

        //assertions
        response.then().body("code", equalTo(200));
        response.then().body("message", equalTo("Saley"));

        //asserting deletion using get() method against http code- 404
        response = given().contentType(ContentType.JSON).pathParam("username", uname).when().get(ROOT_URI + "/{username}");
        System.out.println(response.getBody().asPrettyString());

        //assertions
        response.then().statusCode(404);
        response.then().body("code", equalTo(1));
        response.then().body("message", equalTo("User not found"));

    }
}
