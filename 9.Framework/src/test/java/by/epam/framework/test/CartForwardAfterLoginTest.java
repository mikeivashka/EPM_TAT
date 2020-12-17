package by.epam.framework.test;

import by.epam.framework.page.CartPage;
import by.epam.framework.page.CheckOutPage;
import by.epam.framework.page.ProductPage;
import org.junit.After;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;


public class CartForwardAfterLoginTest extends CommonConditions {
    @After
    public void cleanUp() {
        clearUserCart();
    }

    @DisplayName("decluttr-7")
    @Test
    public void cartForwardAfterLoginTest() {
        CartPage cartPageBeforeAuth = new ProductPage(driver)
                .addAllProductsFromPropertiesToCart()
                .clickCheckoutButton();
        Double inCartTotal = cartPageBeforeAuth.inCartTotal();
        Integer differentItemsCount = cartPageBeforeAuth.countDifferentItemsInCart();

        CheckOutPage checkOutPage = cartPageBeforeAuth
                .checkoutUnAuthorized()
                .logInUserFromProperties();
        assertEquals(inCartTotal, checkOutPage.totalToPay(), 0.01);
    }
}
