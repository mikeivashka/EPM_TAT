package by.epam.framework.test;

import by.epam.framework.page.CategoryPage;
import by.epam.framework.utils.SortingType;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


public class CategoryPageSortingTest extends CommonConditions {

    @DisplayName("decluttr-3")
    @Test
    public void categoryPageSortingTestAllTypes() {
        CategoryPage categoryPage = new CategoryPage(driver).openPage();
        assertEquals(SortingType.POPULARITY, categoryPage.getCurrentSortBy());
        categoryPage.setSortBy(SortingType.ALPHABETICAL);
        List<String> titlesOnPage = categoryPage.allProductsTitles();
        List<String> titlesOnPageSorted = new ArrayList<>(titlesOnPage)
                .stream()
                .sorted()
                .collect(Collectors.toList());
        assertEquals(titlesOnPage, titlesOnPageSorted);

        categoryPage.setSortBy(SortingType.ALPHABETICAL_REVERSED);
        titlesOnPage = categoryPage.allProductsTitles();
        titlesOnPageSorted = new ArrayList<>(titlesOnPage)
                .stream()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
        assertEquals(titlesOnPageSorted, titlesOnPage);

        categoryPage.setSortBy(SortingType.PRICE_ASC);
        var pricesOnPage = categoryPage.allProductsPrices();
        var pricesOnPageSorted = new ArrayList<>(pricesOnPage)
                .stream()
                .sorted()
                .collect(Collectors.toList());
        assertEquals(pricesOnPageSorted, pricesOnPage);

        categoryPage.setSortBy(SortingType.PRICE_DESC);
        pricesOnPage = categoryPage.allProductsPrices();
        pricesOnPageSorted = new ArrayList<>(pricesOnPage)
                .stream()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
        assertEquals(pricesOnPageSorted, pricesOnPage);
    }
}
