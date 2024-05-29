package RestAssured.assertions.conditions;

import RestAssured.assertions.Condition;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.ValidatableResponse;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;

import java.io.InputStream;

/**
 * Условие для проверки соответствия JSON-ответа определенной JSON-схеме.
 */
@RequiredArgsConstructor
public class SchemaCondition implements Condition {
    private final String schemaPath;

    /**
     * Проверяет, соответствует ли тело ответа указанной JSON-схеме.
     *
     * @param response Проверяемый HTTP-ответ
     */
    @Override
    public void check(ValidatableResponse response) {
        String actualResponseBody = response.extract().asString();
        String schemaContent;

        // Чтение схемы из файловой системы
        try (InputStream schemaStream = getClass().getClassLoader().getResourceAsStream("schemes/" + schemaPath)) {
            if (schemaStream == null) {
                throw new IllegalArgumentException("Файл схемы не найден: " + schemaPath);
            }
            schemaContent = new String(schemaStream.readAllBytes());
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при чтении файла схемы: " + schemaPath, e);
        }

        // Проверка тела ответа на соответствие схеме
        try {
            response.assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemes/" + schemaPath));
        } catch (AssertionError e) {
            System.err.println("Ожидаемая JSON-схема: " + schemaContent);
            System.err.println("Фактический JSON-ответ: " + actualResponseBody);
            Assertions.fail("Проверка схемы JSON не удалась: " + e.getMessage());
        }
    }
}
