import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.Constants;
import pages.LoginPage;
import pages.ProfilePage;

import java.time.Duration;
import java.util.Objects;

public class LoginTest extends BaseTest {

    private LoginPage loginPage;
    private WebDriverWait wait;
    private ProfilePage profilePage;

    @Before
    public void setUp() {
        loginPage = new LoginPage(driver);
        profilePage = new ProfilePage(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Ожидание полной загрузки страницы
        wait.until(driver -> Objects.equals(((JavascriptExecutor) driver).executeScript("return document.readyState"), "complete"));

        // Переход на страницу входа
        loginPage.goToLoginPage();

        // Ожидание загрузки формы входа
        wait.until(ExpectedConditions.visibilityOfElementLocated(Constants.LOGINFIELD));

        // Проверяем, что форма входа видима
        assertTrue("Форма входа доступна!", loginPage.isLoginFormVisible());
    }

    @Test
    public void testLogin() {
        // Выполняем вход с корректными данными
        loginPage.performLoginWithoutGoToLoginPage(userCreate.getEmail(), userCreate.getPassword());

        // Переходим в профиль и выходим
        loginPage.goToProfile();
        loginPage.clickLogoutButton();
    }

    @Test
    public void testLoginWithIncorrectPassword() {
        // Вводим корректный логин и некорректный пароль
        loginPage.enterLogin(userCreate.getEmail());
        loginPage.enterPassword("pass");
        loginPage.clickSubmitButton();

        // Проверяем, что появляется сообщение об ошибке
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(Constants.ERRORMESSAGEPASS));
        assertTrue("Сообщение об ошибке отображается!",
                errorMessage.getText().contains("Некорректный пароль"));
    }

    @Test
    public void testPasswordRecoveryProcess() {
        // Нажимаем на ссылку "Восстановить пароль"
        loginPage.clickRecoverPasswordLink();
        loginPage.clickButtonLogin();

        // Ожидаем, что поле для ввода логина станет кликабельным
        wait.until(ExpectedConditions.elementToBeClickable(Constants.LOGINFIELD));

        loginPage.performLoginWithoutGoToLoginPage(userCreate.getEmail(), userCreate.getPassword());

        // Проверяем, что процесс восстановления завершен успешно
        assertTrue("Конструктор доступен!", profilePage.isBurgerConstructorVisible());
    }
}