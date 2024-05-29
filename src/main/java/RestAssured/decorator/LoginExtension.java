package RestAssured.decorator;

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

// Класс LoginExtension реализует интерфейсы для выполнения кода до и после тестов
public class LoginExtension implements BeforeTestExecutionCallback, AfterTestExecutionCallback {

    /**
     * Метод, выполняющийся перед запуском каждого теста.
     *
     * @param context Контекст выполнения теста.
     */
    @Override
    public void beforeTestExecution(ExtensionContext context){
        // Вывод сообщения перед выполнением теста
        System.out.println("\nЗапустился тест: " + context.getDisplayName() + "\n");
    }

    /**
     * Метод, выполняющийся после завершения каждого теста.
     *
     * @param context Контекст выполнения теста.
     */
    @Override
    public void afterTestExecution(ExtensionContext context){
        // Вывод сообщения после выполнения теста
        System.out.println("\nЗавершился тест: " + context.getDisplayName() + "\n");
    }
}
