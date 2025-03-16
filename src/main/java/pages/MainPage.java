package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Step("Открываем главную страницу")
    public void openPage() {
        driver.get(Constants.PAGE_URL);
    }

    @Step("Кликаем по кнопке 'Войти в аккаунт'")
    public void clickLoginButton() {
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(Constants.LOGINBUTTON));
        loginButton.click();
    }

    @Step("Кликаем по кнопке 'Личный кабинет'")
    public void clickProfileButton() {
        WebElement profileButton = wait.until(ExpectedConditions.elementToBeClickable(Constants.PROFILEBUTTON));
        profileButton.click();
    }

    @Step("Кликаем по кнопке 'Конструктор'")
    public void clickConstructorButton() {
        WebElement constructorButton = wait.until(ExpectedConditions.elementToBeClickable(Constants.CONSTRUCTORBUTTON));
        constructorButton.click();
    }

    @Step("Кликаем по логотипу")
    public void clickLogoButton() {
        WebElement logoButton = wait.until(ExpectedConditions.elementToBeClickable(Constants.LOGOBUTTON));
        logoButton.click();
    }
}