package test;

import files.Payload;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SumValidationTest_3 {

    @Test()
    public void sumOfCourses(){
        JsonPath json = new JsonPath(Payload.CoursePrice());

        int countCourses = json.getInt("courses.size()");
        int purchaseAmount = json.get("dashboard.purchaseAmount");

        int sumOfAllCourses = 0;

        //Verify if Sum of all Course prices matches with Purchase Amount
        for(int i=0;i<countCourses;i++){
            int coursePrice = json.getInt("courses["+i+"].price");
            int courseCopies = json.getInt("courses["+i+"].copies");

            int sumOfCourse = coursePrice * courseCopies;

            System.out.println("Sum of course " + json.get("courses["+i+"].title").toString() + " is: " + sumOfCourse);

            sumOfAllCourses = sumOfAllCourses + sumOfCourse;
        }
        System.out.println("Sum of all courses is: " + sumOfAllCourses);
        Assert.assertEquals(sumOfAllCourses,purchaseAmount);
    }
}