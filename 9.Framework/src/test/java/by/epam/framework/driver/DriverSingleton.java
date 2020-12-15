package by.epam.framework.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.Arrays;

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
                    Arrays.asList("disable-popup-blocking"));
            options.addArguments("disable-infobars", "--window-size=1700,1080");
            //options.addArguments("user-data-dir=C:\\Users\\tanki\\AppData\\Local\\Google\\Chrome\\User Data\\Default");
            driver = new ChromeDriver(options);
            //driver.manage().window().maximize();
        }
        return driver;
    }

    public static void closeDriver() {
        driver.quit();
        driver = null;
    }
}