package RestAssured.services;

import RestAssured.assertions.AssertableResponse;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;

public class StatusCodeService {

    @Step("Дергаем ручку /api/bad-request")
    public AssertableResponse endpointGet(String endpoint) {
        return new AssertableResponse(given()
                .redirects().follow(false)
                .contentType(ContentType.JSON)
                .get(endpoint)
                .then());
    }
}
