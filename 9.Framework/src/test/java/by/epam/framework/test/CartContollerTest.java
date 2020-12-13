package by.epam.framework.test;

import by.epam.framework.page.CartPage;
import by.epam.framework.page.ProductPage;
import org.junit.jupiter.api.Test;

public class CartContollerTest extends CommonConditions {
    @Test
    void cartControllerTest() {
        CartPage cartPage = new ProductPage(driver)
                .addAllProductsFromPropertiesToCart()
                .clickCheckoutButton();
        Double priceOfFirstItem = cartPage.getPriceOfProductWithIndex(0);
        Integer quantityOfFirstItem = cartPage.getAmountOfProductWithIndex(0);
        cartPage.setAmountOfProductAtIndex(0, 2);

    }
}
