package by.epam.framework.test;

import by.epam.framework.page.CartPage;
import by.epam.framework.page.CheckOutPage;
import by.epam.framework.page.ProductPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CartForwardAfterLoginTest extends CommonConditions {
    @AfterEach
    public void cleanUp() {
        clearUserCart();
    }

    @DisplayName("decluttr-7")
    @Test
    void cartForwardAfterLoginTest() {
        CartPage cartPageBeforeAuth = new ProductPage(driver)
                .addAllProductsFromPropertiesToCart()
                .clickCheckoutButton();
        Double inCartTotal = cartPageBeforeAuth.inCartTotal();
        Integer differentItemsCount = cartPageBeforeAuth.countDifferentItemsInCart();

        CheckOutPage checkOutPage = (CheckOutPage) cartPageBeforeAuth
                .checkoutUnAuthorized()
                .logInUserFromProperties();
        assertEquals(inCartTotal, checkOutPage.totalToPay(), 0.01);
    }
}
