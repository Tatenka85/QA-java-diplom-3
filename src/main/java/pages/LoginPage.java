package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    private final WebDriverWait wait;
    private final MainPage mainPage;

    public LoginPage(WebDriver driver) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.mainPage = new MainPage(driver);
    }

    @Step("Переходим на страницу входа")
    public void goToLoginPage() {
        mainPage.clickLoginButton();
    }

    @Step("Проверяем, что поля для входа доступны")
    public boolean isLoginFormVisible() {
        try {
            WebElement loginField = wait.until(ExpectedConditions.visibilityOfElementLocated(Constants.LOGINFIELD));
            WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(Constants.PASSWORDFIELD));
            WebElement submitButton = wait.until(ExpectedConditions.visibilityOfElementLocated(Constants.SUBMITBUTTON));
            System.out.println("Поле для ввода логина видимо: " + loginField.isDisplayed());
            System.out.println("Поле для ввода пароля видимо: " + passwordField.isDisplayed());
            System.out.println("Кнопка 'Войти' видима: " + submitButton.isDisplayed());
            return loginField.isDisplayed() && passwordField.isDisplayed() && submitButton.isDisplayed();
        } catch (Exception e) {
            System.err.println("Ошибка при проверке видимости формы входа: " + e.getMessage());
            return false;
        }
    }

    @Step("Вводим логин")
    public void enterLogin(String login) {
        WebElement loginInput = wait.until(ExpectedConditions.elementToBeClickable(Constants.LOGINFIELD));
        loginInput.click();
        loginInput.clear();
        loginInput.sendKeys(login);
    }

    @Step("Вводим пароль")
    public void enterPassword(String password) {
        WebElement passwordInput = wait.until(ExpectedConditions.elementToBeClickable(Constants.PASSWORDFIELD));
        passwordInput.click();
        passwordInput.clear();
        passwordInput.sendKeys(password);
    }

    @Step("Нажимаем на кнопку 'Войти'")
    public void clickSubmitButton() {
        WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(Constants.SUBMITBUTTON));
        submitButton.click();
    }

    @Step("Переход в личный кабинет")
    public void goToProfile() {
        WebElement profileButton = wait.until(ExpectedConditions.elementToBeClickable(Constants.PROFILEBUTTON));
        profileButton.click();
    }

    @Step("Нажимаем кнопку 'Выход'")
    public void clickLogoutButton() {
        WebElement logoutButton = wait.until(ExpectedConditions.elementToBeClickable(Constants.LOGOUTBUTTON));
        logoutButton.click();
    }

    @Step("Нажимаем кнопку восстановления пароля")
    public void clickRecoverPasswordLink() {
        WebElement recoverPasswordLink = wait.until(ExpectedConditions.elementToBeClickable(Constants.RECOVER_PASSWORD_LINK));
        recoverPasswordLink.click();
    }

    @Step("Нажимаем кнопку Войти")
    public void clickButtonLogin() {
        WebElement recoverPasswordLogin = wait.until(ExpectedConditions.elementToBeClickable(Constants.BUTTONLOGIN));
        recoverPasswordLogin.click();
    }

    @Step("Выполняем полный процесс логина (без перехода на страницу входа)")
    public void performLoginWithoutGoToLoginPage(String login, String password) {
        isLoginFormVisible(); // Проверяем, что форма логина видима
        enterLogin(login); // Вводим логин
        enterPassword(password); // Вводим пароль
        clickSubmitButton(); // Нажимаем на кнопку "Войти"
    }
}

