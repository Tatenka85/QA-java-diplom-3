package steps;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pages.BasePage;
import pages.Constants;

import static pages.Constants.PAGE_URL;

public class LoginPage extends BasePage {

    private final By emailInputField = By.xpath("//label[text()='Email']/following-sibling::input");
    private final By passwordInputField = By.xpath("//label[text()='Пароль']/following-sibling::input");
    private final By loginButton = By.xpath("//button[text()='Войти']");
    public static final String LOGIN_PAGE_URL = PAGE_URL + "login";

    public LoginPage(WebDriver driver){
        super(driver);
    }

    // Добавляем метод для проверки перехода на конструктор
    public boolean isRedirectedToConstructor() {
        return waitForPageUrl(Constants.PAGE_URL);
    }

    @Step("Открыть страницу входа")
    public void openLoginPage(){
        driver.get(LOGIN_PAGE_URL);
        waitForPageUrl(LOGIN_PAGE_URL);
    }

    @Step("Заполнить поле с Email")
    public void setEmail(String email) {
        inputTextField(emailInputField, email);
    }

    @Step("Заполнить поле с паролем")
    public void setPassword(String password) {
        inputTextField(passwordInputField, password);
    }

    @Step("Нажать на кнопку 'Войти'")
    public void clickLoginButton() {
        clickElementButton(loginButton);
    }
}

