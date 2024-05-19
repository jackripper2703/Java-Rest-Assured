/**
 * Api тесты для Rest Assured
 * Тест 1
 * 1.Используя сервис https://reqres.in/ получить список пользователей со второй страницы
 * 2.Убедиться что имена файлов-аватаров пользователей совпадают;
 * 3.Убедиться, что email пользователей имеет окончание reqres.in;
 * Тест 2
 * 1.Используя сервис https://reqres.in/ протестировать регистрацию пользователя в системе
 * 2. Необходимо создание 2 тестов:
 * - успешная регистрация
 * - регистрация с ошибкой из-за отсутствия пароля,
 * 3.Проверить коды ошибок.
 * Тест 3
 * 1.Используя сервис https://reqres.in/ убедиться, что операция LIST<RESOURCE> возвращает
 * данные, отсортированные по годам.
 * Тест 4
 * 1.Используя сервис https://reqres.in/ попробовать удалить второго пользователя и сравнить
 * статус-код
 * Тест 5
 * 1.Используя сервис https://reqres.in/ обновить информацию о пользователе и сравнить дату
 * обновления с текущей датой на машине
 */

package RestAssred;

import org.junit.Assert;
import org.junit.Test;

import java.time.Clock;
import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;

public class TestAPI {
    private final static String URL = "https://reqres.in/";

    @Test
    public void checkAvatarAndIdTest(){
        Specification.installSpec(Specification.requestSpec(URL),Specification.responseSpec(200));
        List<UserDate> users = given()
                .when()
                .get("api/users?page=2")
                .then().log().all()
                .extract().body().jsonPath().getList("data", UserDate.class);

        List<String> avatars = users.stream().map(UserDate::getAvatar).collect(Collectors.toList());
        List<String> ids = users.stream().map(x->x.getId().toString()).collect(Collectors.toList());
        for (int i = 0; i < avatars.size(); i++) {
            Assert.assertTrue(avatars.get(i).contains(ids.get(i)));
        }
    }

    @Test
    public void successRegTest(){
        Specification.installSpec(Specification.requestSpec(URL),Specification.responseSpec(200));
        Integer id = 4;
        String token = "QpwL5tke4Pnpja7X4";
        Register user = new Register("eve.holt@reqres.in", "pistol");
        SuccessReg successReg = given()
                .body(user)
                .when()
                .post("api/register")
                .then().log().all()
                .extract().as(SuccessReg.class);

        Assert.assertNotNull(successReg.getId());
        Assert.assertNotNull(successReg.getToken());

        Assert.assertEquals(id, successReg.getId());
        Assert.assertEquals(token, successReg.getToken());
    }

    @Test
    public void unSuccessRegTest() {
        Specification.installSpec(Specification.requestSpec(URL), Specification.responseSpec(400));
        Register user = new Register("eve.holt@reqres.in", "");
        UnSuccessReg unSuccessReg = given()
                .body(user)
                .when()
                .post("api/register")
                .then().log().all()
                .extract().as(UnSuccessReg.class);

        Assert.assertNotNull(unSuccessReg.getError());

        Assert.assertEquals("Missing password", unSuccessReg.getError());
    }

    @Test
    public void sortedYearsTest() {
        Specification.installSpec(Specification.requestSpec(URL), Specification.responseSpec(200));

        List<ColorsData> colors = given()
                .when()
                .get("api/unknown")
                .then().log().all()
                .extract().body().jsonPath().getList("data", ColorsData.class);
        List<Integer> years = colors.stream().map(ColorsData::getYear).collect(Collectors.toList());
        List<Integer> sortedYears = years.stream().sorted().collect(Collectors.toList());

        Assert.assertEquals(sortedYears, years);
    }

    @Test
    public void deleteUserTest() {
        Specification.installSpec(Specification.requestSpec(URL), Specification.responseSpec(204));

        given()
                .when()
                .delete("/api/users/2")
                .then().log().all();
    }

    @Test
    public void timeTest() {
        Specification.installSpec(Specification.requestSpec(URL), Specification.responseSpec(200));
        UserTime user = new UserTime("morpheus", "zion resident");
        ServerTime time = given()
                .body(user)
                .when()
                .put("api/users/2")
                .then().log().all()
                .extract().as(ServerTime.class);

        String regexServer = "(.{6})$";
        String regexClient = "(.{12})$";
        String currentTime = Clock.systemUTC().instant().toString().replaceAll(regexClient, "");

        Assert.assertEquals(currentTime, time.getUpdatedAt().replaceAll(regexServer, ""));
    }
}
