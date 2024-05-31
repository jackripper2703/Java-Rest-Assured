package tests;

import RestAssured.decorator.AdminUserResolver;
import RestAssured.decorator.LoginExtension;
import RestAssured.models.FullUser;
import RestAssured.services.FileService;
import RestAssured.services.UserService;
import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import static RestAssured.helper.ConfigProvider.URL;
import static RestAssured.helper.CustomTml.customLogFilter;
import static RestAssured.helper.CustomTml.isCustomLogAdded;
import static RestAssured.helper.RandomTestData.getRandomUser;
import static org.hamcrest.Matchers.*;

@ExtendWith({LoginExtension.class, AdminUserResolver.class}) // Расширяем тесты дополнительными функциональностями
public class BaseApiTest {

    // Создаем спецификацию для ответов, которая будет применяться ко всем запросам
    static ResponseSpecification responseSpec = new ResponseSpecBuilder()
            .expectResponseTime(lessThan(5000L))  // ожидание времени ответа меньше 5000 миллисекунд
            .build();

    // Определяем сервисы для работы с пользователями и файлами
    protected static UserService userService;
    protected static FileService fileService;
    protected FullUser user;

    @BeforeAll // Метод, который будет выполнен перед всеми тестами в этом классе
    public static void setUp() {
        // Настраиваем общие параметры для всех запросов
        RestAssured.responseSpecification = responseSpec;
        RestAssured.baseURI = URL; // Устанавливаем базовый URL для запросов
        if (!isCustomLogAdded()){ // Проверка включалось ли логирование
        RestAssured.filters(
//                new RequestLoggingFilter(), // Логирование запросов
//                new ResponseLoggingFilter(), // Логирование ответов
                customLogFilter().withCustomTemplates());  // Использование кастомных шаблонов для логирования
        }
        // Инициализация сервисов
        userService = new UserService();
        fileService = new FileService();
    }

    @BeforeEach // Метод, который будет выполнен перед каждым тестом в этом классе
    public void initTestUser() {
        user = getRandomUser(); // Инициализация тестового пользователя случайными данными
    }

    @AfterEach // Метод, который будет выполнен после каждого теста в этом классе
    public void deleteTestUser() {
        try {
            // Аутентификация пользователя для получения токена
            String token = userService.auth(user).asJwt();
            if (token != null) userService.deleteUser(token); // Удаление пользователя, если токен не пустой
        } catch (Exception ignored) {
            // Игнорирование исключений, если что-то пойдет не так
        }
    }
}
