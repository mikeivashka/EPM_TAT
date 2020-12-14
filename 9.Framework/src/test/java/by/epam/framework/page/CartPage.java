package by.epam.framework.page;

import lombok.SneakyThrows;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

public class CartPage extends AbstractPage {

    public static final String CART_PAGE_URL = "https://www.decluttr.com/us/store/cart";

    private final By elementPriceLocator = By.className("basket__amount");

    private final By inCartTotalLocator = By.xpath("//div[contains(@class, 'basket-total')]");

    protected CartPage(RemoteWebDriver driver) {
        super(driver);
    }

    @Override
    public CartPage openPage() {
        driver.get(CART_PAGE_URL);
        return this;
    }

    public Integer inCartDifferentItemsCount(){
    }

    public Integer getAmountOfProductWithIndex(Integer index) {
        return Integer.parseInt(getInputByIndex(index).getAttribute("value"));
    }

    public Double getPriceOfProductWithIndex(Integer index) {
        return Double.valueOf(
                driver.findElements(elementPriceLocator)
                        .get(index)
                        .getText()
                        .substring(1)
        );
    }

    public Double inCartTotal() {
        return Double.valueOf(driver
                .findElement(inCartTotalLocator)
                .getText()
                .transform(s ->
                        s = s.substring(s.indexOf("$") + 1, s.indexOf(".") + 3)
                                .replace(",", ""))
        );
    }

    private WebElement getInputByIndex(Integer index) {
        return driver.findElement(By.id("order_line_items_attributes_" + index + "_quantity"));
    }

    @SneakyThrows
    public void setAmountOfProductAtIndex(Integer index, Integer amount) {
        setAttribute(getInputByIndex(index), "value", amount.toString());
        getInputByIndex(index).submit();
        waitForDocumentReadyState();
    }
}
