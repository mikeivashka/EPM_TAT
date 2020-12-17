package by.epam.framework.test;

import by.epam.framework.page.CartPage;
import by.epam.framework.page.LoginPage;
import by.epam.framework.page.ProductPage;
import lombok.extern.java.Log;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

@Log
public class LowStockNotificationTest extends CommonConditions {

    @DisplayName("decluttr-6")
    @Test
    public void lowStockNotificationTest() {
        CartPage cartPage = new ProductPage(driver)
                .addLowStockProductFromPropertiesToCart()
                .clickCheckoutButton();
        Integer inStock = cartPage.getInStockForProductWithIndex(0);
        cartPage.setAmountOfProductAtIndex(0, inStock);
        assertEquals(inStock, cartPage.getAmountOfProductWithIndex(0));

        String lowStockAlert = cartPage
                .setAmountOfProductAtIndex(0, inStock + 1)
                .lineItemsQuantityWarning();
        assertTrue(lowStockAlert.contains("We’ve updated your basket accordingly"));

        cartPage.checkoutUnAuthorized();
        assertTrue(driver.getCurrentUrl().contains(LoginPage.LOGIN_PAGE_URL));
    }
}
