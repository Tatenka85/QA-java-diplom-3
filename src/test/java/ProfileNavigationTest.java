import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import pages.ProfilePage;
import pages.LoginPage;

public class ProfileNavigationTest extends Base {

    private ProfilePage profilePage;

    @Before
    public void setUp() {
        // Создаем страницы, используя существующий WebDriver
        profilePage = new ProfilePage(driver);
        LoginPage loginPage = new LoginPage(driver);

        // Переходим на страницу входа
        profilePage.goToProfile();

        // Выполняем вход с корректными данными (используем userCreate)
        loginPage.enterLogin(userCreate.getEmail());
        loginPage.enterPassword(userCreate.getPassword());
        loginPage.clickSubmitButton();

        // Переходим в личный кабинет
        profilePage.goToProfile();
    }

    @Test
    public void testConstructorButton() {
        // Кликаем на кнопку "Конструктор"
        profilePage.clickConstructorAndVerify();

        // Проверяем, что после клика открывается страница конструктора
        assertTrue("Конструктор доступен!", profilePage.isBurgerConstructorVisible());

        // Возвращаемся в личный кабинет
        profilePage.goToProfile();
    }

    @Test
    public void testLogoNavigation() {
        // Кликаем на логотип
        profilePage.clickLogoAndVerify();

        // Проверяем, что после клика на логотип мы вернулись на главную страницу
        assertTrue("Переход на главную страницу выполнен!", profilePage.isBurgerConstructorVisible());

        // Возвращаемся в личный кабинет
        profilePage.goToProfile();
    }

    @Test
    public void testLogout() {
        // Кликаем на кнопку "Выйти"
        profilePage.clickLogoutButton();
    }
}