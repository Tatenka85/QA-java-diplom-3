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

        email = "test" + System.currentTimeMillis() + "@example.com";
    }

    @Test
    public void testSuccessfulRegistration() {
        String name = "Test User";
        String password = "Password123"; //валидный пароль
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
    }

    @Test
    public void testRegistrationWithInvalidPassword() {
        registerPage.enterName("Test User");
        registerPage.enterEmail(email);
        //невалидный пароль
        String invalidPassword = "Pass";
        registerPage.enterPassword(invalidPassword);  // Некорректный пароль
        registerPage.clickRegister();

        // Проверяем, что появляется сообщение об ошибке
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(Constants.ERRORMESSAGEPASS));

        assertTrue("Сообщение об ошибке отображается!", errorMessage.getText().contains("Некорректный пароль"));
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit(); // Закрываем браузер
        }
    }
}
