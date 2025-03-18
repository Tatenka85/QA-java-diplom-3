import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.Constants;
import pages.LoginPage;
import pages.RegisterPage;
import pages.MainPage;

import java.time.Duration;
import java.util.Objects;

public class RegisterTest {

    private WebDriver driver;
    private RegisterPage registerPage;
    private String email;
    private WebDriverWait wait;

    @Before
    public void setUp() {
        System.out.println("🔹 Начало настройки теста: инициализация WebDriver и страниц");

        // Создаем WebDriver с помощью WebDriverFactory
        driver = WebDriverFactory.createForEnvironment();
        // Создаем страницы, используя существующий WebDriver
        registerPage = new RegisterPage(driver);
        new LoginPage(driver);
        MainPage mainPage = new MainPage(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Открываем главную страницу
        mainPage.openPage();
        // Ожидание полной загрузки страницы
        wait.until(driver -> Objects.equals(((JavascriptExecutor) driver).executeScript("return document.readyState"), "complete"));

        mainPage.clickLoginButton();

        // Ожидание загрузки формы входа
        wait.until(ExpectedConditions.visibilityOfElementLocated(Constants.REGISTER_LINK));

        registerPage.goToRegisterPage(); // Переход на страницу регистрации

        // Ожидание загрузки формы регистрации
        wait.until(ExpectedConditions.visibilityOfElementLocated(Constants.NAMEFIELD));
        // Проверяем, что форма регистрации видима
        assertTrue("Форма регистрации доступна!", registerPage.isRegistrationFormVisible());

        // Генерация email
        email = "test" + System.currentTimeMillis() + "@example.com";

        System.out.println("✅ Настройка теста завершена");
    }

    @Test
    public void testSuccessfulRegistration() {
        System.out.println("🔹 Начало теста: успешная регистрация");

        String name = "Test User";
        String password = UserSteps.generateValidPassword(); // Генерация валидного пароля
        System.out.println("🔹 Сгенерирован валидный пароль: " + password);

        registerPage.enterName(name);
        registerPage.enterEmail(email);
        registerPage.enterPassword(password);
        registerPage.clickRegister();

        // Ожидание загрузки формы входа
        wait.until(ExpectedConditions.visibilityOfElementLocated(Constants.LOGINFIELD));

        // Создаем объект LoginPage
        LoginPage loginPage = new LoginPage(driver);

        // Убедимся, что форма входа полностью загружена
        wait.until(ExpectedConditions.elementToBeClickable(Constants.LOGINFIELD));
        wait.until(ExpectedConditions.elementToBeClickable(Constants.PASSWORDFIELD));
        wait.until(ExpectedConditions.elementToBeClickable(Constants.SUBMITBUTTON));

        // Выполняем вход после регистрации
        loginPage.enterLogin(email);
        loginPage.enterPassword(password);
        loginPage.clickSubmitButton();

        // Проверяем, что вход выполнен успешно
        assertTrue("Конструктор доступен!", registerPage.isBunsVisible());

        System.out.println("✅ Тест завершен: регистрация и вход выполнены успешно");
    }

    @Test
    public void testRegistrationWithInvalidPassword() {
        System.out.println("🔹 Начало теста: регистрация с некорректным паролем");

        String name = "Test User";
        String invalidPassword = UserSteps.generateInvalidPassword(); // Генерация невалидного пароля
        System.out.println("🔹 Сгенерирован невалидный пароль: " + invalidPassword);

        registerPage.enterName(name);
        registerPage.enterEmail(email);
        registerPage.enterPassword(invalidPassword);
        registerPage.clickRegister();

        // Проверяем, что появляется сообщение об ошибке
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(Constants.ERRORMESSAGEPASS));

        assertTrue("Сообщение об ошибке отображается!", errorMessage.getText().contains("Некорректный пароль"));

        System.out.println("✅ Тест завершен: сообщение об ошибке отображается корректно");
    }

    @After
    public void tearDown() {
        System.out.println("🔹 Начало очистки: закрытие браузера");

        if (driver != null) {
            driver.quit(); // Закрываем браузер
            System.out.println("🔹 Браузер закрыт");
        }

        System.out.println("✅ Очистка завершена");
    }
}