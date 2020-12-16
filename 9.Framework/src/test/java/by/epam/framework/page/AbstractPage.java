package by.epam.framework.page;

import lombok.extern.java.Log;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

@Log
public abstract class AbstractPage {

    protected final Duration WAIT_TIMEOUT_SECONDS = Duration.ofSeconds(15);

    protected RemoteWebDriver driver;

    public static final String BASIC_SHOP_URL = "https://www.decluttr.com/us/store/";

    protected AbstractPage(RemoteWebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public abstract AbstractPage openPage();

    protected void setRequiredCookies() {
        driver.manage().deleteCookieNamed("sessionExperiments");
        requiredCookies().forEach(c -> driver.manage().addCookie(c));
    }

    protected List<Cookie> requiredCookies() {
        return Arrays.asList(
                new Cookie
                        .Builder("DataLayer_UserLogged", "true")
                        .domain(".decluttr.com")
                        .build(),
                new Cookie
                        .Builder("decluttrStoreCompetition-enCapture", "false")
                        .domain(".decluttr.com")
                        .build(),
                new Cookie
                        .Builder("sessionExperiments", "109:0,115:0")
                        .domain(".decluttr.com")
                        .isSecure(true)
                        .sameSite("Strict")
                        .build()
        );
    }

    public Boolean isAuthorized() {
        driver.get(LoginPage.LOGIN_PAGE_URL);
        return !driver.getCurrentUrl().equals(LoginPage.LOGIN_PAGE_URL);
    }

    protected void setAttribute(WebElement element, String attributeName, String attributeValue) {
        driver.executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);",
                element, attributeName, attributeValue);
    }

    protected void waitForDocumentReadyState() {
        if (driver.manage().getCookieNamed("sessionExperiments") != null) {
            log.info("Experimental cookie: " + driver.manage().getCookieNamed("sessionExperiments"));
            setRequiredCookies();
        }
        new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS).until(
                webDriver ->
                        ((JavascriptExecutor) webDriver)
                                .executeScript("return document.readyState")
                                .equals("complete"));
    }
}
