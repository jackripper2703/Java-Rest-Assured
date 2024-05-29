package RestAssured.decorator;

import RestAssured.models.FullUser;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

// Класс AdminUserResolver реализует интерфейс ParameterResolver для разрешения параметров в тестах
public class AdminUserResolver implements ParameterResolver {

    /**
     * Определяет, поддерживается ли параметр, аннотированный @AdminUser.
     *
     * @param parameterContext Контекст параметра.
     * @param extensionContext Контекст расширения.
     * @return true, если параметр поддерживается, иначе false.
     * @throws ParameterResolutionException если произошла ошибка разрешения параметра.
     */
    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        // Проверяет, аннотирован ли параметр @AdminUser
        return parameterContext.isAnnotated(AdminUser.class);
    }

    /**
     * Разрешает параметр, создавая экземпляр FullUser для администратора.
     *
     * @param parameterContext Контекст параметра.
     * @param extensionContext Контекст расширения.
     * @return Экземпляр FullUser с данными администратора.
     * @throws ParameterResolutionException если тип параметра не поддерживается.
     */
    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        Class<?> type = parameterContext.getParameter().getType();
        if (FullUser.class.equals(type)) {
            // Создает и возвращает экземпляр FullUser для администратора
            return FullUser.builder()
                    .login("admin")
                    .pass("admin")
                    .build();
        }
        // Выбрасывает исключение, если тип параметра не поддерживается
        throw new ParameterResolutionException("Административный пользователь не сгенерирован: " + type);
    }
}
