package by.epam.framework.test;

import by.epam.framework.driver.DriverSingleton;
import by.epam.framework.page.*;
import by.epam.framework.service.TestDataReader;
import org.junit.AfterClass;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;


public class CartForwardAfterLoginTest extends CommonConditions {

    @AfterClass
    public static void cleanUp() {
        clearUserCart();
        DriverSingleton.getDriver().quit();
    }

    @DisplayName("decluttr-7")
    @Test
    public void cartForwardAfterLoginTest() {
        CartPage cartPageBeforeAuth = new ProductPage(driver)
                .addAllProductsFromPropertiesToCart()
                .clickCheckoutButton();
        Double inCartTotal = cartPageBeforeAuth.inCartTotal();
        Integer differentItemsCount = cartPageBeforeAuth.countDifferentItemsInCart();
        cartPageBeforeAuth
                .checkoutUnAuthorized()
                .logInUserFromProperties();
        CheckOutPage checkOutPage = new CheckOutPage(driver);

        assertEquals(inCartTotal, checkOutPage.totalToPay(), 0.01);
    }

    @DisplayName("decluttr-8")
    @Test
    public void cartForwardToNotEmptyAfterLoginTest() {
        new LoginPage(driver).openPage().logInUserFromProperties();
        Integer inCartBeforeTest = new CartPage(driver).openPage().countTotalItemsInCart();
        new ProfilePage(driver).openPage().logOut();

        String addedProductUrl = TestDataReader.getTestData("testdata.product.page.common");
        new ProductPage(driver)
                .openPage(addedProductUrl)
                .clickAddToCartButton()
                .clickContinueShoppingButton();
        new LoginPage(driver).openPage().logInUserFromProperties();
        CartPage cartPage = new CartPage(driver).openPage();
        assertEquals(inCartBeforeTest + 1, (int) cartPage.countTotalItemsInCart());
    }
}
