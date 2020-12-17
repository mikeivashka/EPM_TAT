package by.epam.framework.page;

import by.epam.framework.utils.SortingType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.*;
import java.util.stream.Collectors;

public class CategoryPage extends AbstractPage {

    private static final String CATEGORY_PAGE_URL = "https://www.decluttr.com/us/store/category/cell-phones/apple/";
    @FindBy(xpath = "//strong[@class='ng-binding']")
    List<WebElement> productTitles;
    @FindBy(xpath = "//div[@id='search-container']//select[@name='sort-by']")
    private WebElement sortingSelect;
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

    public CategoryPage(RemoteWebDriver driver) {
        super(driver);
    }

    public CategoryPage setMaxPrice(Integer price) {
        maxPriceInput.clear();
        maxPriceInput.sendKeys(price.toString());
        return this;
    }

    public CategoryPage setSortBy(SortingType sortBy) {
        new Select(sortingSelect).selectByValue(sortBy.getValue());
        return this;
    }

    public SortingType getCurrentSortBy() {
        var select = new Select(sortingSelect);
        Optional<SortingType> foundSelectedType = Arrays.stream(SortingType.values())
                .filter(t -> t.getName()
                        .equals(select.getFirstSelectedOption().getText()))
                .findFirst();
        if (foundSelectedType.isPresent()) return foundSelectedType.get();
        throw new NoSuchElementException();
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

    public List<String> allProductsTitles() {
        waitForResultUpdate();
        List<String> titles = new ArrayList<>();
        productTitles.forEach(p -> titles.add(p.getText()));
        return titles;
    }

    public List<Double> allProductsPrices() {
        List<Double> prices;
        waitForResultUpdate();
        prices = filteredProductsPrices.stream().map(e -> Double.parseDouble(
                e.getText()
                        .transform(s -> {
                            s = s
                                    .trim()
                                    .replaceAll(",", "")
                                    .substring(1, s.indexOf('\n'));
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
        setRequiredCookies();
        waitForDocumentReadyState();
        return this;
    }

    private void waitForResultUpdate() {
        new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions
                        .refreshed(ExpectedConditions
                                .or(
                                        ExpectedConditions.elementToBeClickable(goBackButton),
                                        ExpectedConditions.visibilityOfAllElements(filteredProductsPrices)
                                )
                        )
                );
    }
}
