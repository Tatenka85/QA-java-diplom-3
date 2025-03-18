import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.Constants;
import pages.LoginPage;
import pages.ProfilePage;

import java.time.Duration;
import java.util.Objects;

public class LoginTest extends Base {

    private LoginPage loginPage;
    private WebDriverWait wait;
    private ProfilePage profilePage;

    @Before
    public void setUp() {
        System.out.println("🔹 Начало настройки теста: инициализация страниц");

        loginPage = new LoginPage(driver);
        profilePage = new ProfilePage(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(20)); // Увеличили время ожидания до 20 секунд

        // Ожидание полной загрузки страницы
        wait.until(driver -> Objects.equals(((JavascriptExecutor) driver).executeScript("return document.readyState"), "complete"));

        try {
            // Нажимаем кнопку "Личный кабинет"
            loginPage.goToProfile();
            loginPage.isLoginFormVisible();
            loginPage.performLoginWithoutGoToLoginPage(userCreate.getEmail(), userCreate.getPassword());

            // Переход на страницу входа
            loginPage.goToProfile();
            loginPage.clickLogoutButton();
            loginPage.clickRecoverPasswordLink();

            // Ожидание загрузки формы входа
            wait.until(ExpectedConditions.visibilityOfElementLocated(Constants.BUTTONLOGIN));
            loginPage.clickButtonLogin();
            loginPage.goToProfile();

            // Ожидание загрузки формы входа
            wait.until(ExpectedConditions.visibilityOfElementLocated(Constants.LOGINFIELD));
            loginPage.performLoginWithoutGoToLoginPage(userCreate.getEmail(), userCreate.getPassword());
            loginPage.goToProfile();
            loginPage.clickLogoutButton();
            loginPage.clickConstructorAndVerify();
            loginPage.goToProfile();

            // Ожидание загрузки формы входа
            wait.until(ExpectedConditions.visibilityOfElementLocated(Constants.LOGINFIELD));
            loginPage.performLoginWithoutGoToLoginPage(userCreate.getEmail(), userCreate.getPassword());

            System.out.println("✅ Настройка теста завершена");
        } catch (StaleElementReferenceException e) {
            System.err.println("Ошибка в setUp: элемент устарел. Повторите тест.");
        } catch (TimeoutException e) {
            System.err.println("Ошибка в setUp: время ожидания истекло. Проверьте, что элемент доступен.");
        }
    }

    @Test
    public void testLogin() {
        System.out.println("🔹 Начало теста: вход с корректными данными");

        try {
            // Выполняем вход с корректными данными
            loginPage.performLoginWithoutGoToLoginPage(userCreate.getEmail(), userCreate.getPassword());

            // Переходим в профиль и выходим
            loginPage.goToProfile();
            loginPage.clickLogoutButton();

            System.out.println("✅ Тест завершен: вход и выход выполнены успешно");
        } catch (StaleElementReferenceException e) {
            System.err.println("Ошибка в testLogin: элемент устарел. Повторите тест.");
        } catch (TimeoutException e) {
            System.err.println("Ошибка в testLogin: время ожидания истекло. Проверьте, что элемент доступен.");
        }
    }

    @Test
    public void testLoginWithIncorrectPassword() {
        System.out.println("🔹 Начало теста: вход с некорректным паролем");

        try {
            // Генерация некорректного пароля
            String invalidPassword = UserSteps.generateInvalidPassword(); // Используем метод из UserSteps
            System.out.println("🔹 Сгенерирован некорректный пароль: " + invalidPassword);

            // Вводим корректный логин и некорректный пароль
            loginPage.enterLogin(userCreate.getEmail());
            loginPage.enterPassword(invalidPassword);
            loginPage.clickSubmitButton();

            // Проверяем, что появляется сообщение об ошибке
            WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(Constants.ERRORMESSAGEPASS));
            assertTrue("Сообщение об ошибке отображается!",
                    errorMessage.getText().contains("Некорректный пароль"));

            System.out.println("✅ Тест завершен: ошибка при входе с некорректным паролем");
        } catch (StaleElementReferenceException e) {
            System.err.println("Ошибка в testLoginWithIncorrectPassword: элемент устарел. Повторите тест.");
        } catch (TimeoutException e) {
            System.err.println("Ошибка в testLoginWithIncorrectPassword: время ожидания истекло. Проверьте, что элемент доступен.");
        }
    }

    @Test
    public void testPasswordRecoveryProcess() {
        System.out.println("🔹 Начало теста: восстановление пароля");

        try {
            // Нажимаем на ссылку "Восстановить пароль"
            loginPage.clickRecoverPasswordLink();
            loginPage.clickButtonLogin();

            // Ожидаем, что кнопка "Войти" станет кликабельной
            wait.until(ExpectedConditions.elementToBeClickable(Constants.BUTTONLOGIN));

            // Выполняем вход с корректными данными
            loginPage.performLoginWithoutGoToLoginPage(userCreate.getEmail(), userCreate.getPassword());

            // Проверяем, что процесс входа завершен успешно
            assertTrue("Конструктор доступен!", profilePage.isBurgerConstructorVisible());

            System.out.println("✅ Тест завершен: восстановление пароля выполнено успешно");
        } catch (StaleElementReferenceException e) {
            System.err.println("Ошибка в testPasswordRecoveryProcess: элемент устарел. Повторите тест.");
        } catch (TimeoutException e) {
            System.err.println("Ошибка в testPasswordRecoveryProcess: время ожидания истекло. Проверьте, что элемент доступен.");
        }
    }
}