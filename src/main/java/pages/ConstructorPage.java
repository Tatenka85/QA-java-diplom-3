package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ConstructorPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    public ConstructorPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Step("Кликаем по вкладке 'Булки'")
    public void clickBunsTab() {
        clickElementSafely(Constants.BUNSTAB);
    }

    @Step("Кликаем по вкладке 'Соусы'")
    public void clickSaucesTab() {
        clickElementSafely(Constants.SAUCESTAB);
    }

    @Step("Кликаем по вкладке 'Начинки'")
    public void clickFillingsTab() {
        clickElementSafely(Constants.FILLINGSTAB);
    }

    @Step("Получаем текст активной вкладки")
    public String getActiveTabText() {
        WebElement activeTab = wait.until(ExpectedConditions.visibilityOfElementLocated(Constants.ACTIVETAB));
        return activeTab.getText();
    }

    @Step("Получаем секцию 'Булки'")
    public WebElement getBunsSection() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(Constants.BUNS_SECTION));
    }

    @Step("Получаем секцию 'Соусы'")
    public WebElement getSaucesSection() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(Constants.SAUCES_SECTION));
    }

    @Step("Получаем секцию 'Начинки'")
    public WebElement getFillingsSection() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(Constants.FILLINGS_SECTION));
    }

    // Универсальный метод для безопасного клика
    private void clickElementSafely(By locator) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator)); // Используем locator напрямую
        scrollToElementIfNotVisible(element);

        // Попытка клика через стандартный метод
        try {
            element.click();
        } catch (Exception e) {
            // Если стандартный клик не сработал, используем JavaScript для клика
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        }
    }

    // Метод для прокрутки к элементу, если он не в зоне видимости
    private void scrollToElementIfNotVisible(WebElement element) {
        if (!isElementInViewport(element)) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
        }
    }

    // Проверка, находится ли элемент в пределах viewport
    public boolean isElementInViewport(WebElement element) {
        if (element == null) return false;
        JavascriptExecutor js = (JavascriptExecutor) driver;
        return Boolean.TRUE.equals(js.executeScript(
                "var rect = arguments[0].getBoundingClientRect();" +
                        "return (rect.top >= 0 && rect.left >= 0 && rect.bottom <= window.innerHeight && rect.right <= window.innerWidth);",
                element
        ));
    }
}