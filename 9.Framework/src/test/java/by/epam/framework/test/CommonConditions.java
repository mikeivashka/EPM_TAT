package by.epam.framework.test;


import by.epam.framework.driver.DriverSingleton;
import by.epam.framework.utils.TestListener;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Listeners;

@Listeners({TestListener.class})
public class CommonConditions extends Assertions {

    protected WebDriver driver;


    @BeforeEach
    public void setUp() {
        driver = DriverSingleton.getDriver();
    }

    @AfterEach
    public void tearDown() {
        DriverSingleton.closeDriver();
    }
}
