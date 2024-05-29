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
        // Метод для регистрации нового пользователя в системе
        return new AssertableResponse(given().contentType(ContentType.JSON) // Указываем, что тело запроса в формате JSON
                .body(user) // Передаем тело запроса, содержащее информацию о пользователе
                .post("/signup") // Выполняем POST-запрос на эндпоинт /signup
                .then()); // Возвращаем результат запроса
    }

    @Step("Получение информации о пользователе через токен")
    public AssertableResponse getUserInfo(String jwt) {
        // Метод для получения информации о пользователе с использованием JWT токена
        return new AssertableResponse(given().auth().oauth2(jwt) // Добавляем токен в заголовок запроса для авторизации
                .get("/user") // Выполняем GET-запрос на эндпоинт /user
                .then()); // Возвращаем результат запроса
    }

    @Step("Получение информации о пользователе без токена")
    public AssertableResponse getUserInfo() {
        // Метод для получения информации о пользователе без использования токена
        return new AssertableResponse(given()
                .get("/user") // Выполняем GET-запрос на эндпоинт /user
                .then()); // Возвращаем результат запроса
    }

    @Step("Обновление пароля на {newPassword}")
    public AssertableResponse updatePass(String newPassword, String jwt) {
        // Метод для обновления пароля пользователя
        Map<String, String> password = new HashMap<>(); // Создаем карту для нового пароля
        password.put("password", newPassword); // Добавляем новый пароль в карту

        return new AssertableResponse(given().contentType(ContentType.JSON) // Указываем, что тело запроса в формате JSON
                .auth().oauth2(jwt) // Добавляем токен в заголовок запроса для авторизации
                .body(password) // Передаем тело запроса, содержащее новый пароль
                .put("/user") // Выполняем PUT-запрос на эндпоинт /user
                .then()); // Возвращаем результат запроса
    }

    @Step("Удаление пользователя")
    public AssertableResponse deleteUser(String jwt) {
        // Метод для удаления пользователя
        return new AssertableResponse(given().auth().oauth2(jwt) // Добавляем токен в заголовок запроса для авторизации
                .delete("/user") // Выполняем DELETE-запрос на эндпоинт /user
                .then()); // Возвращаем результат запроса
    }

    @Step("Авторизация для получения токена")
    public AssertableResponse auth(FullUser fullUser) {
        // Метод для авторизации пользователя и получения JWT токена
        JwtAuthData data = new JwtAuthData(fullUser.getLogin(), fullUser.getPass()); // Создаем объект с данными для авторизации
        return new AssertableResponse(given().contentType(ContentType.JSON) // Указываем, что тело запроса в формате JSON
                .body(data) // Передаем тело запроса, содержащее данные для авторизации
                .post("/login") // Выполняем POST-запрос на эндпоинт /login
                .then()); // Возвращаем результат запроса
    }

    @Step("Получение списка пользователей")
    public AssertableResponse getAllUsers() {
        // Метод для получения списка всех пользователей
        return new AssertableResponse(given()
                .get("/users") // Выполняем GET-запрос на эндпоинт /users
                .then()); // Возвращаем результат запроса
    }
}
