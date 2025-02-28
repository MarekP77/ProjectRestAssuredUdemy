package test;

import files.ReUsableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;

public class OAuthTest_7 {

    @Test()
    public void oAuthToken(){
        RestAssured.baseURI = "https://rahulshettyacademy.com";

        String responseGetToken = given().filter(new ReUsableMethods.TimingFilter()).log().all()
                .formParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                .formParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
                .formParams("grant_type", "client_credentials")
                .formParams("scope", "trust")
                .when()
                .post("/oauthapi/oauth2/resourceOwner/token")
                .then().log().all()
                .assertThat()
                .statusCode(200)
                .extract().response().asString();

        JsonPath jsonToken = ReUsableMethods.rawToJson(responseGetToken);
        String accessToken = jsonToken.get("access_token");
        System.out.println("Access Token is: " + accessToken);

        String responseGetCourseDetail = given().filter(new ReUsableMethods.TimingFilter()).log().all()
                .queryParam("access_token", accessToken)
                .when()
                .get("/oauthapi/getCourseDetails")
                .then().log().all()
                .assertThat()
                .statusCode(401)
                .extract().response().asString();

    }
}
