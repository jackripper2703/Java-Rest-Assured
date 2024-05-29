# RestAssured API Testing Project

## Описание

Этот проект предназначен для автоматизации тестирования REST API с использованием библиотеки RestAssured и JUnit 5. Проект включает генерацию случайных тестовых данных, проверку различных условий ответов API, а также интеграцию с Allure для генерации отчетов о тестировании.

## Структура проекта

- **RestAssured.services**: Классы, содержащие методы для взаимодействия с различными API сервисами (UserService, FileService).
- **RestAssured.helper**: Утилиты и хелперы для генерации случайных данных, работы с JSON и конфигурациями (RandomTestData, JsonHelper, ConfigProvider).
- **RestAssured.assertions**: Классы для проверки условий ответов API (Condition и его реализации).
- **RestAssured.decorator**: Классы для расширения возможностей JUnit 5 тестов (LoginExtension, AdminUserResolver, AdminUser).
- **src/test/resources/schemes**: JSON схемы для валидации структуры ответов API.
- **src/main/resources/application.conf**: Конфигурационный файл для хранения параметров конфигурации.

## Установка

1. Клонируйте репозиторий:
    ```bash
    git clone https://github.com/your-username/rest-assured-api-testing.git
    ```
2. Перейдите в директорию проекта:
    ```bash
    cd rest-assured-api-testing
    ```
3. Установите зависимости:
    ```bash
    mvn clean install
    ```

## Использование

### Запуск тестов

Запуск всех тестов:
```bash
mvn test
