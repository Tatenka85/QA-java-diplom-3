import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.ConstructorPage;
import pages.MainPage;

import java.time.Duration;
import java.util.Objects;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class ConstructorNavigationTest {

    private WebDriver driver;
    private ConstructorPage constructorPage;
    private final String tabName;

    @Parameterized.Parameters(name = "Тест вкладки: {0}")
    public static Object[] data() {
        return new Object[]{"Булки", "Соусы", "Начинки"};
    }

    public ConstructorNavigationTest(String tabName) {
        this.tabName = tabName;
    }

    @Before
    public void setUp() {
        // Создаем WebDriver с помощью WebDriverFactory
        driver = WebDriverFactory.createForEnvironment();

        // Инициализируем страницы
        MainPage mainPage = new MainPage(driver);
        constructorPage = new ConstructorPage(driver);

        // Открываем главную страницу и переходим на страницу конструктора
        mainPage.openPage();
        mainPage.clickConstructorButton();  // Переход на страницу конструктора

        // Ожидание полной загрузки страницы
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(
                driver -> Objects.equals(((JavascriptExecutor) driver).executeScript("return document.readyState"), "complete"));
    }

    @Test
    public void testTabNavigationAndScroll() {
        // Выполняем клик по вкладке в зависимости от параметра tabName
        switch (tabName) {
            case "Соусы":
                constructorPage.clickSaucesTab();
                break;
            case "Булки":
                constructorPage.clickBunsTab();
                break;
            case "Начинки":
                constructorPage.clickFillingsTab();
                break;
            default:
                throw new IllegalArgumentException("Неизвестная вкладка: " + tabName);
        }

        // Проверяем, что активная вкладка соответствует ожидаемой
        assertEquals("Активная вкладка не соответствует ожидаемой!", tabName, constructorPage.getActiveTabText());

        // Получаем секцию ингредиентов для текущей вкладки
        WebElement ingredientsSection = getIngredientsSection(tabName);

        // Добавляем явное ожидание перед проверкой видимости
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(
                driver -> constructorPage.isElementInViewport(ingredientsSection));

        // Проверяем, что ингредиенты вкладки находятся в зоне видимости
        assertTrue("Ингредиенты вкладки '" + tabName + "' не в зоне видимости!", constructorPage.isElementInViewport(ingredientsSection));
    }

    // Метод для получения секции ингредиентов в зависимости от вкладки
    private WebElement getIngredientsSection(String tabName) {
        switch (tabName) {
            case "Соусы":
                return constructorPage.getSaucesSection();
            case "Булки":
                return constructorPage.getBunsSection();
            case "Начинки":
                return constructorPage.getFillingsSection();
            default:
                throw new IllegalArgumentException("Неизвестная вкладка: " + tabName);
        }
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit(); // Закрываем браузер
        }
    }
}