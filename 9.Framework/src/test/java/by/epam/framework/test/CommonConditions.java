package by.epam.framework.test;


import by.epam.framework.driver.DriverSingleton;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.remote.RemoteWebDriver;

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
}
