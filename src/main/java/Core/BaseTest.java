/*
 Первый создаваемый файл
 В данном файле происходит создания и иницилизация webDriver,
 А так же передаеются параметры, такие как размер экрана браузера и время ожидания
*/

package Core;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

abstract public class BaseTest {
    protected WebDriver driver;

    @Before
    public void setUp() {
//        Скачиваем chromeDriver и иницилизируем
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
//        Браузер на полный экран
        driver.manage().window().maximize();
//        Ожидание загрузки страницы 10 секунд
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
//        Неявное ожидание элементов 15 секунд
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        BasePage.setDriver(driver);
    }

    @After
    public void tearDown(){
//        Закрытия процесса webDriver
        driver.close();
//        Закрытие браузера
        driver.quit();
    }
}