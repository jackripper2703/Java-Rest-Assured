package RestAssured.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class JsonHelper {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static <T> T fromJson(String JsonPath, Class<T> out) throws IOException {
        return MAPPER.readValue(new File(JsonPath), out);
    }

    public static String toJson(Object object) throws JsonProcessingException {
        return MAPPER.writeValueAsString(object);
    }
}
