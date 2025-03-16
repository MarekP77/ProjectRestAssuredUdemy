package test;

import files.ReUsableMethods;
import io.restassured.RestAssured;
import org.testng.annotations.Test;
import pojo.Place.AddPlace;
import pojo.Place.Location;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class AddPutPlaceWithSerializationTest_9 {
    @Test()
    public void setAddPlace() {
        AddPlace addPlace = new AddPlace();
        addPlace.setAccuracy(50);
        addPlace.setName("Frontline house");
        addPlace.setPhone_number("(+91) 983 893 3937");
        addPlace.setAddress("29, side layout, cohen 09");
        addPlace.setWebsite("http://google.com");
        addPlace.setLanguage("French-IN");

        List<String> typesList = new ArrayList<>();
        typesList.add("shoe park");
        typesList.add("shop");
        addPlace.setTypes(typesList);

        Location location = new Location();
        location.setLat(-38.383494);
        location.setLng(33.427362);
        addPlace.setLocation(location);

        RestAssured.baseURI = "https://rahulshettyacademy.com";

        String responseAddPlace = given().filter(new ReUsableMethods.TimingFilter()).log().all()
                .queryParam("key", "qaclick123")
                .header("Content-Type", "application/json")
                .body(addPlace)
                .when()
                .post("maps/api/place/add/json")
                .then()
                .assertThat()
                .statusCode(200)
                .body("scope", equalTo("APP"))
                .header("Server", "Apache/2.4.52 (Ubuntu)")
                .extract().response().asString();

        System.out.println(responseAddPlace);

    }
}
