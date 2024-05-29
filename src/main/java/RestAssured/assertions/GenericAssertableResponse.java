package RestAssured.assertions;

import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * Класс для выполнения различных проверок и извлечения данных из ответа API.
 * Используется с типом данных T для обобщения методов.
 *
 * @param <T> Тип данных, с которым будет работать GenericAssertableResponse.
 */
@RequiredArgsConstructor // Автоматически генерирует конструктор с обязательными аргументами
public class GenericAssertableResponse<T> {
    private final ValidatableResponse response; // Валидационный ответ REST-assured
    private final TypeRef<T> clazz; // Тип данных, для извлечения из ответа

    /**
     * Выполняет проверку условия на ответ.
     *
     * @param condition Условие для проверки
     * @return Экземпляр GenericAssertableResponse для цепочечных вызовов
     */
    public GenericAssertableResponse<T> should(Condition condition) {
        condition.check(response); // Выполняет проверку условия
        return this;
    }

    /**
     * Извлекает и возвращает ответ в виде объекта типа T.
     *
     * @return Объект типа T
     */
    public T asObject() {
        return response.extract().as(clazz);
    }

    /**
     * Извлекает и возвращает объект из ответа по указанному JSON пути.
     *
     * @param jsonPath Путь JSON для извлечения объекта
     * @return Объект типа T
     */
    public T asObject(String jsonPath) {
        return response.extract().jsonPath().getObject(jsonPath, clazz);
    }

    /**
     * Извлекает и возвращает список объектов типа T из ответа.
     *
     * @return Список объектов типа T
     */
    public List<T> asList() {
        return response.extract().jsonPath().getList("", clazz.getTypeAsClass());
    }

    /**
     * Извлекает и возвращает список объектов типа T из ответа по указанному JSON пути.
     *
     * @param jsonPath Путь JSON для извлечения списка объектов
     * @return Список объектов типа T
     */
    public List<T> asList(String jsonPath) {
        return response.extract().jsonPath().getList(jsonPath, clazz.getTypeAsClass());
    }

    /**
     * Извлекает и возвращает полный ответ в виде объекта Response.
     *
     * @return Объект Response
     */
    public Response asResponse() {
        return response.extract().response();
    }
}
