package ParamTest;

import RestAssred.helper.JsonHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.io.IOException;
import java.util.stream.Stream;

import static ParamTest.Worker.testPeoples;

public class ParamTest {

    private static Stream<Arguments> testPeople(){
        return testPeoples();
    }

    @ParameterizedTest
    @MethodSource("testPeople")
    public void createParamTest(Worker model){
        System.out.println(model.toString());
        Assertions.assertTrue(model.getAge() > 18 , "Не может работать в компании!");
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/authDate.csv", delimiter = ',')
    @DisplayName("CsvFileSource")
    public void createParam2Test(String name, Integer age, String work){
        System.out.println(name + " работает " + work + ". Возраст: " + age);
        Assertions.assertTrue(age > 18 , "Не может работать в компании!");
    }

    @Test
    public void readJsonTest() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File("src/main/resources/jack.json"); // Чтение json файлов
        Worker worker = objectMapper.readValue(file, Worker.class);
        System.out.println(worker);
    }

    @Test
    public void readJsonTest2() throws IOException {
        Worker worker = JsonHelper.fromJson("src/main/resources/jack.json", Worker.class);
        System.out.println(worker);
        String toJsonWorker = new Worker("Kit",27,"bilder").toString();
        System.out.println(toJsonWorker);
    }
}


