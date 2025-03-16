package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RegisterPage {
    private final WebDriverWait wait;
    private final WebDriver driver;

    public RegisterPage(WebDriver driver) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.driver = driver;
    }

    @Step("Переходим на страницу регистрации")
    public void goToRegisterPage() {
        wait.until(ExpectedConditions.elementToBeClickable(Constants.REGISTER_LINK)).click();
    }

    @Step("Проверяем, что поля для регистрации доступны")
    public boolean isRegistrationFormVisible() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(Constants.NAMEFIELD));
            wait.until(ExpectedConditions.visibilityOfElementLocated(Constants.EMAILFIELD));
            wait.until(ExpectedConditions.visibilityOfElementLocated(Constants.PASSWORD_FIELD));
            return true;
        } catch (Exception e) {
            System.err.println("Ошибка при проверке видимости формы регистрации: " + e.getMessage());
            return false;
        }
    }

    @Step("Вводим имя {name}")
    public void enterName(String name) {
        driver.findElement(Constants.NAMEFIELD).sendKeys(name);
    }

    @Step("Вводим email {email}")
    public void enterEmail(String email) {
        driver.findElement(Constants.EMAILFIELD).sendKeys(email);
    }

    @Step("Вводим пароль {password}")
    public void enterPassword(String password) {
        driver.findElement(Constants.PASSWORD_FIELD).sendKeys(password);
    }

    @Step("Нажимаем на кнопку 'Зарегистрироваться'")
    public void clickRegister() {
        driver.findElement(Constants.REGISTERBUTTON).click();
    }

    @Step("Проверяем, что вкладка конструктора доступна")
    public boolean isBunsVisible() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(Constants.BUNSTAB)).isDisplayed();
    }
}



