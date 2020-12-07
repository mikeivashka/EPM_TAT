package by.epam.decluttr.test;

import by.epam.decluttr.page.CategoryPage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.List;

public class CategoryPagePriceFilterTest extends Assertions {
    private static ChromeDriver driver;

    @BeforeAll
    static void setUp() {
        String currentDir = System.getProperty("user.dir");
        System.setProperty("webdriver.chrome.driver", currentDir + "\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
    }

    @AfterAll
    public static void tearDown() {
        driver.quit();
    }

    @ParameterizedTest
    @CsvSource(value = {"400,800"})
    void categoryPagePriceFilterTest(int minPriceValue, int maxPriceValue){
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
