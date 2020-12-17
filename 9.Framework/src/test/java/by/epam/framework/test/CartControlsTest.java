package by.epam.framework.test;

import by.epam.framework.page.CartPage;
import by.epam.framework.page.ProductPage;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

@Slf4j
public class CartControlsTest extends CommonConditions {

    static final String DEFAULT_CATEGORY_PAGE_URL = "https://www.decluttr.com/us/store/category/";

    @DisplayName("decluttr-5")
    @Test
    public void cartControlsTest() {
        CartPage cartPage = new ProductPage(driver)
                .addAllProductsFromPropertiesToCart()
                .clickCheckoutButton();
        Double inCartTotal = cartPage.inCartTotal();
        Double firstProductPrice = cartPage.getPriceOfProductWithIndex(0);
        cartPage.setAmountOfProductAtIndex(0, 2);
        assertEquals(inCartTotal + firstProductPrice, cartPage.inCartTotal(), 0.01);

        inCartTotal = cartPage.inCartTotal();
        cartPage.setAmountOfProductAtIndex(0, 1);
        assertEquals(inCartTotal - firstProductPrice, cartPage.inCartTotal(), 0.01);

        Integer differentItemsCount = cartPage.countDifferentItemsInCart();
        cartPage.setAmountOfProductAtIndex(0, 0);
        assertEquals(differentItemsCount - 1, (int) cartPage.countDifferentItemsInCart());

        while (!cartPage.isCartEmpty()) {
            cartPage.clickRemoveButtonOfProductWithIndex(0);
        }
        assertTrue(cartPage.isCartEmpty());

        cartPage.clickContinueShoppingButton();
        assertEquals(DEFAULT_CATEGORY_PAGE_URL, driver.getCurrentUrl());
    }
}
