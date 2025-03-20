package steps;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pages.Base;

import static pages.Constants.PAGE_URL;

public class ForgotPasswordSteps extends Base {

    private final By backToLoginButton = By.xpath("//a[@href='/login']");
    public static final String PASSWORD_RECOVERY_PAGE_URL = PAGE_URL + "forgot-password";

    public ForgotPasswordSteps(WebDriver driver){
        this.driver = driver;
    }

    @Step("Открыть страницу восстановления пароля")
    public void openForgotPasswordPage(){
        driver.get(PASSWORD_RECOVERY_PAGE_URL);
    }

    @Step("Перейти на страницу входа")
    public void switchToLoginPage() {
        clickElementButton(backToLoginButton);
    }
}
