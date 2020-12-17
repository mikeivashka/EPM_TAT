package by.epam.framework.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;

public class ProfilePage extends AbstractPage{
    private final String PROFILE_PAGE_URL = "https://www.decluttr.com/my-account/";

    @FindBy(id = "ContentPlaceHolderDefault_mainContent_MMStoreMyProfileMenu_Shopping_5_btnSignOut")
    private WebElement logOutButton;

    public ProfilePage(RemoteWebDriver driver) {
        super(driver);
    }

    public void logOut(){
        logOutButton.click();
    }

    @Override
    public ProfilePage openPage() {
        driver.get(PROFILE_PAGE_URL);
        return this;
    }
}
