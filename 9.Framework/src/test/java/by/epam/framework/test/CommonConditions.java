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

    @Before
    public void setUp() {
        driver = DriverSingleton.getDriver();
        driver.manage().deleteAllCookies();
    }

    protected void clearUserCart() {
        if (new LoginPage(driver).isAuthorized() && !new CartPage(driver).openPage().isCartEmpty()) {
            CartPage cartPage = new CartPage(driver);
            while (!cartPage.isCartEmpty()) {
                cartPage.clickRemoveButtonOfProductWithIndex(0);
            }
        }
    }
}
