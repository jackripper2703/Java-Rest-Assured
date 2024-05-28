package RestAssured.assertions.conditions;

import RestAssured.assertions.Condition;
import RestAssured.models.Info;
import io.restassured.response.ValidatableResponse;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;

@RequiredArgsConstructor
public class MessageCondition implements Condition {
    private final String expectedStatus;
    private final String expectedMessage;

    @Override
    public void check(ValidatableResponse response) {
        Info info = response.extract().jsonPath().getObject("info", Info.class);
        Assertions.assertEquals(expectedStatus, info.getStatus());
        Assertions.assertEquals(expectedMessage, info.getMessage());
    }
}
