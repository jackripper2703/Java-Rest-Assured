package RestAssured.services;


import RestAssured.assertions.AssertableResponse;
import RestAssured.models.FullUser;
import RestAssured.models.JwtAuthData;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class UserService {

    @Step("Регистрация пользователя в системе")
    public AssertableResponse register(FullUser user) {
        return new AssertableResponse(given().contentType(ContentType.JSON)
                .body(user)
                .post("/signup")
                .then());
    }

    @Step("Получения информации о пользователя через токен")
    public AssertableResponse getUserInfo(String jwt) {
        return new AssertableResponse(given().auth().oauth2(jwt)
                .get("/user")
                .then());
    }

    @Step("Получения информации о пользователя без токена")
    public AssertableResponse getUserInfo() {
        return new AssertableResponse(given()
                .get("/user")
                .then());
    }

    @Step("Обновления пароля на {newPassword}")
    public AssertableResponse updatePass(String newPassword, String jwt) {
        Map<String, String> password = new HashMap<>();
        password.put("password", newPassword);

        return new AssertableResponse(given().contentType(ContentType.JSON)
                .auth().oauth2(jwt)
                .body(password)
                .put("/user")
                .then());
    }

    @Step("Удаление пользователя")
    public AssertableResponse deleteUser(String jwt) {
        return new AssertableResponse(given().auth().oauth2(jwt)
                .delete("/user")
                .then());
    }

    @Step("Авторизация для получения токена")
    public AssertableResponse auth(FullUser fullUser) {
        JwtAuthData data = new JwtAuthData(fullUser.getLogin(), fullUser.getPass());
        return new AssertableResponse(given().contentType(ContentType.JSON)
                .body(data)
                .post("/login")
                .then());
    }

    @Step("Получения списка пользователей")
    public AssertableResponse getAllUsers() {
        return new AssertableResponse(given()
                .get("/users")
                .then());
    }
}
