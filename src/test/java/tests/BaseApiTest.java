package tests;

import RestAssured.decorator.AdminUserResolver;
import RestAssured.decorator.LoginExtension;
import RestAssured.models.FullUser;
import RestAssured.services.FileService;
import RestAssured.services.GameService;
import RestAssured.services.StatusCodeService;
import RestAssured.services.UserService;
import io.qameta.allure.Step;
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

@ExtendWith({LoginExtension.class, AdminUserResolver.class})
public class BaseApiTest {

    static ResponseSpecification responseSpec = new ResponseSpecBuilder()
            .expectResponseTime(lessThan(5000L))  // ожидание времени ответа меньше 5000 миллисекунд
            .build();

    protected static UserService userService;
    protected static FileService fileService;
    protected static GameService gameService;
    protected static StatusCodeService statusCodeService;
    protected FullUser user;
    protected static boolean isUserRegistered = false;

    @BeforeAll
    public static void setUp() {
        RestAssured.responseSpecification = responseSpec;
        RestAssured.baseURI = URL;
        if (!isCustomLogAdded()) {
            RestAssured.filters(
                    new RequestLoggingFilter(),
                    new ResponseLoggingFilter(),
                    customLogFilter().withCustomTemplates()
            );
        }
        userService = new UserService();
        fileService = new FileService();
        gameService = new GameService();
        statusCodeService = new StatusCodeService();
    }

    @BeforeEach
    public void initTestUser() {
        user = getRandomUser();
        isUserRegistered = false;
    }

    @AfterEach
    public void deleteTestUser() {
        if (isUserRegistered) { // Проверка, зарегистрирован ли пользователь
            try {
                String token = userService.auth(user).asJwt();
                if (token != null) {
                    userService.deleteUser(token);
                }
            } catch (Exception e) {
                e.printStackTrace(); // Логируем исключение
            }
        }
    }
}
