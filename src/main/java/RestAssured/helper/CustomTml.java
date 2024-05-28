package RestAssured.helper;

import io.qameta.allure.restassured.AllureRestAssured;

public class CustomTml {
    private static final AllureRestAssured FILTER = new AllureRestAssured();

    private CustomTml() {
    }

    public static CustomTml customLogFilter() {
        return InitLogFilter.logFilter;
    }

    public AllureRestAssured withCustomTemplates() {
        FILTER.setRequestTemplate("http-request.ftl");
        FILTER.setResponseTemplate("http-response.ftl");
        return FILTER;
    }

    private static class InitLogFilter {
        private static final CustomTml logFilter = new CustomTml();
    }
}
