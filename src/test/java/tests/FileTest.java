package tests;

import io.qameta.allure.*;
import org.junit.jupiter.api.*;

import java.io.File;

import static RestAssured.assertions.Conditions.hasInfo;
import static RestAssured.assertions.Conditions.hasStatusCode;

@Tag("FILE") // Тег для группировки тестов, связанных с файлами
public class FileTest extends BaseApiTest {

    @Attachment(value = "downloaded", type = "image/png") // Аннотация для добавления вложения в отчет Allure
    private byte[] attachFile(byte[] bytes) {
        return bytes; // Возвращаем байты файла для вложения в отчет
    }

    @Test
    @Severity(SeverityLevel.CRITICAL) // Устанавливаем уровень важности теста
    @Owner("Семионов Константин") // Указываем автора теста
    @Issue("files-controller/downloadImageUsingGET") // Ссылка на тест-кейс в системе управления задачами
    @DisplayName("[POSITIVE] Загрузка файла") // Читаемое имя теста для отображения в отчете
    public void positiveDownloadTest() {
        // Загружаем файл с помощью сервиса fileService и преобразуем его в массив байтов
        byte[] file = fileService.downloadBaseImage()
                .asResponse().asByteArray();
        attachFile(file); // Добавляем загруженный файл в отчет Allure
        // Ожидаемый файл для сравнения
        File expectedFile = new File("src/main/resources/testFile/threadqa.jpeg");
        // Сравниваем размер загруженного файла с размером ожидаемого файла
        Assertions.assertEquals(expectedFile.length(), file.length);
    }

    @Test
    @Severity(SeverityLevel.TRIVIAL)
    @Owner("Семионов Константин")
    @Issue("files-controller/uploadFileUsingPOST")
    @DisplayName("[POSITIVE] Скачивание последнего загруженного файла")
    public void positiveUploadTest() {
        // Ожидаемый файл для загрузки
        File expectedFile = new File("src/main/resources/testFile/threadqa.jpeg");
        // Загружаем файл с помощью сервиса fileService и проверяем статус ответа и сообщение
        fileService.uploadFile(expectedFile)
                .should(hasStatusCode(200)) // Проверка, что статус ответа 200
                .should(hasInfo("success", "file uploaded to server")); // Проверка, что файл успешно загружен на сервер
        // Скачиваем последний загруженный файл и преобразуем его в массив байтов
        byte[] actualFile = fileService.downloadLastFile().asResponse().asByteArray();
        attachFile(actualFile); // Добавляем загруженный файл в отчет Allure
        // Проверяем, что размер скачанного файла не равен нулю
        Assertions.assertTrue(actualFile.length != 0);
        // Сравниваем размер скачанного файла с размером ожидаемого файла
        Assertions.assertEquals(expectedFile.length(), actualFile.length);
    }
}
