package test;

import files.ReUsableMethods;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.Test;
import pojo.AddPlace;
import pojo.Location;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

/*třída SpecBuilder definuje základní nastavení parametrů jako je login,
baseURI, ContentType atd. aby byl kód přehlednější
+ je zde serializace vstupního JSONu
*/
public class AddPutPlaceWithSerializationAndSpecBuilderTest_10 {
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

        RequestSpecification requestSpecificationAddplace = new RequestSpecBuilder()
                .setBaseUri("https://rahulshettyacademy.com")
                .addQueryParam("key", "qaclick123")
                .setContentType(ContentType.JSON).build();

        ResponseSpecification responseSpecificationAddPlace = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON).build();


        RequestSpecification addPlaceRequest = given()
                .filter(new ReUsableMethods.TimingFilter()).log().all()
                .spec(requestSpecificationAddplace)
                .body(addPlace);

        Response responseAddPlace = addPlaceRequest.when()
                .post("maps/api/place/add/json")
                .then()
                .spec(responseSpecificationAddPlace)
                .body("scope", equalTo("APP"))
                .header("Server", "Apache/2.4.52 (Ubuntu)")
                .extract().response();

        String responseAddPlaceString = responseAddPlace.asString();

        System.out.println(responseAddPlaceString);

    }
}
