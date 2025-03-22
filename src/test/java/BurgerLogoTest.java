import common.BaseUITest;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import steps.ForgotPasswordPage;
import steps.LoginPage;
import steps.RegisterPage;
import static org.junit.Assert.assertTrue;

public class BurgerLogoTest extends BaseUITest {

    @Test
    @DisplayName("Клик по логотипу на странице регистрации ведет на конструктор")
    @Description("Проверка, что клик по логотипу на странице регистрации перенаправляет на страницу конструктора.")
    public void verifyLogoRedirectsToConstructorFromRegisterPage() {
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.openRegistrationPage();
        registerPage.clickOnLogo();
        assertTrue(registerPage.isRedirectedToConstructor());
    }

    @Test
    @DisplayName("Клик по логотипу на странице входа ведет на конструктор")
    @Description("Проверка, что клик по логотипу на странице входа перенаправляет на страницу конструктора.")
    public void verifyLogoRedirectsToConstructorFromLoginPage() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.openLoginPage();
        loginPage.clickOnLogo();
        assertTrue(loginPage.isRedirectedToConstructor());
    }

    @Test
    @DisplayName("Клик по логотипу на странице восстановления пароля ведет на конструктор")
    @Description("Проверка, что клик по логотипу на странице восстановления пароля перенаправляет на страницу конструктора.")
    public void verifyLogoRedirectsToConstructorFromPasswordRecoveryPage() {
        ForgotPasswordPage forgotPasswordPage = new ForgotPasswordPage(driver);
        forgotPasswordPage.openForgotPasswordPage();
        forgotPasswordPage.clickOnLogo();
        assertTrue(forgotPasswordPage.isRedirectedToConstructor());
    }
}
