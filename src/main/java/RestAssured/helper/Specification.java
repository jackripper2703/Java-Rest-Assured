package RestAssured.helper;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;


public class Specification {

    public static RequestSpecification requestSpecification(String url) {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder()
                .setBaseUri(url);

        switch (url) {
            case "https://reqres.in/":
                requestSpecBuilder.setContentType(ContentType.JSON);
                break;
            case "https://dev-api.vpluse.me/":
                requestSpecBuilder.setContentType("multipart/form-data");
                break;
            default:
                requestSpecBuilder.setContentType(ContentType.JSON);
                break;
        }

        return requestSpecBuilder.build();
    }

    public static RequestSpecification requestSpecificationAuth(String url, String token) {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder()
                .setBaseUri(url)
                .addHeader("Authorization", "Bearer " + token);

        switch (url) {
            case "urlRegres":
                requestSpecBuilder.setContentType(ContentType.JSON);
                break;
            case "urlVpluse":
                requestSpecBuilder.setContentType("multipart/form-data");
                break;
            default:
                requestSpecBuilder.setContentType(ContentType.JSON);
                break;
        }

        return requestSpecBuilder.build();
    }


    public static ResponseSpecification responseSpec(Integer code){
        return new ResponseSpecBuilder()
                .expectStatusCode(code)
                .build();
    }

    public static void installSpec(RequestSpecification request, ResponseSpecification response){
        RestAssured.requestSpecification = request;
        RestAssured.responseSpecification = response;
    }

    public static void installSpecRequest(RequestSpecification request){
        RestAssured.requestSpecification = request;
    }

    public static void installSpecResponse(ResponseSpecification response){
        RestAssured.responseSpecification = response;
    }

}
