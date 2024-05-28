package RestAssured.decorator;

import RestAssured.models.FullUser;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

public class AdminUserResolver implements ParameterResolver {

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.isAnnotated(AdminUser.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
       Class<?> type = parameterContext.getParameter().getType();
       if(FullUser.class.equals(type)){
           return FullUser.builder()
                   .login("admin")
                   .pass("admin")
                   .build();
       }
       throw new ParameterResolutionException("Админиский пользователь не сгенерирован" + type);
    }
}
