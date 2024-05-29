package RestAssured.assertions;

import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * Класс AssertableResponse оборачивает ValidatableResponse и предоставляет методы для удобной проверки и извлечения данных из ответа API.
 */
@RequiredArgsConstructor
public class AssertableResponse {
    private final ValidatableResponse response;

    /**
     * Применяет заданное условие к ответу API.
     *
     * @param condition Условие для проверки ответа
     * @return Текущий объект AssertableResponse для цепочки вызовов
     */
    public AssertableResponse should(Condition condition) {
        condition.check(response);
        return this;
    }

    /**
     * Извлекает JWT токен из ответа.
     *
     * @return JWT токен в виде строки
     */
    public String asJwt() {
        return response.extract().jsonPath().getString("token");
    }

    /**
     * Извлекает объект указанного типа из ответа.
     *
     * @param tClass Класс типа, в который нужно преобразовать ответ
     * @param <T>    Тип возвращаемого объекта
     * @return Объект указанного типа
     */
    public <T> T as(Class<T> tClass) {
        return response.extract().as(tClass);
    }

    /**
     * Извлекает объект указанного типа из ответа по заданному JSON пути.
     *
     * @param jsonPath JSON путь для извлечения объекта
     * @param tClass   Класс типа, в который нужно преобразовать ответ
     * @param <T>      Тип возвращаемого объекта
     * @return Объект указанного типа
     */
    public <T> T as(String jsonPath, Class<T> tClass) {
        return response.extract().jsonPath().getObject(jsonPath, tClass);
    }

    /**
     * Извлекает список объектов указанного типа из ответа по заданному JSON пути.
     *
     * @param jsonPath JSON путь для извлечения списка объектов
     * @param tClass   Класс типа, в который нужно преобразовать ответ
     * @param <T>      Тип объектов в списке
     * @return Список объектов указанного типа
     */
    public <T> List<T> asList(String jsonPath, Class<T> tClass) {
        return response.extract().jsonPath().getList(jsonPath, tClass);
    }

    /**
     * Извлекает список объектов указанного типа из ответа.
     *
     * @param tClass Класс типа, в который нужно преобразовать ответ
     * @param <T>    Тип объектов в списке
     * @return Список объектов указанного типа
     */
    public <T> List<T> asList(Class<T> tClass) {
        return response.extract().jsonPath().getList("", tClass);
    }

    /**
     * Извлекает Response из ValidatableResponse.
     *
     * @return Объект Response
     */
    public Response asResponse() {
        return response.extract().response();
    }
}
