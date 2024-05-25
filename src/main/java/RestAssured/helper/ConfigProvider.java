package RestAssured.helper;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public interface ConfigProvider {
    Config config = readConfig();

    static Config readConfig() {
        return ConfigFactory.load("application.conf");
    }

    String URLREQRES = config.getString("urlReqres");
    String URLVPLUSE = config.getString("urlVpluse");

    String PHONE_NUMBER = config.getString("phoneNumber");
    String PASSWORD = config.getString("password");
}

