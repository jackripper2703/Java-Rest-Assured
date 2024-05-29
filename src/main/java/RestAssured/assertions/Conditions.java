package RestAssured.assertions;

import RestAssured.assertions.conditions.MessageCondition;
import RestAssured.assertions.conditions.StatusCodeCondition;
import RestAssured.assertions.conditions.SchemaCondition;
import io.qameta.allure.Step;

/**
 * Класс Conditions предоставляет статические методы для создания различных условий проверок.
 * Используется в тестах для валидации ответов API.
 */
public class Conditions {

    /**
     * Создает условие для проверки информации в ответе.
     *
     * @param expectedStatus  Ожидаемый статус
     * @param expectedMessage Ожидаемое сообщение
     * @return Условие MessageCondition
     */
    @Step("Проверка ответа в info")
    public static MessageCondition hasInfo(String expectedStatus, String expectedMessage) {
        return new MessageCondition(expectedStatus, expectedMessage);
    }

    /**
     * Создает условие для проверки статус кода.
     *
     * @param expectedCode Ожидаемый статус код
     * @return Условие StatusCodeCondition
     */
    @Step("Проверка статус кода {expectedCode}")
    public static StatusCodeCondition hasStatusCode(Integer expectedCode) {
        return new StatusCodeCondition(expectedCode);
    }

    /**
     * Создает условие для проверки соответствия JSON схемы.
     *
     * @param schemaPath Путь к файлу JSON схемы
     * @return Условие SchemaCondition
     */
    @Step("Проверка JSON схемы")
    public static SchemaCondition matchesSchema(String schemaPath) {
        return new SchemaCondition(schemaPath);
    }
}
