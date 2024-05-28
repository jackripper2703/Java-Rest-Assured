package RestAssured.assertions;

import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GenericAssertableResponse<T> {
    private final ValidatableResponse response;
    private final TypeRef<T> clazz;

    public GenericAssertableResponse<T> should(Condition condition) {
        condition.check(response);
        return this;
    }

    public T asObject() {
        return response.extract().as(clazz);
    }

    public T asObject(String jsonPath) {
        return response.extract().jsonPath().getObject(jsonPath, clazz);
    }

    public List<T> asList() {
        return response.extract().jsonPath().getList("", clazz.getTypeAsClass());
    }

    public List<T> asList(String jsonPath) {
        return response.extract().jsonPath().getList(jsonPath, clazz.getTypeAsClass());
    }

    public Response asResponse() {
        return response.extract().response();
    }

}
