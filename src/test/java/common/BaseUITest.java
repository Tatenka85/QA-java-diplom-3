package common;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import utils.WebDriverFactory;

public class BaseUITest {

    protected WebDriver driver;

    @Before
    public void startUp() {
        driver = WebDriverFactory.createForEnvironment(); // Теперь driver обновляется правильно
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
