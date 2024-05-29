# RestAssured API Testing Project

## Описание

Этот проект предназначен для автоматизации тестирования REST API с использованием библиотеки RestAssured и JUnit 5. Проект включает генерацию случайных тестовых данных, проверку различных условий ответов API, а также интеграцию с Allure для генерации отчетов о тестировании.

## Структура проекта
### src/main/java/

- **RestAssured.assertions**: Классы для проверки условий ответов API (Condition и его реализации).
- **RestAssured.decorator**: Классы для расширения возможностей JUnit 5 тестов (LoginExtension, AdminUserResolver, AdminUser).
- **RestAssured.helper**: Утилиты и хелперы для генерации случайных данных, работы с JSON и конфигурациями (RandomTestData, JsonHelper, ConfigProvider).
- **RestAssured.models**: Pojo классы
- **RestAssured.services**: Классы, содержащие методы для взаимодействия с различными API сервисами (UserService, FileService).
- **resources/tpl**: Стили Allure
- **resources/schemes**: JSON схемы для валидации структуры ответов API.
- **resources/application.conf**: Конфигурационный файл для хранения параметров конфигурации.

### src/test/java/
- **tests/BaseApiTest**: Базовый класс в котором происходит инициализация, а так же описаны Before и After для тестов  
- **tests/FileTest**: Тесты для files-controller
- **tests/UserTest**: Тесты для user-controller

## Установка

1. Клонируйте репозиторий:
    ```bash
    git clone https://github.com/jackripper2703/Java-Rest-Assured.git
    ```
2. Перейдите в директорию проекта:
    ```bash
    cd Java-Rest-Assured
    ```
3. Установите зависимости:
    ```bash
    mvn clean install
    ```

4. Запуск всех тестов:
   ```bash
   mvn test
   ```
