package by.epam.framework.page;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class AbstractPage {

    protected final Duration WAIT_TIMEOUT_SECONDS = Duration.ofSeconds(15);
    protected RemoteWebDriver driver;

    protected AbstractPage(RemoteWebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public abstract AbstractPage openPage();

    protected void setAttribute(WebElement element, String attributeName, String attributeValue) {
        driver.executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);",
                element, attributeName, attributeValue);
    }

    protected void waitForDocumentReadyState() {
        new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS).until(
                webDriver ->
                        ((JavascriptExecutor) webDriver)
                                .executeScript("return document.readyState")
                                .equals("complete"));
    }
}
