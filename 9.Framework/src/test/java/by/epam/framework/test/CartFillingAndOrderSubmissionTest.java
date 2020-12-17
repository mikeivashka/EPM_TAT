package by.epam.framework.test;

import by.epam.framework.page.LoginPage;
import by.epam.framework.page.ProductPage;
import by.epam.framework.service.TestDataReader;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

public class CartFillingAndOrderSubmissionTest extends CommonConditions {
    @DisplayName("decluttr-4")
    @Test
    public void fillInCartAndSubmitOrderTest() {
        ProductPage productPage = new ProductPage(driver)
                .openPage(TestDataReader.getTestData("testdata.product.page.common"));
        productPage.clickAddToCartButton()
                .clickContinueShoppingButton();
        double firstProductPrice = productPage.getCurrentPrice();
        assertEquals(firstProductPrice, productPage.getInCartTotal(), 0.01);

        productPage = new ProductPage(driver).openPage(TestDataReader.getTestData("testdata.product.pages.1"));
        double secondProductPrice = productPage.getCurrentPrice();
        var cartPage = productPage.clickAddToCartButton()
                .clickCheckoutButton();
        double inCartTotal = cartPage.inCartTotal();
        assertEquals(firstProductPrice + secondProductPrice, inCartTotal, 0.01);

        cartPage.checkoutUnAuthorized();
        assertTrue(driver.getCurrentUrl().contains(LoginPage.LOGIN_PAGE_URL));
    }
}
