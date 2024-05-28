package RestAssured.helper;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;


public class Specification {

    public static RequestSpecification requestSpecification(String url, ContentType type) {
        return new RequestSpecBuilder()
                .setBaseUri(url)
                .setContentType(type).build();
    }

    public static RequestSpecification requestSpecificationAuth(ContentType type, String token) {
        return new RequestSpecBuilder()
                .addHeader("Authorization", "Bearer " + token)
                .setContentType(type).build();
    }


    public static ResponseSpecification responseSpec(Integer code) {
        return new ResponseSpecBuilder()
                .expectStatusCode(code)
                .build();
    }

    public static void filterSet() {
        RestAssured.filters(CustomTml.customLogFilter().withCustomTemplates());
    }

    public static void installSpec(RequestSpecification request, ResponseSpecification response) {
        RestAssured.requestSpecification = request;
        RestAssured.responseSpecification = response;
    }

    public static void installSpecRequest(RequestSpecification request) {
        RestAssured.requestSpecification = request;
    }

    public static void installSpecResponse(ResponseSpecification response) {
        RestAssured.responseSpecification = response;
    }

}
