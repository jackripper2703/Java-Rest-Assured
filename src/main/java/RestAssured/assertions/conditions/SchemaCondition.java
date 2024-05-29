package RestAssured.assertions.conditions;

import RestAssured.assertions.Condition;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.ValidatableResponse;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;

import java.io.InputStream;

@RequiredArgsConstructor
public class SchemaCondition implements Condition {
    private final String schemaPath;

    @Override
    public void check(ValidatableResponse response) {
        String actualResponseBody = response.extract().asString();
        String schemaContent;

        try (InputStream schemaStream = getClass().getClassLoader().getResourceAsStream("schemes/" + schemaPath)) {
            if (schemaStream == null) {
                throw new IllegalArgumentException("Schema file not found: " + schemaPath);
            }
            schemaContent = new String(schemaStream.readAllBytes());
        } catch (Exception e) {
            throw new RuntimeException("Error reading schema file: " + schemaPath, e);
        }

        try {
            response.assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemes/" + schemaPath));
        } catch (AssertionError e) {
            System.err.println("Expected JSON Schema: " + schemaContent);
            System.err.println("Actual JSON Response: " + actualResponseBody);
            Assertions.fail("JSON schema validation failed: " + e.getMessage());
        }
    }
}
