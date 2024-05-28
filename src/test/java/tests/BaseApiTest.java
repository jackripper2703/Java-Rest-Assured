package tests;

import RestAssured.decorator.AdminUserResolver;
import RestAssured.decorator.LoginExtension;
import RestAssured.helper.CustomTml;
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
import static RestAssured.helper.RandomTestData.getRandomUser;
import static org.hamcrest.Matchers.*;

@ExtendWith({LoginExtension.class,AdminUserResolver.class})

public class BaseApiTest {

    // Создаем спецификацию для ответов
    static ResponseSpecification responseSpec = new ResponseSpecBuilder()
            .expectResponseTime(lessThan(5000L))  // время в миллисекундах
            .build();

    protected static UserService userService;
    protected static FileService fileService;
    protected FullUser user;

    @BeforeAll
    public static void setUp() {
        RestAssured.responseSpecification = responseSpec;
        RestAssured.baseURI = URL;
        RestAssured.filters(
                new RequestLoggingFilter(),
                new ResponseLoggingFilter(),
                CustomTml.customLogFilter().withCustomTemplates());
        userService = new UserService();
        fileService = new FileService();
    }

    @BeforeEach
    public void initTestUser() {
        user = getRandomUser();
    }

    @AfterEach
    public void deleteTestUser() {
        try {
            String token = userService.auth(user).asJwt();
            if (token !=null)userService.deleteUser(token);
        } catch (Exception ignored) {}
    }
}