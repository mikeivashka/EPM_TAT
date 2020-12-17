package by.epam.framework.page;

import by.epam.framework.service.TestDataReader;
import lombok.SneakyThrows;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.naming.OperationNotSupportedException;
import java.util.Arrays;

public class ProductPage extends AbstractPage {

    private final By checkoutButtonLocator = By.partialLinkText("Checkout Now");

    private final By continueShoppingButtonLocator = By.partialLinkText("Continue Shopping");

    @FindBy(name = "button")
    private WebElement addToCartButton;

    @FindBy(id = "variant-price")
    private WebElement productPrice;

    public ProductPage(RemoteWebDriver driver) {
        super(driver);
    }

    public ProductPage addAllProductsFromPropertiesToCart() {
        String[] productPageUrls = TestDataReader.getProductPagesLinksArray();
        Arrays.stream(productPageUrls).forEach(p -> {
            driver.get(p);
            clickAddToCartButton();
        });
        return new ProductPage(driver);
    }

    public ProductPage clickAddToCartButton() {
        addToCartButton.click();
        return this;
    }

    public ProductPage addLowStockProductFromPropertiesToCart() {
        driver.get(TestDataReader.getTestData("testdata.product.pages.low-in-stock"));
        clickAddToCartButton();
        return this;
    }

    public Double getInCartTotal() {
        String textInPriceField = new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions.visibilityOfElementLocated(inCartTotalLocator))
                .getText()
                .substring(1)
                .replaceAll(",", "");
        return Double.parseDouble(textInPriceField);
    }

    public Double getCurrentPrice() {
        return Double.parseDouble(productPrice.getAttribute("data-price"));
    }

    public CartPage clickCheckoutButton() {
        WebElement checkoutButton = new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions
                        .visibilityOfElementLocated(checkoutButtonLocator));
        checkoutButton.click();
        return new CartPage(driver);
    }

    public ProductPage clickContinueShoppingButton() {
        WebElement continueShoppingButton = new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions
                        .visibilityOfElementLocated(continueShoppingButtonLocator));
        continueShoppingButton.click();
        return this;
    }

    public ProductPage openPage(String url) {
        driver.get(url);
        return this;
    }

    @SneakyThrows
    @Override
    public ProductPage openPage() {
        throw new OperationNotSupportedException("Unspecified product page call");
    }
}
