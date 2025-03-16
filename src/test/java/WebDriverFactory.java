import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.Constants;

public class WebDriverFactory {

    // Метод для создания WebDriver в зависимости от имени браузера
    public static WebDriver createForName(String browserName) {
        switch (browserName.toUpperCase()) {
            case "CHROME":
                return new ChromeDriver();
            case "YANDEX":
                // Указываем путь к YandexDriver
                System.setProperty("webdriver.chrome.driver", "C:/cygwin64/home/vipno/Yandexdriver/yandexdriver.exe/");
                // Настройки для Yandex Browser
                ChromeOptions options = new ChromeOptions();
                options.setBinary("C:/Users/vipno/AppData/Local/Yandex/YandexBrowser/Application/browser.exe"); // Укажите путь к исполняемому файлу Yandex Browser
                return new ChromeDriver(options);
            default:
                throw new RuntimeException("Нераспознанный браузер: " + browserName);
        }
    }

    // Метод для получения WebDriver с учетом переменной окружения или системного свойства
    public static WebDriver createForEnvironment() {
        // Получаем имя браузера из переменной окружения или из системного свойства
        String browserName = System.getenv(Constants.BROWSER_NAME_ENV_VARIABLE); // Переменная окружения
        if (browserName == null) {
            browserName = System.getProperty(Constants.BROWSER_NAME_SYS_PROPERTY); // Системное свойство
        }

        // Если не указано ни переменное окружение, ни системное свойство, используем браузер по умолчанию
        return createForName(browserName != null ? browserName : Constants.DEFAULT_BROWSER_NAME);
    }
}