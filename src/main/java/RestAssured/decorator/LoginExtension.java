package RestAssured.decorator;

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class LoginExtension implements BeforeTestExecutionCallback, AfterTestExecutionCallback {

    @Override
    public void beforeTestExecution(ExtensionContext context){
        System.out.println("\nЗапустился тест: " + context.getDisplayName() + "\n");
    }

    @Override
    public void afterTestExecution(ExtensionContext context){
        System.out.println("\nЗавершился тест: " + context.getDisplayName() + "\n");
    }

}
