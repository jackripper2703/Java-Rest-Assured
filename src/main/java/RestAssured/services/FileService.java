package RestAssured.services;

import RestAssured.assertions.AssertableResponse;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import lombok.SneakyThrows;

import java.io.File;
import java.nio.file.Files;

import static io.restassured.RestAssured.given;

public class FileService {

    @Step("Скачивание файла")
    public AssertableResponse downloadBaseImage() {
        // Метод для скачивания базового изображения
        return new AssertableResponse(given()
                .get("files/download") // Выполняем GET-запрос на эндпоинт /files/download
                .then()); // Возвращаем результат запроса
    }

    @Step("Скачивание последнего загруженного файла")
    public AssertableResponse downloadLastFile() {
        // Метод для скачивания последнего загруженного файла
        return new AssertableResponse(given()
                .get("files/downloadLastUploaded") // Выполняем GET-запрос на эндпоинт /files/downloadLastUploaded
                .then()); // Возвращаем результат запроса
    }

    @SneakyThrows
    @Step("Загрузка файла")
    public AssertableResponse uploadFile(File file) {
        // Метод для загрузки файла
        return new AssertableResponse(given().contentType(ContentType.MULTIPART) // Указываем, что тело запроса в формате multipart/form-data
                .multiPart("file", "myFile.jpg", Files.readAllBytes(file.toPath())) // Добавляем файл в тело запроса
                .post("files/upload") // Выполняем POST-запрос на эндпоинт /files/upload
                .then()); // Возвращаем результат запроса
    }
}
