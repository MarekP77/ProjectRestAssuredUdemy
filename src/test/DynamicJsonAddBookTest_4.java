package test;

import files.Payload;
import files.ReUsableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

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
        //array=collection of elements -> array je []
        //multidimensional array=collection of arrays -> multidimensional je [][]
        return new Object[][] {{"ojwty", "9363"},{"cwetee", "4253"},{"okmfet", "533"}};

    }
}