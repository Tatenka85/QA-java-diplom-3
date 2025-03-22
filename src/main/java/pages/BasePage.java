package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.Objects;

public class BasePage {

    protected WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    public void waitForPageLoad() {
        new WebDriverWait(driver, Duration.ofSeconds(10)) // исправленный вариант
                .until(driver -> Objects.requireNonNull(((JavascriptExecutor) driver)
                        .executeScript("return document.readyState")).toString().equals("complete"));
    }


    private final By logo = By.className("AppHeader_header__logo__2D0X2");


    protected void inputTextField(By fieldLocator, String inputText) {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(fieldLocator));

        driver.findElement(fieldLocator).click();
        driver.findElement(fieldLocator).sendKeys(inputText);
    }

    protected void clickElementButton(By elementLocator){
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(elementLocator));
        driver.findElement(elementLocator).click();
    }

    public void clickOnLogo(){
        clickElementButton(logo);
    }

    public boolean waitForPageUrl(String expectedUrl) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.urlToBe(expectedUrl));
            return true; // Успешный переход
        } catch (Exception e) {
            return false; // Переход не выполнен
        }
    }

    protected void waitForElementVisibility(By locator) {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    // Добавленный метод для получения текущего URL
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

}
