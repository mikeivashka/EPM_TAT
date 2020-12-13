package by.epam.framework.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class CategoryPage extends AbstractPage {

    private static final String CATEGORY_PAGE_URL = "https://www.decluttr.com/us/store/category/cell-phones/apple/";


    @FindBy(xpath = "//result//h5")
    private List<WebElement> filteredProductsPrices;

    @FindBy(id = "price-lower-bound")
    private WebElement minPriceInput;

    @FindBy(id = "price-upper-bound")
    private WebElement maxPriceInput;

    @FindBy(partialLinkText = "GO BACK")
    private WebElement goBackButton;

    @FindBy(xpath = "//button[text()=' >> ']")
    private WebElement submitFilterButton;

    public CategoryPage(WebDriver driver) {
        super(driver);
    }

    public CategoryPage setMaxPrice(Integer price) {
        maxPriceInput.clear();
        maxPriceInput.sendKeys(price.toString());
        return this;
    }

    public CategoryPage setMinPrice(Integer price) {
        minPriceInput.clear();
        minPriceInput.sendKeys(price.toString());
        return this;
    }

    public String getCurrentMaxPriceValue() {
        return maxPriceInput.getText();
    }

    public String getCurrentMinPriceValue() {
        return minPriceInput.getText();
    }

    public List<Double> allFilteredProductsPrices() {
        List<Double> prices;
        new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions
                        .refreshed(ExpectedConditions
                                .or(
                                        ExpectedConditions.elementToBeClickable(goBackButton),
                                        ExpectedConditions.visibilityOfAllElements(filteredProductsPrices)
                                )
                        )
                );
        prices = filteredProductsPrices.stream().map(e -> Double.parseDouble(
                e.getText()
                        .transform(s -> {
                            s = s.trim().substring(1, s.indexOf('\n'));
                            return s;
                        })
        )).collect(Collectors.toCollection(LinkedList::new));
        return prices;
    }

    public CategoryPage clickGoBackButton() {
        goBackButton.click();
        return this;
    }

    public CategoryPage submitFilters() {
        submitFilterButton.click();
        return this;
    }

    @Override
    public CategoryPage openPage() {
        driver.get(CATEGORY_PAGE_URL);
        return this;
    }
}
