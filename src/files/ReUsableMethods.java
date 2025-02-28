package files;

import io.restassured.filter.*;
import io.restassured.response.Response;
import io.restassured.specification.*;
import io.restassured.path.json.JsonPath;

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
}