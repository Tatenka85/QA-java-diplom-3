import common.BaseUITest;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Assert;
import org.junit.Test;
import steps.ConstructorSteps;

public class ConstructorNavigationTest extends BaseUITest {

    @Test
    @DisplayName("Вкладка с булочками становится активной при клике")
    @Description("Проверка, что при клике на вкладку с булочками она становится активной на странице конструктора.")
    public void testBunsTabIsActiveAfterClick() {
        ConstructorSteps constructorSteps = new ConstructorSteps(driver);
        constructorSteps.openConstructorPage();

        constructorSteps.clickBunsTab();
        Assert.assertTrue("Вкладка с булочками не стала активной", constructorSteps.checkIfBunsTabIsCurrent());
    }

    @Test
    @DisplayName("Вкладка с соусами становится активной при клике")
    @Description("Проверка, что при клике на вкладку с соусами она становится активной на странице конструктора.")
    public void testSaucesTabIsActiveAfterClick() {
        ConstructorSteps constructorSteps = new ConstructorSteps(driver);
        constructorSteps.openConstructorPage();

        constructorSteps.clickSaucesTab();
        Assert.assertTrue("Вкладка с соусами не стала активной", constructorSteps.checkIfSaucesTabIsCurrent());
    }

    @Test
    @DisplayName("Вкладка с начинками становится активной при клике")
    @Description("Проверка, что при клике на вкладку с начинками она становится активной на странице конструктора.")
    public void testFillingsTabIsActiveAfterClick() {
        ConstructorSteps constructorPage = new ConstructorSteps(driver);
        constructorPage.openConstructorPage();

        constructorPage.clickFillingsTab();
        Assert.assertTrue("Вкладка с начинками не стала активной", constructorPage.checkIfFillingsTabIsCurrent());
    }
}
