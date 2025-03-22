package steps;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.NoSuchElementException;
import pages.BasePage;
import pages.Constants;

public class RegisterPage extends BasePage {

    private final By nameInputField = By.xpath("//label[text()='Имя']/following-sibling::input");
    private final By emailInputField = By.xpath("//label[text()='Email']/following-sibling::input");
    private final By passwordInputField = By.xpath("//label[text()='Пароль']/following-sibling::input");
    private final By registerButton = By.xpath("//button[text()='Зарегистрироваться']");
    private final By registrationErrorMessage = By.xpath("//p[starts-with(@class, 'input__error')]");
    private final By loginPageButton = By.xpath("//a[@href='/login']");

    public RegisterPage(WebDriver driver) {
        super(driver); // Вызов конструктора родительского класса
    }

    @Step("Открыть страницу регистрации")
    public void openRegistrationPage() {
        driver.get(Constants.PAGE_URL + "register");
    }

    public boolean isRedirectedToConstructor() {
        return waitForPageUrl(Constants.PAGE_URL);
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
        waitForPageLoad(); // Добавляем ожидание загрузки новой страницы
    }

    @Step("Перейти на страницу входа")
    public void switchToLoginPage() {
        clickElementButton(loginPageButton);
        waitForPageUrl(LoginPage.LOGIN_PAGE_URL);
    }

    @Step("Получить текст ошибки регистрации")
    public String getRegistrationErrorMessage() {
        try {
            waitForElementVisibility(registrationErrorMessage);
            return driver.findElement(registrationErrorMessage).getText();
        } catch (NoSuchElementException e) {
            return ""; // Если ошибки нет, возвращаем пустую строку
        }
    }
}