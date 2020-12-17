package by.epam.framework.page;

import by.epam.framework.service.TestDataReader;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends AbstractPage {

    public static final String LOGIN_PAGE_URL = "https://www.decluttr.com/login/";

    @FindBy(id = "email")
    private WebElement emailInput;

    @FindBy(id = "Password")
    private WebElement passwordInput;

    @FindBy(className = "send")
    private WebElement signInButton;

    @FindBy(partialLinkText = "Accept all cookies")
    private WebElement acceptCookiesButton;

    public LoginPage(RemoteWebDriver driver) {
        super(driver);
    }

    public LoginPage clickAcceptCookiesButton() {
        acceptCookiesButton.click();
        return this;
    }

    public void logInUserFromProperties() {
        String email = TestDataReader.getTestData("testdata.user.email");
        String password = TestDataReader.getTestData("testdata.user.password");
        emailInput.sendKeys(email);
        passwordInput.sendKeys(password);
        signInButton.click();
    }

    @Override
    public LoginPage openPage() {
        driver.get(LOGIN_PAGE_URL);
        setRequiredCookies();
        return this;
    }
}
