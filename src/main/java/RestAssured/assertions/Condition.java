package RestAssured.assertions;

import io.restassured.response.ValidatableResponse;

/**
 * Интерфейс Condition определяет метод для проверки условий на ответах API.
 * Используется для создания различных условий, которые могут быть применены к ответам API.
 */
public interface Condition {

    /**
     * Проверяет заданное условие на данном ответе API.
     *
     * @param response Ответ API, к которому применяется условие
     */
    void check(ValidatableResponse response);
}
