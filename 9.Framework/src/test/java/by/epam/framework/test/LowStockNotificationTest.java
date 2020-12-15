package by.epam.framework.test;

import by.epam.framework.page.CartPage;
import by.epam.framework.page.ProductPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LowStockNotificationTest extends CommonConditions {
    @DisplayName("decluttr-6")
    @Test
    void lowStockNotificationTest() {
        CartPage lowStockProductPage = new ProductPage(driver)
                .addLowStockProductFromPropertiesToCart()
                .clickCheckoutButton();
    }
}
