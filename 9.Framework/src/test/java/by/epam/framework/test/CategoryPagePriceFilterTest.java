package by.epam.framework.test;

import by.epam.framework.page.CategoryPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

class CategoryPagePriceFilterTest extends CommonConditions {

    @DisplayName("decluttr-10")
    @CsvSource(value = {"400,800"})
    @ParameterizedTest
    void categoryPagePriceFilterTest(int minPriceValue, int maxPriceValue) {
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
