package steps;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.Base;

import java.time.Duration;

import static pages.Constants.PAGE_URL;

public class RegisterSteps extends Base {

    private final By nameInputField = By.xpath("//label[text()='Имя']/following-sibling::input");
    private final By emailInputField = By.xpath("//label[text()='Email']/following-sibling::input");
    private final By passwordInputField = By.xpath("//label[text()='Пароль']/following-sibling::input");
    private final By registerButton = By.xpath("//button[text()='Зарегистрироваться']");
    private final By registrationErrorMessage = By.xpath("//p[starts-with(@class, 'input__error')]");
    private final By loginPageButton = By.xpath("//a[@href='/login']");

    public RegisterSteps(WebDriver driver){
        this.driver = driver;
    }

    @Step("Открыть страницу регистрации")
    public void openRegistrationPage(){
        String registrationPageUrl = PAGE_URL + "register";
        driver.get(registrationPageUrl);
    }

    @Step("Заполнить поле 'Имя'")
    public void setName(String name) {
        inputTextField(nameInputField, name);
    }

    @Step("Заполнить поле 'Email'")
    public void setEmail(String email) {
        inputTextField(emailInputField, email);
    }

    @Step("Заполнить поле 'Пароль'")
    public void setPassword(String password) {
        inputTextField(passwordInputField, password);
    }

    @Step("Нажать на кнопку 'Зарегистрироваться'")
    public void clickRegisterButton() {
        clickElementButton(registerButton);
    }

    @Step("Перейти на страницу входа")
    public void switchToLoginPage() {
        clickElementButton(loginPageButton);
    }

    @Step("Получить текст ошибки регистрации")
    public String getRegistrationErrorMessage() {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(registrationErrorMessage));
        return driver.findElement(registrationErrorMessage).getText();
    }
}
