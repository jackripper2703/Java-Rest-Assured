package RestAssured.assertions;

import RestAssured.assertions.conditions.MessageCondition;
import RestAssured.assertions.conditions.StatusCodeCondition;


public class Conditions {
    public static MessageCondition hasInfo(String expectedStatus, String expectedMessage) {
        return new MessageCondition(expectedStatus, expectedMessage);
    }

    public static StatusCodeCondition hasStatusCode(Integer expectedCode) {
        return new StatusCodeCondition(expectedCode);
    }
}
