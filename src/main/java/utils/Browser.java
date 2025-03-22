package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Browser {

    protected WebDriver driver;

    // Константы для имен переменных окружения и системных свойств
    private static final String BROWSER_ENV_VARIABLE = "BROWSER";
    private static final String BROWSER_SYS_PROPERTY = "browser";
    private static final String DEFAULT_BROWSER = "CHROME"; // Браузер по умолчанию

    public WebDriver initDriver() {
        // Получаем имя браузера из переменной окружения или системного свойства
        String browserName = System.getenv(BROWSER_ENV_VARIABLE); // Переменная окружения
        if (browserName == null) {
            browserName = System.getProperty(BROWSER_SYS_PROPERTY, DEFAULT_BROWSER); // Системное свойство или значение по умолчанию
        }

        // Преобразуем строку в значение enum BrowserType
        BrowserType browserType;
        try {
            browserType = BrowserType.valueOf(browserName.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Нераспознанный браузер: " + browserName);
        }

        // Инициализация драйвера в зависимости от выбранного браузера
        switch (browserType) {
            case CHROME:
                ChromeOptions options = new ChromeOptions();
                driver = new ChromeDriver(options);
                break;

            case YANDEX:
                System.setProperty("webdriver.chrome.driver", "C:/WebDriver/bin/yandexdriver.exe");
                ChromeOptions yandexOptions = new ChromeOptions();
                yandexOptions.setBinary("C:/Users/vipno/AppData/Local/Yandex/YandexBrowser/Application/browser.exe");
                driver = new ChromeDriver(yandexOptions);
                break;

            default:
                throw new RuntimeException("Нераспознанный браузер: " + browserType);
        }

        // Максимизируем окно браузера
        driver.manage().window().maximize();
        return driver;
    }
}