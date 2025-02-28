package test;

import files.Payload;
import files.ReUsableMethods;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class E2EAddPutPlaceTest_1 {
    public static void main(String[] args) {
        // TODO Auto-generated method sub
        // validate if Add Place API is working as expected
        //Add place -> Update Place with New Address -> Get Place to validate if New address is present in response

        //given - all input details
        //when - Submit the API
        //then - validate the response

        baseURI = "https://rahulshettyacademy.com";
        String response = given().log().all()
                .queryParam("key", "qaclick123")
                .header("Content-Type", "application/json")
                .body(Payload.AddPlace())
                .when()
                .post("maps/api/place/add/json")
                .then()//.log().all()
                .assertThat()
                .statusCode(200)
                .body("scope", equalTo("APP"))
                .header("Server", "Apache/2.4.52 (Ubuntu)")
                .extract().response().asString();

        System.out.println(response);

        JsonPath json = new JsonPath(response); //for parsing JSON
        String placeId = json.getString("place_id");

        System.out.println("Place Id: " + placeId);

        String newAddress = "70 Summer walk, USA";

        given().log().all()
                .queryParam("key", "qaclick123")
                .header("Content-Type", "application/json")
                .body("{\n" +
                      "\"place_id\":\""+placeId+"\",\n" +
                      "\"address\":\""+newAddress+"\",\n" +
                      "\"key\":\"qaclick123\"\n" +
                      "}")
                .when()
                .put("maps/api/place/update/json")
                .then()
                .assertThat()
                .statusCode(200)
                .body("msg", equalTo("Address successfully updated"));

        //Get Place

        String getPlaceResponse = given().log().all()
                .queryParam("key", "qaclick123")
                .queryParam("place_id", placeId)
                .when()
                .get("maps/api/place/get/json")
                .then().log().all()
                .assertThat()
                .statusCode(200)
                .extract().response().asString();
        JsonPath jsonGet = ReUsableMethods.rawToJson(getPlaceResponse);
        String actualAddress = jsonGet.getString("address");

        System.out.println(getPlaceResponse);
        System.out.println(actualAddress);
        Assert.assertEquals(actualAddress, newAddress);



        //Cucumber JUnit, TestNG


    }
}