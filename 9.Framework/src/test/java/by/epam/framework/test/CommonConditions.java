package by.epam.framework.test;


import by.epam.framework.driver.DriverSingleton;
import by.epam.framework.page.AbstractPage;
import by.epam.framework.page.CartPage;
import by.epam.framework.page.LoginPage;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.remote.RemoteWebDriver;

@Slf4j
public class CommonConditions extends Assertions {

    protected RemoteWebDriver driver;

    @BeforeEach
    public void setUp() {
        driver = DriverSingleton.getDriver();
        driver.manage().deleteAllCookies();
    }

    @AfterEach
    public void tearDown() {
        DriverSingleton.closeDriver();
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
