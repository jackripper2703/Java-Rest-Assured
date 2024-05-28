package tests;

import RestAssured.helper.CustomTml;
import RestAssured.services.FileService;
import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.jupiter.api.*;

import java.io.File;

import static RestAssured.assertions.Conditions.hasInfo;
import static RestAssured.assertions.Conditions.hasStatusCode;
import static RestAssured.helper.ConfigProvider.URL;

@Tag("FILE")
public class FileTest {
    private static FileService fileService;

    @BeforeAll
    public static void setUp() {

        RestAssured.baseURI = URL;
        RestAssured.filters(
                new RequestLoggingFilter(),
                new ResponseLoggingFilter(),
                CustomTml.customLogFilter().withCustomTemplates());
        fileService = new FileService();
    }

    @Attachment(value = "download", type = "image/png")
    private byte[] attachFile(byte[] bytes){
        return bytes;
    }

    @Test
    @Severity(SeverityLevel.CRITICAL) //    Возможные значения: BLOCKER, CRITICAL, NORMAL, MINOR, TRIVIAL.
    @Owner("Семионов Константин")   //  Автор теста
    @Issue("files-controller/downloadImageUsingGET") // Ссылка для allure на тест кейс
    @DisplayName("[POSITIVE] Загрузка файла")   //  Наименование теста
    public void positiveDownloadTest(){
        byte[] file = fileService.downLoadBaseImage().asResponse().asByteArray();
        attachFile(file);
        File expectedFile = new File("src/main/resources/testFile/threadqa.jpeg");

        Assertions.assertEquals(expectedFile.length(),file.length);
    }

    @Test
    @Severity(SeverityLevel.TRIVIAL)
    @Owner("Семионов Константин")
    @Issue("files-controller/uploadFileUsingPOST")
    @DisplayName("[POSITIVE] Скачивание последнего загруженного файла")
    public void positiveUploadTest(){
        File expectedFile = new File("src/main/resources/testFile/threadqa.jpeg");
        fileService.uploadFile(expectedFile)
                .should(hasStatusCode(200))
                .should(hasInfo("success","file uploaded to server"));
       byte[] actualFile = fileService.downLoadLastUploaded().asResponse().asByteArray();
        attachFile(actualFile);
       Assertions.assertTrue(actualFile.length != 0);
       Assertions.assertEquals(expectedFile.length(),actualFile.length);

    }
}
