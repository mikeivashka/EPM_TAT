package by.epam.framework.page;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.Arrays;
import java.util.List;

public class CheckOutPage extends AbstractPage{

    public final String CHECKOUT_PAGE_URL = "https://www.decluttr.com/us/store/checkout/";

    @FindBy(xpath = "//div[@class='text-center mbottom mtop summary__total']")
    private WebElement totalToPay;

    public CheckOutPage(RemoteWebDriver driver) {
        super(driver);
    }

    public Double totalToPay(){
        return Double.valueOf(totalToPay.getAttribute("data-total"));
    }

    @Override
    public CheckOutPage openPage() {
        driver.get(CHECKOUT_PAGE_URL);
        setRequiredCookies();
        return this;
    }
}
