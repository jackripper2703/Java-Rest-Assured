package RestAssured.helper;

import RestAssured.models.*;
import com.github.javafaker.Faker;
import io.qameta.allure.Step;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class RandomTestData {
    private static final Random random = new Random(); // Инициализация генератора случайных чисел
    private static final Faker faker = new Faker(); // Инициализация библиотеки Faker для генерации случайных данных

    @Step("Генерация рандомной игры")
    public static GamesItem getRandomGame() {
        // Генерация случайного DLC для игры
        SimilarDlc similarDlc = SimilarDlc.builder()
                .isFree(false) // DLC не является бесплатным
                .dlcNameFromAnotherGame(faker.funnyName().name()) // Случайное имя DLC
                .build();

        // Генерация случайного элемента DLC
        DlcsItem dlcsItem = DlcsItem.builder()
                .rating(faker.random().nextInt(10)) // Случайный рейтинг DLC
                .price(faker.random().nextInt(1, 500)) // Случайная цена DLC
                .description(faker.funnyName().name()) // Случайное описание DLC
                .dlcName(faker.dragonBall().character()) // Случайное имя DLC
                .isDlcFree(false) // DLC не является бесплатным
                .similarDlc(similarDlc) // Добавление сгенерированного похожего DLC
                .build();

        // Генерация случайных системных требований для игры
        Requirements requirements = Requirements.builder()
                .ramGb(faker.random().nextInt(4, 16)) // Случайное количество оперативной памяти
                .osName("Windows") // Название операционной системы
                .hardDrive(faker.random().nextInt(30, 70)) // Случайное количество памяти на жестком диске
                .videoCard("NVIDIA") // Название видеокарты
                .build();

        // Генерация случайной игры
        return GamesItem.builder()
                .requirements(requirements) // Добавление сгенерированных системных требований
                .genre(faker.book().genre()) // Случайный жанр
                .price(random.nextInt(400)) // Случайная цена
                .description(faker.funnyName().name()) // Случайное описание
                .company(faker.company().name()) // Случайное название компании
                .isFree(false) // Игра не является бесплатной
                .title(faker.beer().name()) // Случайное название игры
                .rating(faker.random().nextInt(10)) // Случайный рейтинг игры
                .publishDate(LocalDateTime.now().toString()) // Текущая дата публикации
                .requiredAge(random.nextBoolean()) // Требование к возрасту (случайное значение)
                .tags(Arrays.asList("shooter", "quests")) // Список тегов
                .dlcs(Collections.singletonList(dlcsItem)) // Список сгенерированных DLC
                .build();
    }

    @Step("Инициализация нового случайного пользователя с играми")
    public static FullUser getRandomUserWithGames() {
        int randomNumber = Math.abs(random.nextInt()); // Генерация случайного числа для уникальности логина
        GamesItem gamesItem = getRandomGame(); // Генерация случайной игры
        return FullUser.builder()
                .login(faker.name().username() + randomNumber) // Генерация случайного логина
                .pass(faker.internet().password()) // Генерация случайного пароля
                .games(Collections.singletonList(gamesItem)) // Добавление сгенерированной игры в список игр пользователя
                .build();
    }

    @Step("Инициализация нового случайного пользователя")
    public static FullUser getRandomUser() {
        int randomNumber = Math.abs(random.nextInt()); // Генерация случайного числа для уникальности логина
        return FullUser.builder()
                .login("Jack" + randomNumber) // Уникальный логин пользователя
                .pass("Ripper") // Статический пароль
                .build();
    }

    @Step("Инициализация админа")
    public static FullUser getAdmin() {
        return FullUser.builder()
                .login("admin") // Логин администратора
                .pass("admin") // Пароль администратора
                .build();
    }
}
