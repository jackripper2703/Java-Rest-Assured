package tests;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;

import static RestAssured.assertions.Conditions.*;

public class StatusCodeTest  extends BaseApiTest {

    @Test
    @Severity(SeverityLevel.MINOR)
    @Issue("status-codes-controller/getBadRequest400UsingGET")
    @DisplayName("[POSITIVE] bad-request")
    public void badRequestTest() {
        statusCodeService.endpointGet("/api/bad-request")
                .should(hasStatusCode(400));
    }


    @Test
    @Severity(SeverityLevel.MINOR)
    @Issue("status-codes-controller/getCreated201UsingGET")
    @DisplayName("[POSITIVE] created")
    public void createdTest() {
        statusCodeService.endpointGet("/api/created")
                .should(hasStatusCode(201));
    }

    @Test
    @Severity(SeverityLevel.MINOR)
    @Issue("status-codes-controller/getForbidden403UsingGET")
    @DisplayName("[POSITIVE] forbidden")
    public void forbiddenTest() {
        statusCodeService.endpointGet("/api/forbidden")
                .should(hasStatusCode(403));
    }

    @Test
    @Severity(SeverityLevel.MINOR)
    @Issue("status-codes-controller/getInvalidUrl404UsingGET")
    @DisplayName("[POSITIVE] invalid-url")
    public void invalidUrlTest() {
        statusCodeService.endpointGet("/api/invalid-url")
                .should(hasStatusCode(404));
    }


    @Test
    @Severity(SeverityLevel.MINOR)
    @Issue("status-codes-controller/getMoved301UsingGET")
    @DisplayName("[POSITIVE] moved")
    public void movedTest() {
        statusCodeService.endpointGet("/api/moved")
                .should(hasStatusCode(301));
    }


    @Test
    @Severity(SeverityLevel.MINOR)
    @Issue("status-codes-controller/getNoContent204UsingGET")
    @DisplayName("[POSITIVE] no-content")
    public void noContentTest() {
        statusCodeService.endpointGet("/api/no-content")
                .should(hasStatusCode(204));
    }


    @Test
    @Severity(SeverityLevel.MINOR)
    @Issue("status-codes-controller/getUnauthorized401UsingGET")
    @DisplayName("[POSITIVE] unauthorized")
    public void unauthorizedTest() {
        statusCodeService.endpointGet("/api/unauthorized")
                .should(hasStatusCode(401));
    }
}
