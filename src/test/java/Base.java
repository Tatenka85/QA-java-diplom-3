import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import pages.MainPage;

public class Base {

    protected WebDriver driver; // WebDriver для использования в тестах
    protected static UserCreate userCreate;
    protected MainPage mainPage;// Общий пользователь для всех тестов

    // Настройка WebDriver перед каждым тестом
    @Before
    public void before() {
        driver = WebDriverFactory.createForEnvironment(); // Создаем WebDriver в зависимости от браузера
        mainPage = new MainPage(driver); // Инициализируем MainPage
        mainPage.openPage(); // Открываем главную страницу перед каждым тестом

        // Если пользователь ещё не создан, создаём его
        if (userCreate == null) {
            userCreate = new UserCreate();
            userCreate.createUser();
        }
    }

    // Закрытие WebDriver после каждого теста
    @After
    public void after() {
        if (driver != null) {
            driver.quit(); // Закрываем браузер
        }

        if (userCreate != null) {
            userCreate.cleanup(userCreate.getAccessToken()); // Удаляем пользователя после тестов
            userCreate = null; // Сбрасываем объект, чтобы не использовать его в следующих тестах
        }
    }
}