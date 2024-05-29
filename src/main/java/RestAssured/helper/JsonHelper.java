package RestAssured.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class JsonHelper {

    private static final ObjectMapper MAPPER = new ObjectMapper(); // Инициализация ObjectMapper для работы с JSON

    /**
     * Метод для чтения JSON из файла и преобразования его в объект указанного класса.
     *
     * @param JsonPath Путь к файлу с JSON.
     * @param out      Класс, в экземпляр которого нужно преобразовать JSON.
     * @param <T>      Тип возвращаемого объекта.
     * @return Объект типа T, полученный из JSON.
     * @throws IOException В случае ошибки чтения файла или преобразования JSON.
     */
    public static <T> T fromJson(String JsonPath, Class<T> out) throws IOException {
        return MAPPER.readValue(new File(JsonPath), out); // Чтение JSON из файла и преобразование в объект типа T
    }

    /**
     * Метод для преобразования объекта в строку JSON.
     *
     * @param object Объект, который нужно преобразовать в JSON.
     * @return Строка JSON, представляющая объект.
     * @throws JsonProcessingException В случае ошибки преобразования объекта в JSON.
     */
    public static String toJson(Object object) throws JsonProcessingException {
        return MAPPER.writeValueAsString(object); // Преобразование объекта в строку JSON
    }
}
