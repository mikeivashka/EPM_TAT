package by.epam.framework.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

public class DriverSingleton {
    private static RemoteWebDriver driver;

    private DriverSingleton() {
    }

    public static RemoteWebDriver getDriver() {
        if (driver == null) {
            System.setProperty("environment", "dev");
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
            driver.manage().window().maximize();
        }
        return driver;
    }

    public static void closeDriver(){
        driver.quit();
        driver = null;
    }
}