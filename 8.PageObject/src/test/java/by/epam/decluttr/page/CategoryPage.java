package by.epam.decluttr.page;

import org.openqa.selenium.WebDriver;

public class CategoryPage extends AbstractPage {

    private static final String CATEGORY_PAGE_URL = "https://www.decluttr.com/us/store/category/cell-phones/apple/";

    public CategoryPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected AbstractPage openPage() {
        driver.get(CATEGORY_PAGE_URL);
        return this;
    }
}
