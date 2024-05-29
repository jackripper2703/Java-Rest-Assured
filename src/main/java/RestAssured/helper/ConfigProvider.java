package RestAssured.helper;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public interface ConfigProvider {
    // Чтение конфигурации из файла "application.conf" при инициализации интерфейса
    Config config = readConfig();

    /**
     * Метод для чтения конфигурационного файла "application.conf".
     *
     * @return Объект конфигурации, содержащий настройки из файла "application.conf".
     */
    static Config readConfig() {
        return ConfigFactory.load("application.conf"); // Загрузка конфигурации из файла "application.conf"
    }

    // Получение значения URL из конфигурационного файла
    String URL = config.getString("url");
}
