package by.epam.framework.page;

import by.epam.framework.service.TestDataReader;
import lombok.SneakyThrows;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.naming.OperationNotSupportedException;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class ProductPage extends AbstractPage {

    private final By checkoutButtonLocator = By.partialLinkText("Checkout Now");

    @FindBy(name = "button")
    private WebElement addToCartButton;


    public ProductPage(RemoteWebDriver driver) {
        super(driver);
    }

    public ProductPage addAllProductsFromPropertiesToCart() {
        String[] productPageUrls = TestDataReader.getProductPagesLinksArray();
        Arrays.stream(productPageUrls).forEach(p -> {
            driver.get(p);
            addToCartButton.click();
        });
        return new ProductPage(driver);
    }

    public ProductPage addLowStockProductFromPropertiesToCart() {
        driver.get(TestDataReader.getTestData("testdata.product.pages.low-in-stock"));
        addToCartButton.click();
        return this;
    }

    public CartPage clickCheckoutButton() {

        WebElement checkoutButton = new WebDriverWait(driver, Duration.ofSeconds(15))
                .until(ExpectedConditions
                        .visibilityOfElementLocated(checkoutButtonLocator));
        checkoutButton.click();
        return new CartPage(driver);
    }

    @SneakyThrows
    @Override
    public ProductPage openPage() {
        throw new OperationNotSupportedException("Unspecified product page call");
    }
}
