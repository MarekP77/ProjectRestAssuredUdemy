package test;

import files.ReUsableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojo.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static io.restassured.RestAssured.*;

public class OAuthWithPojoTest_7 {

    @Test()
    public void oAuthToken(){

        String[] courseTitles = {"Selenium Webdriver Java", "Protractor", "Cypress"};

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

        GetCourse responseGetCourseDetail = given().filter(new ReUsableMethods.TimingFilter()).log().all()
                .queryParam("access_token", accessToken)
                .when()
                .get("/oauthapi/getCourseDetails").as(GetCourse.class);


/*        .then().log().all()
                .assertThat()
                    .statusCode(401)
                .extract().response().asString();*/

        System.out.println("LinkedIn: " + responseGetCourseDetail.getLinkedIn());
        System.out.println("Instructor: " + responseGetCourseDetail.getInstructor());

        System.out.println("Course title of first index : " + responseGetCourseDetail.getCourses().getApi().get(1).getCourseTitle());

        List<Api> apiCourses = responseGetCourseDetail.getCourses().getApi();
        for (int i=0; i<apiCourses.size();i++)
        {
            if(apiCourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing"))
            {
                System.out.println("Price of SoapUI Webservices testing: " + apiCourses.get(i).getPrice());
            }
        }

        ArrayList<String> arrayOfCoursesActual = new ArrayList<String>();
        List<WebAutomation> webAutomation = responseGetCourseDetail.getCourses().getWebAutomation();
        for(int i=0; i<webAutomation.size();i++)
        {
            if(webAutomation.get(i).getPrice().equalsIgnoreCase("50"))
            {
                System.out.println("Course Title of web automation price 50: " + "'" + webAutomation.get(i).getCourseTitle() + "'"  + " for index of " + i);
            }
            System.out.println("Course Title of web automation_" + i + ": " + webAutomation.get(i).getCourseTitle());

            arrayOfCoursesActual.add(webAutomation.get(i).getCourseTitle());

        }

        //toto konvertuje moje pole na arrayList
        List<String> arrayOfCoursesExpected = Arrays.asList(courseTitles);

        //zajistí pořadí, aby při asserci obou listů nebyl problém
        arrayOfCoursesActual.sort(Comparator.naturalOrder());
        arrayOfCoursesExpected.sort(Comparator.naturalOrder());

        Assert.assertTrue(arrayOfCoursesActual.equals(arrayOfCoursesExpected));
        Assert.assertEquals(arrayOfCoursesExpected, arrayOfCoursesActual);

    }
}
