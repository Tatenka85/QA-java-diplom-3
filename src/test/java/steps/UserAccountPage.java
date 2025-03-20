package steps;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pages.BasePage;

import static pages.Constants.PAGE_URL;

public class UserAccountPage extends BasePage {

    public static final String userAccountPageUrl = PAGE_URL + "account/profile";
    private final By logoutButton = By.xpath("//button[text()='Выход']");

    public UserAccountPage(WebDriver driver){
        this.driver = driver;
    }

    @Step("Выйти из аккаунта")
    public void clickLogoutButton() {
        clickElementButton(logoutButton);
    }
}
