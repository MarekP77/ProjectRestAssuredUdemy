package test;

import files.ReUsableMethods;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.Test;
import pojo.LoginRequest;
import pojo.LoginResponse;

import static io.restassured.RestAssured.given;

public class ECommerceAPITest_11 {

    @Test
    public void e2eECommerce(){
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserEmail("martanpospa@seznam.cz");
        loginRequest.setUserPassword("Hello123@");

        RequestSpecification requestSpecificationECommerce = new RequestSpecBuilder()
                .setBaseUri("https://rahulshettyacademy.com")
                .addFilter(new ReUsableMethods.TimingFilter())
                .addFilter(new RequestLoggingFilter())
                .setContentType(ContentType.JSON).build();

        ResponseSpecification responseSpecificationECommerce = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON).build();

        RequestSpecification eCommerceRequest = given()
                .spec(requestSpecificationECommerce)
                .body(loginRequest);

        LoginResponse responseLogin = eCommerceRequest.when()
                .post("api/ecom/auth/login")
                .then()
                .spec(responseSpecificationECommerce)
                .extract().response().as(LoginResponse.class);


        String loginToken = responseLogin.getToken();
        System.out.println("My loginToken: " + loginToken);

        String responseLoginAsString = ReUsableMethods.convertObjectToJsonString(responseLogin);

        System.out.println(responseLoginAsString);
    }

}
