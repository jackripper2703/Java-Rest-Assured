package tests;

import RestAssured.decorator.AdminUser;
import RestAssured.decorator.AdminUserResolver;
import RestAssured.models.FullUser;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.*;

import static RestAssured.assertions.Conditions.*;
import static RestAssured.helper.RandomTestData.*;

@Tag("USER")
@Owner("Семионов Константин")
@ExtendWith(AdminUserResolver.class)
public class UserTest extends BaseApiTest {


    @Test
    @Severity(SeverityLevel.BLOCKER) //    Возможные значения: BLOCKER, CRITICAL, NORMAL, MINOR, TRIVIAL.
    @Issue("user-controller-new/addUserUsingPOST")
    @DisplayName("[POSITIVE] Регистрация пользователя без игр")
    public void positiveRegistrationTest() {
        userService.register(user)
                .should(matchesSchema("userDTO.json"))
                .should(hasStatusCode(201))
                .should(hasInfo("success", "User created"));
    }

    @Test
    @Severity(SeverityLevel.BLOCKER)
    @Issue("user-controller-new/addUserUsingPOST")
    @DisplayName("[POSITIVE] Регистрация пользователя с игрой")
    public void positiveRegisterWithGameTest() {
        user = getRandomUserWithGames();
        userService.register(user)
                .should(matchesSchema("userDTO.json"))
                .should(hasStatusCode(201))
                .should(hasInfo("success", "User created"));
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Issue("user-controller-new/addUserUsingPOST")
    @DisplayName("[NEGATIVE] Регистрация пользователя для существующего логина")
    public void negativeRegistrationExistsTest() {
        userService.register(user)
                .should(hasStatusCode(201))
                .should(matchesSchema("userDTO.json"))
                .should(hasInfo("success", "User created"));
        userService.register(user)
                .should(hasStatusCode(400))
                .should(hasInfo("fail", "Login already exist"));
    }


    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Issue("user-controller-new/addUserUsingPOST")
    @DisplayName("[NEGATIVE] Регистрация пользователя без пароля")
    public void negativeRegistrationNoPassTest() {
        user.setPass(null);
        userService.register(user)
                .should(hasStatusCode(400))
                .should(hasInfo("fail", "Missing login or password"));
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Issue("createAuthenticationTokenUsingPOST")
    @DisplayName("[POSITIVE] Получение JWT токена для Админа")
    public void positiveAdminAuthTest(@AdminUser FullUser user) {
        String token = userService.auth(user)
                .should(hasStatusCode(200))
                .asJwt();
        Assertions.assertNotNull(token);
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Issue("createAuthenticationTokenUsingPOST")
    @DisplayName("[POSITIVE] Получение JWT токена для нового пользователя")
    public void positiveNewUserAuthTest() {
        userService.register(user);
        String token = userService.auth(user)
                .should(hasStatusCode(200)).asJwt();
        Assertions.assertNotNull(token);
    }


    @Test
    @Severity(SeverityLevel.NORMAL)
    @Issue("createAuthenticationTokenUsingPOST")
    @DisplayName("[NEGATIVE] Получение JWT токена для не зарегистированого пользователя")
    public void negativeNewUserAuthTest() {
        userService.auth(user)
                .should(hasStatusCode(401));
    }


    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Issue("user-controller-new/getUserUsingGET")
    @DisplayName("[POSITIVE] Получения информации о пользователе")
    public void positiveGetUserInfoTest(@AdminUser FullUser user) {
        String token = userService.auth(user)
                .asJwt();
        userService.getUserInfo(token)
                .should(matchesSchema("info.json"));
    }


    @Test
    @Severity(SeverityLevel.MINOR)
    @Issue("user-controller-new/getUserUsingGET")
    @DisplayName("[NEGATIVE] Получения информации о пользователе передавая неверный токен")
    public void negativeGetUserInfoTest() {
        userService.getUserInfo("23")
                .should(hasStatusCode(401));
    }


    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Issue("user-controller-new/getUserUsingGET")
    @DisplayName("[NEGATIVE] Получения информации о пользователе не передавая токен")
    public void negativeGetUserInfoNotToketTest() {
        userService.getUserInfo()
                .should(hasStatusCode(401));
    }


    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Issue("user-controller-new/updateUserPasswordUsingPUT")
    @DisplayName("[POSITIVE] Обновление пароля у пользователя")
    public void positiveChangeUserPassTest() {
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
    @Issue("user-controller-new/updateUserPasswordUsingPUT")
    @DisplayName("[NEGATIVE] Смена пароля у админа")
    public void negativeChangeAdminPassTest(@AdminUser FullUser user) {
        String token = userService.auth(user).asJwt();
        String newPass = "qwerty";
        userService.updatePass(newPass, token)
                .should(hasStatusCode(400))
                .should(hasInfo("fail", "Cant update base users"));
    }


    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Issue("user-controller-new/deleteUserFromDbUsingDELETE")
    @DisplayName("[POSITIVE] Удаляет пользователя из Базы данных")
    public void positiveDeleteUserTest() {
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
    @Issue("user-controller-new/deleteUserFromDbUsingDELETE")
    @DisplayName("[NEGATIVE] Удаления админа")
    public void negativeDeleteAdminTest(@AdminUser FullUser user) {
        String token = userService.auth(user)
                .asJwt();
        userService.deleteUser(token)
                .should(hasStatusCode(400))
                .should(hasInfo("fail", "Cant delete base users"));
    }


    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Issue("user-controller-new/getLast100UsersUsingGET")
    @DisplayName("[POSITIVE] Показывает логины последних 100 зарегистрированных пользователей")
    public void positiveUsersListTest() {
        List<String> users = userService.getAllUsers()
                .should(matchesSchema("allUsers.json"))
                .asList(String.class);
        Assertions.assertTrue(users.size() > 3);
    }
}