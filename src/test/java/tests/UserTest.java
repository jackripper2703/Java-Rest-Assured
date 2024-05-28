package tests;

import RestAssured.helper.CustomTml;
import RestAssured.models.FullUser;
import RestAssured.services.UserService;
import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.jupiter.api.*;

import java.util.*;

import static RestAssured.assertions.Conditions.hasInfo;
import static RestAssured.assertions.Conditions.hasStatusCode;
import static RestAssured.helper.ConfigProvider.URL;

@Tag("USER")
public class UserTest {
    private static Random random;
    private static UserService userService;

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = URL;
        RestAssured.filters(
                new RequestLoggingFilter(),
                new ResponseLoggingFilter(),
                CustomTml.customLogFilter().withCustomTemplates());
        random = new Random();
        userService = new UserService();
    }

    @Step("Создания нового пользователя с логином (Jack + Рандомное число) и паролем (Ripper)")
    private FullUser getRandomUser() {
        int randomNumber = Math.abs(random.nextInt());
        return FullUser.builder()
                .login("Jack" + randomNumber)
                .pass("Ripper")
                .build();
    }

    private FullUser getAdmin() {
        return FullUser.builder()
                .login("admin")
                .pass("admin")
                .build();
    }

    @Test
    @Severity(SeverityLevel.BLOCKER) //    Возможные значения: BLOCKER, CRITICAL, NORMAL, MINOR, TRIVIAL.
    @Owner("Семионов Константин")
    @Issue("user-controller-new/addUserUsingPOST")
    @DisplayName("[POSITIVE] Регистрация пользователя")
    public void positiveRegistrationTest() {
        FullUser user = getRandomUser();
        userService.register(user)
                .should(hasStatusCode(201))
                .should(hasInfo("success", "User created"));
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Семионов Константин")
    @Issue("user-controller-new/addUserUsingPOST")
    @DisplayName("[NEGATIVE] Регистрация пользователя для существующего логина")
    public void negativeRegistrationExistsTest() {
        FullUser user = getRandomUser();
        userService.register(user)
                .should(hasStatusCode(201))
                .should(hasInfo("success", "User created"));
        userService.register(user)
                .should(hasStatusCode(400))
                .should(hasInfo("fail", "Login already exist"));
    }


    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Семионов Константин")
    @Issue("user-controller-new/addUserUsingPOST")
    @DisplayName("[NEGATIVE] Регистрация пользователя без пароля")
    public void negativeRegistrationNoPassTest() {
        FullUser user = getRandomUser();
        user.setPass(null);
        userService.register(user)
                .should(hasStatusCode(400))
                .should(hasInfo("fail", "Missing login or password"));
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Семионов Константин")
    @Issue("createAuthenticationTokenUsingPOST")
    @DisplayName("[POSITIVE] Получение JWT токена для Админа")
    public void positiveAdminAuthTest() {
        FullUser user = getAdmin();
        String token = userService.auth(user)
                .should(hasStatusCode(200))
                .asJwt();
        Assertions.assertNotNull(token);
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Семионов Константин")
    @Issue("createAuthenticationTokenUsingPOST")
    @DisplayName("[POSITIVE] Получение JWT токена для нового пользователя")
    public void positiveNewUserAuthTest() {
        FullUser user = getRandomUser();
        userService.register(user);
        String token = userService.auth(user)
                .should(hasStatusCode(200)).asJwt();
        Assertions.assertNotNull(token);
    }


    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Семионов Константин")
    @Issue("createAuthenticationTokenUsingPOST")
    @DisplayName("[NEGATIVE] Получение JWT токена для не зарегистированого пользователя")
    public void negativeNewUserAuthTest() {
        FullUser user = getRandomUser();
        userService.auth(user)
                .should(hasStatusCode(401));
    }


    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Семионов Константин")
    @Issue("user-controller-new/getUserUsingGET")
    @DisplayName("[POSITIVE] Получения информации о пользователе")
    public void positiveGetUserInfoTest() {
        FullUser user = getAdmin();
        String token = userService.auth(user)
                .asJwt();
        userService.getUserInfo(token);
    }


    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Семионов Константин")
    @Issue("user-controller-new/getUserUsingGET")
    @DisplayName("[NEGATIVE] Получения информации о пользователе передавая неверный токен")
    public void negativeGetUserInfoTest() {
        userService.getUserInfo("23")
                .should(hasStatusCode(401));
    }


    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Семионов Константин")
    @Issue("user-controller-new/getUserUsingGET")
    @DisplayName("[NEGATIVE] Получения информации о пользователе не передавая токен")
    public void negativeGetUserInfoNotToketTest() {
        userService.getUserInfo()
                .should(hasStatusCode(401));
    }


    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Семионов Константин")
    @Issue("updateUserPasswordUsingPUT")
    @DisplayName("[POSITIVE] Обновление пароля у пользователя")
    public void positiveChangeUserPassTest() {
        FullUser user = getRandomUser();
        userService.register(user);
        String token = userService.auth(user).asJwt();
        userService.getUserInfo(token);
        String newPass = "reppiR";
        userService.updatePass(newPass, token)
                .should(hasStatusCode(200))
                .should(hasInfo("success", "User password successfully changed"));
        user.setPass(newPass);
        token = userService.auth(user)
                .asJwt();
        Assertions.assertNotNull(token);
        userService.getUserInfo(token)
                .should(hasStatusCode(200));
    }


    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Семионов Константин")
    @Issue("updateUserPasswordUsingPUT")
    @DisplayName("[NEGATIVE] Смена пароля у админа")
    public void negativeChangeAdminPassTest() {
        FullUser user = getAdmin();
        String token = userService.auth(user).asJwt();
        String newPass = "qwerty";
        userService.updatePass(newPass, token)
                .should(hasStatusCode(400))
                .should(hasInfo("fail", "Cant update base users"));
    }


    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Семионов Константин")
    @Issue("user-controller-new/deleteUserFromDbUsingDELETE")
    @DisplayName("[POSITIVE] Удаляет пользователя из Базы данных")
    public void positiveDeleteUserTest() {
        FullUser user = getRandomUser();
        userService.register(user);
        String token = userService.auth(user)
                .should(hasStatusCode(200))
                .asJwt();
        userService.deleteUser(token)
                .should(hasStatusCode(200))
                .should(hasInfo("success", "User successfully deleted"));
        userService.auth(user)
                .should(hasStatusCode(401));
    }


    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Семионов Константин")
    @Issue("user-controller-new/deleteUserFromDbUsingDELETE")
    @DisplayName("[NEGATIVE] Удаления админа")
    public void negativeDeleteAdminTest() {
        FullUser user = getAdmin();
        String token = userService.auth(user)
                .asJwt();
        userService.deleteUser(token)
                .should(hasStatusCode(400))
                .should(hasInfo("fail", "Cant delete base users"));
    }


    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Семионов Константин")
    @Issue("user-controller-new/getLast100UsersUsingGET")
    @DisplayName("[POSITIVE] Показывает логины последних 100 зарегистрированных пользователей")
    public void positiveUsersListTest() {
        List<String> users = userService.getAllUsers().asList(String.class);
        Assertions.assertTrue(users.size() > 3);
    }
}