import common.BaseUITest;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import steps.ConstructorPage;
import steps.LoginPage;
import steps.UserAccountPage;
import pages.User;
import steps.UserApi;

import java.time.Duration;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;

public class UserAccountTest extends BaseUITest {

    protected UserApi userApi;
    protected User user;
    protected String accessToken;

    @Before
    public void setUp() {
        userApi = new UserApi();
        String name = "Tanya";
        String password = "tatka1234567";
        String email = "btata@qweasdmail.com";
        user = new User(name, password, email);

        // Удаляем пользователя, если он уже существует
        try {
            ValidatableResponse loginResponse = userApi.loginUser(user);
            if (loginResponse.extract().statusCode() == HttpStatus.SC_OK) {
                String token = loginResponse.extract().path("accessToken");
                userApi.deleteUser(token)
                        .assertThat()
                        .statusCode(HttpStatus.SC_ACCEPTED);
            }
        } catch (Exception e) {
            // Пользователь не существует, продолжаем
        }

        // Регистрируем нового пользователя
        ValidatableResponse response = userApi.createUser(user);
        response.log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .body("success", is(true));

        // Логиним пользователя и получаем accessToken
        user.setName(null); // Убираем имя для логина
        response = userApi.loginUser(user);
        response.log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_OK);

        accessToken = response.extract().path("accessToken");

        // Открываем страницу логина и выполняем вход через UI
        LoginPage loginPage = new LoginPage(driver);
        loginPage.openLoginPage();

        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.urlToBe(LoginPage.LOGIN_PAGE_URL));

        loginPage.setEmail(email);
        loginPage.setPassword(password);
        loginPage.clickLoginButton();

        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.urlToBe(ConstructorPage.CONSTRUCTOR_PAGE_URL));

        // Переходим в личный кабинет
        ConstructorPage constructorPage = new ConstructorPage(driver);
        constructorPage.clickUserAccountButton();

        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.urlToBe(UserAccountPage.userAccountPageUrl));
    }

    @After
    public void userDelete() {
        if (accessToken != null) {
            ValidatableResponse response = userApi.deleteUser(accessToken);
            response.log().all()
                    .assertThat()
                    .statusCode(HttpStatus.SC_ACCEPTED)
                    .and()
                    .body("success", is(true));
        } else {
            System.out.println("AccessToken is null. User was not deleted.");
        }
    }

    @Test
    @DisplayName("Открытие страницы учетной записи пользователя")
    @Description("Проверка, что при входе в систему пользователь может попасть на страницу своей учетной записи.")
    public void openUserAccount() {
        String currentUrl = driver.getCurrentUrl();
        assertEquals(UserAccountPage.userAccountPageUrl, currentUrl);
    }

    @Test
    @DisplayName("Выход из учетной записи пользователя")
    @Description("Проверка, что пользователь может выйти из своей учетной записи и перейти на страницу логина.")
    public void logoutFromUserAccount() {
        UserAccountPage userAccountPage = new UserAccountPage(driver);
        userAccountPage.clickLogoutButton();

        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.urlToBe(LoginPage.LOGIN_PAGE_URL));

        String currentUrl = driver.getCurrentUrl();
        assertEquals(LoginPage.LOGIN_PAGE_URL, currentUrl);
    }
}