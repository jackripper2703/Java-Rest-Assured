package RestAssured.assertions;

import RestAssured.assertions.conditions.MessageCondition;
import RestAssured.assertions.conditions.StatusCodeCondition;
import RestAssured.assertions.conditions.SchemaCondition;
import io.qameta.allure.Step;

public class Conditions {

    @Step("Проверка ответа в info")
    public static MessageCondition hasInfo(String expectedStatus, String expectedMessage) {
        return new MessageCondition(expectedStatus, expectedMessage);
    }

    @Step("Проверка статус кода {expectedCode}")
    public static StatusCodeCondition hasStatusCode(Integer expectedCode) {
        return new StatusCodeCondition(expectedCode);
    }

    @Step("Проверка JSON схемы")
    public static SchemaCondition matchesSchema(String schemaPath) {
        return new SchemaCondition(schemaPath);
    }
}
