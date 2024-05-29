package RestAssured.helper;

import io.qameta.allure.restassured.AllureRestAssured;

public class CustomTml {
    // Создание единственного экземпляра фильтра для логирования с помощью AllureRestAssured
    private static final AllureRestAssured FILTER = new AllureRestAssured();

    // Приватный конструктор для предотвращения создания экземпляров класса извне
    private CustomTml() {
    }

    /**
     * Метод для получения экземпляра CustomTml.
     *
     * @return Экземпляр CustomTml.
     */
    public static CustomTml customLogFilter() {
        return InitLogFilter.logFilter; // Возвращает статический экземпляр CustomTml
    }

    /**
     * Метод для настройки фильтра AllureRestAssured с пользовательскими шаблонами.
     *
     * @return Настроенный фильтр AllureRestAssured.
     */
    public AllureRestAssured withCustomTemplates() {
        FILTER.setRequestTemplate("http-request.ftl"); // Установка пользовательского шаблона для запросов
        FILTER.setResponseTemplate("http-response.ftl"); // Установка пользовательского шаблона для ответов
        return FILTER; // Возвращение настроенного фильтра
    }

    // Вложенный статический класс для ленивой инициализации экземпляра CustomTml
    private static class InitLogFilter {
        private static final CustomTml logFilter = new CustomTml(); // Инициализация экземпляра CustomTml
    }
}
