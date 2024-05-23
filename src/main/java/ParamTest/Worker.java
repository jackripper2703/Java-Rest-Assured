package ParamTest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Worker {
    private String name;
    private int age;
    private String work;


    @Override
    public String toString() {
        return name + " работает " + work + ". Возраст: " + age;
    }

    public static Stream<Arguments> testPeoples(){
        return Stream.of(
            Arguments.of(new Worker("Jack",27,"QA")),
            Arguments.of(new Worker("Renat",30,"Backend")),
            Arguments.of(new Worker("Sergey",23,"Frontend")),
            Arguments.of(new Worker("Anastasia",17,"Analytic"))
        );

    }
}
