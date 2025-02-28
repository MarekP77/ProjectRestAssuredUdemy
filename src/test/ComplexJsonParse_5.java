package test;

import files.Payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse_5 {
    public static void main(String[] args) {

        JsonPath json = new JsonPath(Payload.CoursePrice());
        //Print No of courses returned by API
        int countCourses = json.getInt("courses.size()");
        System.out.println("Number of courses: " + countCourses);

        //Print Purchase Amount
        int purchaseAmount = json.get("dashboard.purchaseAmount");
        System.out.println("Purchase Amount: " + purchaseAmount);

        //Print Title of the first course
        String firstTitleCourse = json.get("courses[0].title");
        System.out.println("First Title: " + firstTitleCourse);

        //Print All course titles and their respective Prices
        for(int i=0;i<countCourses;i++){
            System.out.println("Tile of course index "+i+" is: " + json.get("courses["+i+"].title").toString());
            System.out.println("Price of course index "+i+" is: " + json.get("courses["+i+"].price").toString());
        }

        //Print no of copies sold by RPA Course
        for(int i=0;i<countCourses;i++){
            String courseTitles = json.get("courses["+i+"].title");
            if(courseTitles.equalsIgnoreCase("RPA"))
            {
                //copies sold
                System.out.println("Number of copies sold by RPA course is: " + json.get("courses["+i+"].copies"));
                break;
            }
        }


    }
}