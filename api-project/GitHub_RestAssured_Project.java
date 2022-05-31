package org.example;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class GitHub_RestAssured_Project {

    //  Git hub Rest Assured API project questions: https://docs.google.com/document/d/16W1FFexSSuW9Zn9cvddBtNK0yyR07sXeAjO7mvMmDcc/edit

    public static final String BASE_URI = "https://api.github.com";
    RequestSpecification reqSpec;
    String sshKey;
    int sshId = 0;

    @BeforeClass
    void setup() {
        /*  for generating github token:
            Settings>Developer settings>Personal access tokens>Generate new token*/
        reqSpec = new RequestSpecBuilder().setContentType(ContentType.JSON).addHeader("Authorization", "token ghp_##").setBaseUri(BASE_URI).build();

        /*  to generate sshkey: ssh-keygen -t ed25519 -C "example@email.com" and then go to the folder where key is generated and
            check for .pub file for public sshkey.*/
        sshKey = "ssh-##";
    }

    @Test(priority = 0)
    public void postKey() {

        //request body
        String reqBody = "{\n" +
                "    \"title\": \"TestAPIKey\",\n" +
                "    \"key\": \"" + sshKey + "\"\n" +
                "}\n";

        //posting the user keys
        Response res = given().spec(reqSpec).body(reqBody).when().post(" /user/keys");

        //printing response to console
        String resBody = res.getBody().asPrettyString();
        System.out.println(resBody);

        //extracting sshID and saving it to the variable declared at the beginning
        sshId = res.then().extract().path("id");
        System.out.println("sshid=" + sshId);

        //Assertion
        res.then().statusCode(201);
    }

    @Test(priority = 1)
    public void getKey() {
        //getting the user keys
        Response res = given().log().all().spec(reqSpec).when().get(" /user/keys");

        //printing response to console
        String resBody = res.getBody().asPrettyString();
        System.out.println(resBody);

        //Assertion
        res.then().statusCode(200);

        //getting sshid through get request which returns arraylist response if sshid is 0 somehow
        if (sshId == 0) {
            String sshIds = res.then().extract().path("id").toString().replace("[", "").replace("]", "");
            sshId = Integer.valueOf(sshIds);
            System.out.println("sshid converted=" + sshId);
        }

    }

    @Test(priority = 2)
    public void deleteKey() {
        //deleting the keyId
        Response res = given().spec(reqSpec).pathParam("keyId", sshId).when().delete("/user/keys/{keyId}");

        //printing response to console
        String resBody = res.getBody().asPrettyString();
        System.out.println(resBody);

        //Assertion
        res.then().statusCode(204);

    }

}
