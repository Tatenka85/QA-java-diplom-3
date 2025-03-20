import common.BaseUITest;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import steps.ConstructorSteps;
import steps.ForgotPasswordSteps;
import steps.LoginSteps;
import steps.RegisterSteps;

import java.time.Duration;

import static org.junit.Assert.assertEquals;

public class NavigationTest extends BaseUITest {

    @Test
    @DisplayName("Клик по логотипу на странице регистрации ведет на конструктор")
    @Description("Проверка, что клик по логотипу на странице регистрации перенаправляет на страницу конструктора.")
    public void verifyLogoRedirectsToConstructorFromRegisterPage() {
        RegisterSteps registerPage = new RegisterSteps(driver);
        registerPage.openRegistrationPage();
        registerPage.clickOnLogo();

        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.urlToBe(ConstructorSteps.CONSTRUCTOR_PAGE_URL));
        String currentUrl = driver.getCurrentUrl();
        assertEquals(ConstructorSteps.CONSTRUCTOR_PAGE_URL, currentUrl);
    }

    @Test
    @DisplayName("Клик по логотипу на странице входа ведет на конструктор")
    @Description("Проверка, что клик по логотипу на странице входа перенаправляет на страницу конструктора.")
    public void verifyLogoRedirectsToConstructorFromLoginPage() {
        LoginSteps loginSteps = new LoginSteps(driver);
        loginSteps.openLoginPage();
        loginSteps.clickOnLogo();

        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.urlToBe(ConstructorSteps.CONSTRUCTOR_PAGE_URL));
        String currentUrl = driver.getCurrentUrl();
        assertEquals(ConstructorSteps.CONSTRUCTOR_PAGE_URL, currentUrl);
    }

    @Test
    @DisplayName("Клик по логотипу на странице восстановления пароля ведет на конструктор")
    @Description("Проверка, что клик по логотипу на странице восстановления пароля перенаправляет на страницу конструктора.")
    public void verifyLogoRedirectsToConstructorFromPasswordRecoveryPage() {
        ForgotPasswordSteps forgotPasswordSteps = new ForgotPasswordSteps(driver);
        forgotPasswordSteps.openForgotPasswordPage();
        forgotPasswordSteps.clickOnLogo();

        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.urlToBe(ConstructorSteps.CONSTRUCTOR_PAGE_URL));
        String currentUrl = driver.getCurrentUrl();
        assertEquals(ConstructorSteps.CONSTRUCTOR_PAGE_URL, currentUrl);
    }
}
