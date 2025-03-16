package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.Assert.assertTrue;

public class ProfilePage {
    private final WebDriverWait wait;
    private final MainPage mainPage;

    public ProfilePage(WebDriver driver) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.mainPage = new MainPage(driver);
    }

    @Step("Переход в личный кабинет")
    public void goToProfile() {
        mainPage.clickProfileButton();
    }

    @Step("Клик на логотип и проверка перехода на главную")
    public void clickLogoAndVerify() {
        mainPage.clickLogoButton();
        assertTrue("Конструктор доступен!", isBurgerConstructorVisible());
    }

    @Step("Клик на кнопку 'Конструктор' и проверка перехода на главную")
    public void clickConstructorAndVerify() {
        mainPage.clickConstructorButton();
        assertTrue("Конструктор доступен!", isBurgerConstructorVisible());
    }

    @Step("Клик на кнопку 'Выйти'")
    public void clickLogoutButton() {
        WebElement logoutButton = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//button[@type='button' and contains(@class, 'Account_button__14Yp3') and contains(text(), 'Выход')]")
        ));
        assertTrue("Кнопка 'Выход' доступна", logoutButton.isDisplayed());
        logoutButton.click();

        WebElement loginButton = wait.until(ExpectedConditions.visibilityOfElementLocated(Constants.SUBMITBUTTON));
        assertTrue("Кнопка 'Войти в аккаунт' доступна!", loginButton.isDisplayed());
    }

    @Step("Проверяем, что вкладка конструктора доступна")
    public boolean isBurgerConstructorVisible() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(Constants.BUNSTAB)).isDisplayed();
    }
}