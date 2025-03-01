package test;

import files.Payload;
import files.ReUsableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Random;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class DynamicJsonAddBookTest_4 {

    //@Test se používá, abych nemusel vytvářet public static void main...
    @Test(dataProvider = "BooksData")
    public void addBook(String isbn, String aisle) {
        RestAssured.baseURI = "http://216.10.245.166";

        String responseAddBook = given().log().all()
                .header("Content-Type", "application/json")
                .body(Payload.AddBook(isbn, aisle))
                .when()
                .post("/Library/Addbook.php")
                .then().log().all()
                .assertThat()
                .statusCode(200)
                .body("Msg", equalTo("successfully added"))
                .extract().response().asString();
        JsonPath jsonAddBook = ReUsableMethods.rawToJson(responseAddBook);
        String id = jsonAddBook.get("ID");
        System.out.println("ID of created book: " + id);

        String responseDeleteBook = given().log().all()
                .header("Content-Type", "application/json")
                .body(Payload.DeleteBook(id))
                .when()
                .delete("/Library/DeleteBook.php")
                .then().log().all()
                .assertThat()
                .statusCode(200)
                .body("msg", equalTo("book is successfully deleted"))
                .extract().response().asString();
        JsonPath jsonDeleteBook = ReUsableMethods.rawToJson(responseDeleteBook);
        String msg = jsonDeleteBook.get("msg");
        System.out.println("Message after delete book is: " + msg);
    }

    @DataProvider(name="BooksData")
    public Object[][] getData()
    {
        return new Object[][] {
                {"ojwty", generateFourDigitNumber()},
                {"cwetee", generateFourDigitNumber()},
                {"okmfet", generateFourDigitNumber()}
        };
    }

    private static String generateFourDigitNumber() {
        Random random = new Random();
        return String.valueOf(1000 + random.nextInt(9000));
    }
}