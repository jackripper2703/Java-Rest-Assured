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

@Tag("USER") // Тег для группировки тестов, связанных с пользователями
@Owner("Семионов Константин") // Указываем автора теста
@ExtendWith(AdminUserResolver.class) // Расширяем тесты дополнительной функциональностью для работы с администраторами
public class UserTest extends BaseApiTest {

    @Test
    @Severity(SeverityLevel.BLOCKER) // Устанавливаем уровень важности теста
    @Issue("user-controller-new/addUserUsingPOST") // Ссылка на тест-кейс в системе управления задачами
    @DisplayName("[POSITIVE] Регистрация пользователя без игр") // Читаемое имя теста для отображения в отчете
    public void positiveRegistrationTest() {
        userService.register(user) // Регистрация пользователя
                .should(matchesSchema("userDTO.json")) // Проверка соответствия JSON схеме
                .should(hasStatusCode(201)) // Проверка статуса ответа 201
                .should(hasInfo("success", "User created")); // Проверка сообщения об успешной регистрации
    }

    @Test
    @Severity(SeverityLevel.BLOCKER)
    @Issue("user-controller-new/addUserUsingPOST")
    @DisplayName("[POSITIVE] Регистрация пользователя с игрой")
    public void positiveRegisterWithGameTest() {
        user = getRandomUserWithGames(); // Генерация пользователя с играми
        userService.register(user) // Регистрация пользователя
                .should(matchesSchema("userDTO.json"))
                .should(hasStatusCode(201))
                .should(hasInfo("success", "User created"));
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Issue("user-controller-new/addUserUsingPOST")
    @DisplayName("[NEGATIVE] Регистрация пользователя для существующего логина")
    public void negativeRegistrationExistsTest() {
        userService.register(user) // Регистрация пользователя
                .should(hasStatusCode(201))
                .should(matchesSchema("userDTO.json"))
                .should(hasInfo("success", "User created"));
        userService.register(user) // Повторная регистрация того же пользователя
                .should(hasStatusCode(402)) // Проверка статуса ответа 400
                .should(hasInfo("fail", "Login already exist")); // Проверка сообщения об ошибке
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Issue("user-controller-new/addUserUsingPOST")
    @DisplayName("[NEGATIVE] Регистрация пользователя без пароля")
    public void negativeRegistrationNoPassTest() {
        user.setPass(null); // Установка пароля пользователя в null
        userService.register(user) // Попытка регистрации пользователя
                .should(hasStatusCode(400)) // Проверка статуса ответа 400
                .should(hasInfo("fail", "Missing login or password")); // Проверка сообщения об ошибке
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Issue("createAuthenticationTokenUsingPOST")
    @DisplayName("[POSITIVE] Получение JWT токена для Админа")
    public void positiveAdminAuthTest(@AdminUser FullUser user) {
        String token = userService.auth(user) // Аутентификация администратора
                .should(hasStatusCode(200)) // Проверка статуса ответа 200
                .asJwt(); // Получение JWT токена
        Assertions.assertNotNull(token); // Проверка, что токен не null
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Issue("createAuthenticationTokenUsingPOST")
    @DisplayName("[POSITIVE] Получение JWT токена для нового пользователя")
    public void positiveNewUserAuthTest() {
        userService.register(user); // Регистрация нового пользователя
        String token = userService.auth(user) // Аутентификация пользователя
                .should(hasStatusCode(200)) // Проверка статуса ответа 200
                .asJwt(); // Получение JWT токена
        Assertions.assertNotNull(token); // Проверка, что токен не null
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Issue("createAuthenticationTokenUsingPOST")
    @DisplayName("[NEGATIVE] Получение JWT токена для незарегистрированного пользователя")
    public void negativeNewUserAuthTest() {
        userService.auth(user) // Попытка аутентификации незарегистрированного пользователя
                .should(hasStatusCode(401)); // Проверка статуса ответа 401
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Issue("user-controller-new/getUserUsingGET")
    @DisplayName("[POSITIVE] Получение информации о пользователе")
    public void positiveGetUserInfoTest(@AdminUser FullUser user) {
        String token = userService.auth(user) // Аутентификация администратора
                .asJwt();
        userService.getUserInfo(token) // Получение информации о пользователе
                .should(matchesSchema("info.json")); // Проверка соответствия JSON схеме
    }

    @Test
    @Severity(SeverityLevel.MINOR)
    @Issue("user-controller-new/getUserUsingGET")
    @DisplayName("[NEGATIVE] Получение информации о пользователе, передавая неверный токен")
    public void negativeGetUserInfoTest() {
        userService.getUserInfo("23") // Попытка получить информацию о пользователе с неверным токеном
                .should(hasStatusCode(401)); // Проверка статуса ответа 401
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Issue("user-controller-new/getUserUsingGET")
    @DisplayName("[NEGATIVE] Получение информации о пользователе без токена")
    public void negativeGetUserInfoNotTokenTest() {
        userService.getUserInfo() // Попытка получить информацию о пользователе без токена
                .should(hasStatusCode(401)); // Проверка статуса ответа 401
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Issue("user-controller-new/updateUserPasswordUsingPUT")
    @DisplayName("[POSITIVE] Обновление пароля у пользователя")
    public void positiveChangeUserPassTest() {
        userService.register(user); // Регистрация пользователя
        String token = userService.auth(user).asJwt(); // Аутентификация пользователя
        userService.getUserInfo(token); // Получение информации о пользователе
        String newPass = "reppiR"; // Новый пароль
        userService.updatePass(newPass, token) // Обновление пароля
                .should(hasStatusCode(200)) // Проверка статуса ответа 200
                .should(hasInfo("success", "User password successfully changed")); // Проверка сообщения об успешном обновлении пароля
        user.setPass(newPass); // Установка нового пароля для пользователя
        token = userService.auth(user).asJwt(); // Повторная аутентификация пользователя с новым паролем
        Assertions.assertNotNull(token); // Проверка, что токен не null
        userService.getUserInfo(token) // Получение информации о пользователе
                .should(hasStatusCode(200)); // Проверка статуса ответа 200
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Issue("user-controller-new/updateUserPasswordUsingPUT")
    @DisplayName("[NEGATIVE] Смена пароля у админа")
    public void negativeChangeAdminPassTest(@AdminUser FullUser user) {
        String token = userService.auth(user).asJwt(); // Аутентификация администратора
        String newPass = "qwerty"; // Новый пароль
        userService.updatePass(newPass, token) // Попытка обновления пароля администратора
                .should(hasStatusCode(400)) // Проверка статуса ответа 400
                .should(hasInfo("fail", "Cant update base users")); // Проверка сообщения об ошибке
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Issue("user-controller-new/deleteUserFromDbUsingDELETE")
    @DisplayName("[POSITIVE] Удаление пользователя из базы данных")
    public void positiveDeleteUserTest() {
        userService.register(user); // Регистрация пользователя
        String token = userService.auth(user) // Аутентификация пользователя
                .should(hasStatusCode(200)) // Проверка статуса ответа 200
                .asJwt();
        userService.deleteUser(token) // Удаление пользователя
                .should(hasStatusCode(200)) // Проверка статуса ответа 200
                .should(hasInfo("success", "User successfully deleted")); // Проверка сообщения об успешном удалении пользователя
        userService.auth(user) // Попытка аутентификации удаленного пользователя
                .should(hasStatusCode(401)); // Проверка статуса ответа 401
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Issue("user-controller-new/deleteUserFromDbUsingDELETE")
    @DisplayName("[NEGATIVE] Удаление администратора")
    public void negativeDeleteAdminTest(@AdminUser FullUser user) {
        String token = userService.auth(user).asJwt(); // Аутентификация администратора
        userService.deleteUser(token) // Попытка удаления администратора
                .should(hasStatusCode(400)) // Проверка статуса ответа 400
                .should(hasInfo("fail", "Cant delete base users")); // Проверка сообщения об ошибке
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Issue("user-controller-new/getLast100UsersUsingGET")
    @DisplayName("[POSITIVE] Показ логинов последних 100 зарегистрированных пользователей")
    public void positiveUsersListTest() {
        List<String> users = userService.getAllUsers() // Получение списка пользователей
                .should(matchesSchema("allUsers.json")) // Проверка соответствия JSON схеме
                .asList(String.class); // Преобразование ответа в список строк
        Assertions.assertTrue(users.size() > 3); // Проверка, что список содержит более 3 пользователей
    }
}
