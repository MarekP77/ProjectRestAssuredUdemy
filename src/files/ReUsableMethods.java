package files;

import io.restassured.filter.*;
import io.restassured.response.Response;
import io.restassured.specification.*;
import io.restassured.path.json.JsonPath;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ReUsableMethods {
    public static JsonPath rawToJson(String response) {
        return new JsonPath(response);
    }

    //log volání délky API
    public static class TimingFilter implements Filter {
        @Override
        public Response filter(FilterableRequestSpecification requestSpec,
                               FilterableResponseSpecification responseSpec,
                               FilterContext ctx) {
            long start = System.nanoTime();
            Response response = ctx.next(requestSpec, responseSpec); // Opraveno
            long duration = (System.nanoTime() - start) / 1_000_000;
            System.out.println("API call took: " + duration + " s");
            return response;
        }
    }
    public static String convertObjectToJsonString(Object obj) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Vrací null, pokud převod selže
        }
    }
}