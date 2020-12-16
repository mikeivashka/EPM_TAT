package by.epam.framework.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.Collections;

public class DriverSingleton {
    private static RemoteWebDriver driver;

    private DriverSingleton() {
    }

    public static RemoteWebDriver getDriver() {
        if (driver == null) {
            System.setProperty("environment", "dev");
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.setExperimentalOption("excludeSwitches",
                    Collections.singletonList("disable-popup-blocking"));
            options.addArguments("disable-infobars");
            driver = new ChromeDriver(options);
            driver.manage().window().setSize(new Dimension(1400, 800));
        }
        return driver;
    }

    public static void closeDriver() {
        driver.quit();
        driver = null;
    }
}