package test;

import files.ReUsableMethods;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.Test;
import pojo.ECommerce.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class ECommerceAPITest_11 {

    @Test
    public void e2eECommerce(){
        //POST login
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

        ResponseSpecification responseSpecificationECommerceCreate = new ResponseSpecBuilder()
                .expectStatusCode(201)
                .expectContentType(ContentType.JSON).build();

        RequestSpecification eCommerceLoginRequest = given()
                .spec(requestSpecificationECommerce)
                .body(loginRequest);

        LoginResponse loginResponse = eCommerceLoginRequest.when()
                .post("api/ecom/auth/login")
                .then().log().all()
                .spec(responseSpecificationECommerce)
                .extract().response().as(LoginResponse.class);


        String loginToken = loginResponse.getToken();
        String userId = loginResponse.getUserId();

        String responseLoginAsString = ReUsableMethods.convertObjectToJsonString(loginResponse);
        System.out.println(responseLoginAsString);

        //POST Create Product
        RequestSpecification requestSpecificationCreate = new RequestSpecBuilder()
                .setBaseUri("https://rahulshettyacademy.com")
                .addFilter(new ReUsableMethods.TimingFilter())
                .addFilter(new RequestLoggingFilter())
                .addHeader("Authorization", loginToken).build();

        RequestSpecification createProduct = given()
                .spec(requestSpecificationCreate)
                .param("productName", "kocka")
                .param("productAddedBy", userId)
                .param("productCategory", "fashion")
                .param("productSubCategory", "shirt")
                .param("productPrice", "12000")
                .param("productDescription", "Addias Originals")
                .param("productFor", "women")
                .multiPart("productImage", new File("src/files/images.jpg"));

        CreateProduct createProductResponse = createProduct.when()
                .post("api/ecom/product/add-product")
                .then().log().all()
                .spec(responseSpecificationECommerceCreate)
                .extract().response().as(CreateProduct.class);

        String productId = createProductResponse.getProductId();

        //POST Create Order
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setCountry("Czech republic");
        orderDetail.setProductOrderedId(productId);

        List<OrderDetail> orderDetailList = new ArrayList<OrderDetail>();
        orderDetailList.add(orderDetail);

        Orders orders = new Orders();
        orders.setOrders(orderDetailList);

        //TODO
/*        RequestSpecification createOrder = given()
                .spec(requestSpecificationECommerce)
                .post("api/ecom/order/create-order")
                .body(orders);*/

    }

}
