package files;

public class Payload {
    public static String AddPlace() {
        return """
                {
                    "location": {
                        "lat": -38.383494,
                        "lng": 33.427362
                    },
                    "accuracy": 50,
                    "name": "Frontline house",
                    "phone_number": "(+91) 983 893 3937",
                    "address": "29, side layout, cohen 09",
                    "types": [
                        "shoe park",
                        "shop"
                    ],
                    "website": "http://google.com",
                    "language": "French-IN"
                }""";
    }

    //910
    public static String CoursePrice() {
        return """
                {
                  "dashboard": {
                    "purchaseAmount": 1162,
                    "website": "rahulshettyacademy.com"
                  },
                  "courses": [
                    {
                      "title": "Selenium Python",
                      "price": 50,
                      "copies": 6
                    },
                    {
                      "title": "Cypress",
                      "price": 40,
                      "copies": 4
                    },
                    {
                      "title": "RPA",
                      "price": 45,
                      "copies": 10
                    },
                    {
                      "title": "Appium",
                      "price": 36,
                      "copies": 7
                    }
                  ]
                }""";
    }

    //pro přidání proměnných místo "\"isbn\":\"bcd\", se "bcd" přípíše na \""+isbn+"\"
    public static String AddBook(String isbn, String aisle) {
        return "{\n" +
               "\"name\":\"Learn Appium Automation with Java\",\n" +
               "\"isbn\":\"" + isbn + "\",\n" +
               "\"aisle\":\"" + aisle + "\",\n" +
               "\"author\":\"John foer\"\n" +
               "}";
    }

    public static String DeleteBook(String id) {
        return "{\n" +
               "    \"ID\": \"" + id + "\"\n" +
               "}";
    }

    public static String CreateBug(){
        return """
                {
                    "fields": {
                        "project": {
                            "key": "SCRUM"
                        },
                        "issuetype": {
                            "name": "Bug"
                        },
                        "summary": "Website items are not working - automation Rest Assured"
                    }
                }""";
    }
}