package by.epam.framework.test;

import by.epam.framework.page.CartPage;
import by.epam.framework.page.ProductPage;
import org.junit.jupiter.api.Test;

public class CartControlsTest extends CommonConditions {
    @Test
    void cartControlsTest() {
        CartPage cartPage = new ProductPage(driver)
                .addAllProductsFromPropertiesToCart()
                .clickCheckoutButton();
        Double inCartTotal = cartPage.inCartTotal();
        Double firstProductPrice = cartPage.getPriceOfProductWithIndex(0);
        cartPage.setAmountOfProductAtIndex(0, 2);
        assertEquals(inCartTotal + firstProductPrice, cartPage.inCartTotal(), 0.01);
        inCartTotal = cartPage.inCartTotal();
        cartPage.setAmountOfProductAtIndex(0, 1);
        assertEquals(inCartTotal - firstProductPrice, cartPage.inCartTotal());
        inCartTotal = cartPage.inCartTotal();
        cartPage.setAmountOfProductAtIndex(0, 0);
        assertEquals(inCartTotal - firstProductPrice, cartPage.inCartTotal());
    }
}
