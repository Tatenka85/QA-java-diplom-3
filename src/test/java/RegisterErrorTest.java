import common.BaseUITest;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import steps.RegisterSteps;

import static org.junit.Assert.assertEquals;

public class RegisterErrorTest extends BaseUITest {

    @Test
    @DisplayName("Невозможно зарегистрироваться с коротким паролем")
    @Description("Проверка, что регистрация невозможна, если пароль короче 6 символов.")
    public void testRegistrationWithShortPassword() {

        steps.RegisterSteps registerSteps = new RegisterSteps(driver);
        registerSteps.openRegistrationPage();

        registerSteps.setName(RandomStringUtils.randomAlphabetic(4));
        registerSteps.setEmail(RandomStringUtils.randomAlphabetic(4));
        registerSteps.setPassword(RandomStringUtils.randomAlphabetic(4));
        registerSteps.clickRegisterButton();

        String expectedError = "Некорректный пароль";
        assertEquals("Ошибка при регистрации: " + registerSteps.getRegistrationErrorMessage(), expectedError, registerSteps.getRegistrationErrorMessage());
    }
}