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
    public AssertableResponse downloadBaseImage(){
        return new AssertableResponse(given().get("files/download").then());
    }

    @Step("Скачивание последнего загруженного файла")
    public AssertableResponse downloadLastFile(){
        return new AssertableResponse(given().get("files/downloadLastUploaded").then());
    }

    @SneakyThrows
    @Step("Загрузка файла")
    public AssertableResponse uploadFile(File file){
        return new AssertableResponse(given().contentType(ContentType.MULTIPART)
                .multiPart("file", "myFile.jpg", Files.readAllBytes(file.toPath()))
                .post("files/upload").then());
    }


}