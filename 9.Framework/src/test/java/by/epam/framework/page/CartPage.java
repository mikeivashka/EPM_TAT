package by.epam.framework.page;

import lombok.SneakyThrows;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfAllElementsLocatedBy;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public final class CartPage extends AbstractPage {

    public static final String CART_PAGE_URL = "https://www.decluttr.com/us/store/cart";

    private final By elementPriceLocator = By.xpath("//div[@class='basket__amount']/strong");

    private final By removeElementButtonLocator = By.xpath("//button[contains(@id, 'delete_line_item_')]");

    private final By emptyCartNotifierLocator = By.xpath("//div[@class='callout primary']");

    private final By continueShoppingButtonLocator = By.xpath("//a[text()='Continue shopping']");

    private final By inStockLimitLocator = By.xpath("//span[contains(text(),'stock')] | //span[contains(text(),'Stock')]");

    private final By lowStockAlertLocator = By.xpath("//div[@class='callout alert']/ul/li");

    private final By checkoutButtonLocator = By.id("checkout-btn2");

    private final By totalItemsInCartLocator = By.xpath("(//*[contains(text(), 'Cart (')])[1]");

    public CartPage(RemoteWebDriver driver) {
        super(driver);
    }

    @Override
    public CartPage openPage() {
        driver.get(CART_PAGE_URL);
        setRequiredCookies();
        return this;
    }

    public Integer countTotalItemsInCart() {
        String tagText = new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions
                        .visibilityOfElementLocated(totalItemsInCartLocator)).getText();
        return Integer.parseInt(tagText.transform(t -> t = t.substring(t.indexOf("(")+1, t.indexOf(")"))));
    }

    public Integer countDifferentItemsInCart() {
        return driver.findElements(elementPriceLocator).size();
    }

    public CartPage clickRemoveButtonOfProductWithIndex(Integer index) {
        waitForDocumentReadyState();
        driver.findElements(removeElementButtonLocator).get(index).click();
        return this;
    }

    public Integer getAmountOfProductWithIndex(Integer index) {
        return Integer.parseInt(getInputByIndex(index).getAttribute("value"));
    }

    public Double getPriceOfProductWithIndex(Integer index) {
        return Double.valueOf(
                new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                        .until(presenceOfAllElementsLocatedBy(elementPriceLocator)
                        )
                        .get(index)
                        .getText()
                        .substring(1)
        );
    }

    public boolean isCartEmpty() {
        return getEmptyCartNotifierText().contains("Your cart is empty");
    }

    public String getEmptyCartNotifierText() {
        return driver.findElement(emptyCartNotifierLocator).getText();
    }

    public Double inCartTotal() {
        waitForDocumentReadyState();
        return Double.valueOf(
                new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                        .until(visibilityOfElementLocated(inCartTotalLocator))
                        .getText()
                        .transform(s ->
                                s = s.substring(s.indexOf("$") + 1, s.indexOf(".") + 3)
                                        .replace(",", ""))
        );
    }

    public CategoryPage clickContinueShoppingButton() {
        waitForDocumentReadyState();
        new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(visibilityOfElementLocated(continueShoppingButtonLocator))
                .click();
        return new CategoryPage(driver);
    }

    public Integer getInStockForProductWithIndex(Integer index) {
        waitForDocumentReadyState();
        return Integer.parseInt(new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(visibilityOfElementLocated(inStockLimitLocator))
                .getText()
                .transform(s -> {
                    s = s.toLowerCase();
                    return s.substring(s.indexOf(" in stock") - 1, s.indexOf(" in stock"));
                }));

    }

    public String lineItemsQuantityWarning() {
        return new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(visibilityOfElementLocated(lowStockAlertLocator))
                .getText();
    }

    public LoginPage checkoutUnAuthorized() {
        driver.findElement(checkoutButtonLocator).click();
        return new LoginPage(driver);
    }

    private WebElement getInputByIndex(Integer index) {
        return driver.findElement(By.id("order_line_items_attributes_" + index + "_quantity"));
    }

    @SneakyThrows
    public CartPage setAmountOfProductAtIndex(Integer index, Integer amount) {
        setAttribute(getInputByIndex(index), "value", amount.toString());
        getInputByIndex(index).submit();
        return this;
    }
}
