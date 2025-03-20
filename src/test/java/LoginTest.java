import com.github.javafaker.Faker;
import common.BaseUITest;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import steps.*;
import steps.RegisterSteps;
import pages.User;
import steps.UserApiSteps;

import java.time.Duration;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class LoginTest extends BaseUITest {

    protected UserApiSteps userApi;
    protected User user;
    private final int loginVersion;
    protected String accessToken;

    private final Faker faker = new Faker();

    public LoginTest(int loginVersion) {
        this.loginVersion = loginVersion;
    }

    @Before
    public void setUp() {
        userApi = new UserApiSteps();

        String randomName = faker.name().fullName();
        String randomPassword = faker.internet().password();
        String randomEmail = faker.internet().emailAddress();

        user = new User(randomName, randomPassword, randomEmail);

        ValidatableResponse response = userApi.createUser(user);
        response.log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .body("success", is(true));

        user.setName(null);
        response = userApi.loginUser(user);
        response.log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_OK);

        accessToken = response.extract().path("accessToken");
    }

    @After
    public void userDelete() {
        ValidatableResponse response = userApi.deleteUser(accessToken);
        response.log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_ACCEPTED)
                .and()
                .body("success", is(true));
    }

    @Parameterized.Parameters
    public static Object[][] getUserData() {
        return new Object[][] {
                { 1 },
                { 2 },
                { 3 },
                { 4 }
        };
    }

    @Test
    @DisplayName("Авторизация через различные способы")
    @Description("Проверка успешной авторизации через разные варианты входа в систему.")
    public void testLoginWithDifferentMethods() {
        switch (loginVersion) {
            case 1:
            case 2:
                ConstructorSteps constructorPage = new ConstructorSteps(driver);
                constructorPage.openConstructorPage();
                constructorPage.loginVersions(loginVersion);
                break;
            case 3:
                RegisterSteps registerSteps = new RegisterSteps(driver);
                registerSteps.openRegistrationPage();
                registerSteps.switchToLoginPage();
                break;
            case 4:
                ForgotPasswordSteps forgotPasswordSteps = new ForgotPasswordSteps(driver);
                forgotPasswordSteps.openForgotPasswordPage();
                forgotPasswordSteps.switchToLoginPage();
                break;
        }

        LoginSteps loginSteps = new LoginSteps(driver);

        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.urlToBe(LoginSteps.LOGIN_PAGE_URL));

        String currentUrl = driver.getCurrentUrl();
        assertEquals(LoginSteps.LOGIN_PAGE_URL, currentUrl);

        loginSteps.setEmail(user.getEmail());
        loginSteps.setPassword(user.getPassword());
        loginSteps.clickLoginButton();

        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.urlToBe(ConstructorSteps.CONSTRUCTOR_PAGE_URL));
        currentUrl = driver.getCurrentUrl();
        assertEquals(ConstructorSteps.CONSTRUCTOR_PAGE_URL, currentUrl);
    }
}
