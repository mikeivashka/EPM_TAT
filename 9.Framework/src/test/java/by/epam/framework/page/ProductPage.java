package by.epam.framework.page;

import by.epam.framework.service.TestDataReader;
import lombok.SneakyThrows;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.naming.OperationNotSupportedException;
import java.time.Duration;
import java.util.Arrays;

public class ProductPage extends AbstractPage {

    private final By checkoutButtonLocator = By.linkText("Checkout Now");
    @FindBy(name = "button")
    private WebElement addToCartButton;

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    public ProductPage addAllProductsFromPropertiesToCart() {
        System.out.println(System.getProperty("environment"));
        String[] productPageUrls = TestDataReader.getProductPagesLinksArray();
        Arrays.stream(productPageUrls).forEach(p -> {
            driver.get(p);
            addToCartButton.click();
        });
        return new ProductPage(driver);
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
