package tests;

import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import static RestAssured.assertions.Conditions.*;

@Tag("StatusCode")
public class StatusCodeTest extends BaseApiTest {

    @Test
    @Severity(SeverityLevel.MINOR)
    @Issue("status-codes-controller/getBadRequest400UsingGET")
    @DisplayName("[POSITIVE] bad-request")
    public void badRequestTest() {
        statusCodeService.endpointGet("bad-request")
                .should(hasStatusCode(400));
    }

    @Test
    @Severity(SeverityLevel.MINOR)
    @Issue("status-codes-controller/getCreated201UsingGET")
    @DisplayName("[POSITIVE] created")
    public void createdTest() {
        statusCodeService.endpointGet("created")
                .should(hasStatusCode(201));
    }

    @Test
    @Severity(SeverityLevel.MINOR)
    @Issue("status-codes-controller/getForbidden403UsingGET")
    @DisplayName("[POSITIVE] forbidden")
    public void forbiddenTest() {
        statusCodeService.endpointGet("forbidden")
                .should(hasStatusCode(403));
    }

    @Test
    @Severity(SeverityLevel.MINOR)
    @Issue("status-codes-controller/getInvalidUrl404UsingGET")
    @DisplayName("[POSITIVE] invalid-url")
    public void invalidUrlTest() {
        statusCodeService.endpointGet("invalid-url")
                .should(hasStatusCode(404));
    }

    @Test
    @Severity(SeverityLevel.MINOR)
    @Issue("status-codes-controller/getMoved301UsingGET")
    @DisplayName("[POSITIVE] getMoved301")
    public void movedTest() {
        statusCodeService.endpointGet("/api/moved")
                .should(hasStatusCode(301));
    }

    @Test
    @Severity(SeverityLevel.MINOR)
    @Issue("status-codes-controller/getNoContent204UsingGET")
    @DisplayName("[POSITIVE] no-content")
    public void noContentTest() {
        statusCodeService.endpointGet("no-content")
                .should(hasStatusCode(204));
    }

    @Test
    @Severity(SeverityLevel.MINOR)
    @Issue("status-codes-controller/getUnauthorized401UsingGET")
    @DisplayName("[POSITIVE] unauthorized")
    public void unauthorizedTest() {
        statusCodeService.endpointGet("unauthorized")
                .should(hasStatusCode(401));
    }
}
