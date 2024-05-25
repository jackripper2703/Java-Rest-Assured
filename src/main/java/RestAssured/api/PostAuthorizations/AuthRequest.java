package RestAssured.api.PostAuthorizations;

import RestAssured.helper.Specification;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.Assertions;

import static RestAssured.helper.ConfigProvider.*;
import static io.restassured.RestAssured.given;

@Data
@AllArgsConstructor
public class AuthRequest {

    public static String getToken() {
        Specification.installSpec(Specification.requestSpecification(URLVPLUSE), Specification.responseSpec(200));
        AuthResponse authResponse = given()
                .multiPart("username", PHONE_NUMBER)
                .multiPart("password", PASSWORD)
                .post("account/b2c/login/access-token")
                .then()
                .extract().as(AuthResponse.class);

        Assertions.assertNotNull(authResponse.getAccess_token());
        Assertions.assertNotNull(authResponse.getRefresh_token());
        Assertions.assertEquals("bearer", authResponse.getToken_type());
        return authResponse.getAccess_token();
    }

}
