package by.epam.framework.test;


import by.epam.framework.driver.DriverSingleton;
import by.epam.framework.page.CartPage;
import by.epam.framework.page.LoginPage;
import by.epam.framework.service.ScreenshotOnFailure;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.openqa.selenium.remote.RemoteWebDriver;

@Slf4j
public class CommonConditions extends Assert {

    @Rule
    public ScreenshotOnFailure screenShootRule = new ScreenshotOnFailure(DriverSingleton.getDriver());
    protected RemoteWebDriver driver;

    protected static void clearUserCart() {
        if (new LoginPage(DriverSingleton.getDriver()).isAuthorized() && !new CartPage(DriverSingleton.getDriver()).openPage().isCartEmpty()) {
            CartPage cartPage = new CartPage(DriverSingleton.getDriver());
            while (!cartPage.isCartEmpty()) {
                cartPage.clickRemoveButtonOfProductWithIndex(0);
            }
        }
    }

    @Before
    public void setUp() {
        driver = DriverSingleton.getDriver();
        driver.manage().deleteAllCookies();
    }
}
