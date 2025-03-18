package pages;

import org.openqa.selenium.By;

public class Constants {
    public static final String BROWSER_NAME_ENV_VARIABLE = "BROWSER_NAME"; // Переменная окружения для выбора браузера
    public static final String BROWSER_NAME_SYS_PROPERTY = "browser.name"; // Системное свойство для выбора браузера
    public static final String DEFAULT_BROWSER_NAME = "CHROME"; // Браузер по умолчанию

    public static final String PAGE_URL = "https://stellarburgers.nomoreparties.site/";

    // Эндпоинты для управления пользователями
    public static final String AUTH_REGISTER_ENDPOINT = PAGE_URL + "api/auth/register";
    public static final String AUTH_LOGIN_ENDPOINT = PAGE_URL + "api/auth/login";
    public static final String AUTH_LOGOUT_ENDPOINT = PAGE_URL + "api/auth/logout";
    public static final String AUTH_USER_ENDPOINT = PAGE_URL + "api/auth/user";

    public static final By LOGINBUTTON = By.xpath("//button[text()='Войти в аккаунт']");
    public static final By PROFILEBUTTON = By.xpath("//p[text()='Личный Кабинет']");
    public static final By CONSTRUCTORBUTTON = By.xpath("//p[text()='Конструктор']");
    public static final By LOGOBUTTON = By.className("AppHeader_header__logo__2D0X2");
    public static final By RECOVER_PASSWORD_LINK = By.xpath("//a[contains(@class, 'Auth_link__1fOlj') and contains(text(), 'Восстановить пароль')]");
    public static final By REGISTER_LINK = By.cssSelector("a[href='/register']");

    public static final By LOGINFIELD = By.cssSelector("input[type='email'][name='name']");
    public static final By PASSWORDFIELD = By.xpath("//input[@name='Пароль']");
    public static final By SUBMITBUTTON = By.cssSelector("button.button_button_type_primary__1O7Bx");

    public static final By NAMEFIELD = By.xpath("//input[@name='name'][preceding-sibling::label[text()='Имя']]");
    public static final By EMAILFIELD = By.xpath("//input[@name='name'][preceding-sibling::label[text()='Email']]");
    public static final By PASSWORD_FIELD = By.cssSelector("input[type='password'][name='Пароль']");
    public static final By REGISTERBUTTON = By.xpath("//button[text()='Зарегистрироваться']");
    public static final By ERRORMESSAGEPASS = By.xpath("//p[contains(@class, 'input__error') and contains(text(), 'Некорректный пароль')]");

    public static final By BUTTONLOGIN = By.xpath("//a[contains(@class, 'Auth_link__1fOlj') and text()='Войти']");

    public static final By BUNSTAB = By.xpath("//div[contains(@class, 'tab_tab__') and .//span[text()='Булки']]");
    public static final By SAUCESTAB = By.xpath("//div[contains(@class, 'tab_tab__') and .//span[text()='Соусы']]");
    public static final By FILLINGSTAB = By.xpath("//div[contains(@class, 'tab_tab__') and .//span[text()='Начинки']]");
    public static final By ACTIVETAB = By.xpath("//div[contains(@class, 'tab_tab_type_current')]");
    public static final By BUNS_SECTION = By.xpath("//h2[contains(@class, 'text_type_main-medium') and contains(text(), 'Булки')]");
    public static final By SAUCES_SECTION = By.xpath("//h2[contains(@class, 'text_type_main-medium') and contains(text(), 'Соусы')]");
    public static final By FILLINGS_SECTION = By.xpath("//h2[contains(@class, 'text_type_main-medium') and contains(text(), 'Начинки')]");

    public static final By LOGOUTBUTTON = By.xpath("//li[contains(@class, 'Account_listItem__35dAP')]//button[text()='Выход']");
}
