package test;

import files.Payload;
import files.ReUsableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.*;

public class BugTest_6 {

    @Test()
    public void createBug(){
        RestAssured.baseURI = "https://marekpospisil.atlassian.net";

        String responseCreateBug = given().log().all()
                .header("Content-Type", "application/json")
                .header("Authorization", "Basic bWFydGFucG9zcGFAb3V0bG9vay5jb206QVRBVFQzeEZmR0YwRW0ycUFmNjhreUlCTUt2OTc1ODdzcXNIcHV1YmJKNFNacVZza2JZM1pSMUQwaDdQNVViVzIzYnJfYWh2VVdNbExySGZPczdOTkg3TE9XX2MtT2xkaVU0VUdJb2t0SVFBajI5YTU0dWFLSVBpcXNmODhyVVRXVEdxdHFuMXBZMG1ZTzZib2xaOW5MdkxNUy1PSTJteEVMSmZTREZlUnlWS29DY3NBamIwRGU4PTc5MDE0Qzkw")
                .body(Payload.CreateBug())
                .when()
                .post("/rest/api/3/issue")
                .then().log().all()
                .assertThat()
                .statusCode(201)
                .extract().response().asString();

        JsonPath jsonCreateBug = ReUsableMethods.rawToJson(responseCreateBug);
        String bugId = jsonCreateBug.get("id");
        System.out.println("ID of created BUG: " + bugId);

        String addAttachementToBug = given().log().all()
                .header("X-Atlassian-Token", "no-check")
                .header("Authorization", "Basic bWFydGFucG9zcGFAb3V0bG9vay5jb206QVRBVFQzeEZmR0YwRW0ycUFmNjhreUlCTUt2OTc1ODdzcXNIcHV1YmJKNFNacVZza2JZM1pSMUQwaDdQNVViVzIzYnJfYWh2VVdNbExySGZPczdOTkg3TE9XX2MtT2xkaVU0VUdJb2t0SVFBajI5YTU0dWFLSVBpcXNmODhyVVRXVEdxdHFuMXBZMG1ZTzZib2xaOW5MdkxNUy1PSTJteEVMSmZTREZlUnlWS29DY3NBamIwRGU4PTc5MDE0Qzkw")
                .multiPart("file", new File("src/files/Snapshot.png"))
                .pathParam("issueIdOrKey", bugId)
                .when()
                .post("/rest/api/3/issue/{issueIdOrKey}/attachments")
                .then().log().all()
                .assertThat()
                .statusCode(200)
                .extract().response().asString();

        /* Get Bug TODO and assertion to filename of attachement*/
    }
}