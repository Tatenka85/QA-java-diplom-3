package common;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import utils.Browser;

public class BaseUITest {

    protected WebDriver driver;

    @Before
    public void startUp() {
        Browser browser = new Browser();
        driver = browser.initDriver();
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
