package by.epam.framework.page;

import org.openqa.selenium.remote.RemoteWebDriver;

public class LoginPage extends AbstractPage {

    public static final String LOGIN_PAGE_URL = "https://www.decluttr.com/login/";

    protected LoginPage(RemoteWebDriver driver) {
        super(driver);
    }

    @Override
    public LoginPage openPage() {
        driver.get(LOGIN_PAGE_URL);
        return this;
    }
}
