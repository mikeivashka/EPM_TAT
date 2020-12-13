package by.epam.framework.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CartPage extends AbstractPage {

    public static final String CART_PAGE_URL = "https://www.decluttr.com/us/store/cart";

    private final By elementPriceLocator = By.className("basket__amount");


    protected CartPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public CartPage openPage() {
        driver.get(CART_PAGE_URL);
        return this;
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

    private WebElement getInputByIndex(Integer index) {
        return driver.findElement(By.id("order_line_items_attributes_" + index + "_quantity"));
    }

    public void setAmountOfProductAtIndex(Integer index, Integer amount) {
        getInputByIndex(index).sendKeys(amount.toString());
    }
}
