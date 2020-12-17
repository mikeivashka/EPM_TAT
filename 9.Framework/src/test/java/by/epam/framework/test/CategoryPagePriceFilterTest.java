package by.epam.framework.test;

import by.epam.framework.page.CategoryPage;
import org.junit.Test;

import java.util.List;

public class CategoryPagePriceFilterTest extends CommonConditions {

    private static final int minPriceValue = 400;
    private static final int maxPriceValue = 800;

    @Test
    public void categoryPagePriceFilterTest() {

        var categoryPage = new CategoryPage(driver);
        List<Double> pricesAfterFiltering = categoryPage
                .openPage()
                .setMinPrice(minPriceValue)
                .setMaxPrice(maxPriceValue)
                .submitFilters()
                .allFilteredProductsPrices();
        assertTrue(pricesAfterFiltering.stream().noneMatch(p -> p < minPriceValue && p > maxPriceValue));

        String maxPriceValueAfterReload = categoryPage
                .setMinPrice(maxPriceValue + 1)
                .getCurrentMaxPriceValue();
        assertTrue(maxPriceValueAfterReload.isBlank());
        String lastVisitedUrl = driver.getCurrentUrl();

        List<Double> pricesAfterInvalidFilter = categoryPage
                .setMinPrice(10000)
                .submitFilters()
                .allFilteredProductsPrices();
        assertTrue(pricesAfterInvalidFilter.isEmpty());

        categoryPage.clickGoBackButton();
        assertEquals(lastVisitedUrl, driver.getCurrentUrl());
        assertTrue(categoryPage.getCurrentMinPriceValue().isBlank());
        assertTrue(categoryPage.getCurrentMaxPriceValue().isBlank());
    }
}
