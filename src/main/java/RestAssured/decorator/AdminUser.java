package RestAssured.decorator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация для пометки параметра как административного пользователя.
 */
@Target(ElementType.PARAMETER) // Указывает, что аннотация может быть применена только к параметрам методов
@Retention(RetentionPolicy.RUNTIME) // Указывает, что аннотация будет доступна в runtime через reflection
public @interface AdminUser {
    // Пустое тело аннотации, используется только для метки параметра
}
