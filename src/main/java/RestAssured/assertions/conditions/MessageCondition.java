package RestAssured.assertions.conditions;

import RestAssured.assertions.Condition;
import RestAssured.models.Info;
import io.restassured.response.ValidatableResponse;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;

/**
 * Условие для проверки значений полей "status" и "message" в объекте Info внутри JSON-ответа.
 */
@RequiredArgsConstructor
public class MessageCondition implements Condition {
    private final String expectedStatus;
    private final String expectedMessage;

    /**
     * Проверяет, что значения полей "status" и "message" в объекте Info соответствуют ожидаемым.
     *
     * @param response Проверяемый HTTP-ответ
     */
    @Override
    public void check(ValidatableResponse response) {
        // Извлечение объекта Info из JSON-ответа
        Info info = response.extract().jsonPath().getObject("info", Info.class);

        // Проверка соответствия поля "status"
        Assertions.assertEquals(expectedStatus, info.getStatus(),
                "Ожидаемый статус: " + expectedStatus + ", но был: " + info.getStatus());

        // Проверка соответствия поля "message"
        Assertions.assertEquals(expectedMessage, info.getMessage(),
                "Ожидаемое сообщение: " + expectedMessage + ", но было: " + info.getMessage());
    }
}
