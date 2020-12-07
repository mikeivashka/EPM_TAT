package by.epam.decluttr;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.LinkedList;
import java.util.List;

class MultipleCartAdditionNotAuthorizedTest extends Assertions {

    public static final String FIRST_PRODUCT_PAGE = "https://www.decluttr.com/us/store/products/lg-v35-thinq-64gb-aurora-black-at-t-8f64d689-6d54-4ee0-9548-4aaac66117b0";
    public static final String SECOND_PRODUCT_PAGE = "https://www.decluttr.com/us/store/products/lg-g7-thinq-64gb-moroccan-blue-unlocked";
    private static WebDriver driver;

    @BeforeAll
    static void setUp() {
        String currentDir = System.getProperty("user.dir");
        System.setProperty("webdriver.chrome.driver", currentDir + "\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(40));
    }

    @AfterAll
    public static void tearDown() {
        driver.quit();
    }

    private static WebElement waitForElementLocatedBy(WebDriver driver, By by) {
        return new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions
                        .visibilityOfElementLocated(by));
    }

    @Test
    void addTwoProductsAndCheckoutTest() {
        driver.get(FIRST_PRODUCT_PAGE);
        WebElement addToCartButton = driver.findElement(By.name("button"));
        Double firstProductPrice = Double.parseDouble(
                driver
                        .findElement(By.id("variant-price"))
                        .getText()
                        .substring(1)
                        .replace(",", "")
        );
        assertTrue(addToCartButton.isDisplayed());
        addToCartButton.click();

        WebElement modalBody = waitForElementLocatedBy(driver, By.className("modal-body"));
        assertTrue(modalBody.isDisplayed());

        Double inCartTotalWithFirstProduct = Double.parseDouble(
                driver
                        .findElement(By.xpath("//h3[@class='mbottom total-display']/span[2]"))
                        .getText()
                        .transform(s ->
                                s.replaceAll(",", "").substring(1, s.length() - 4))
        );
        assertEquals(firstProductPrice, inCartTotalWithFirstProduct);

        WebElement continueShoppingButton = driver.findElement(By.linkText("Continue Shopping"));
        continueShoppingButton.click();
        assertFalse(modalBody.isDisplayed());

        new WebDriverWait(driver, Duration.ofSeconds(45))
                .until(ExpectedConditions
                        .textToBePresentInElementLocated(By
                                .xpath("//span[contains(@class, 'stat')]"), inCartTotalWithFirstProduct.toString()
                        )
                );
        assertEquals(firstProductPrice, inCartTotalWithFirstProduct);

        WebElement cartButton = driver.findElement(By.partialLinkText("CART"));
        assertTrue(cartButton.getText().endsWith("(1)"));

        driver.get(SECOND_PRODUCT_PAGE);
        Double secondProductPrice = Double.parseDouble(
                driver
                        .findElement(By.id("variant-price"))
                        .getText()
                        .substring(1)
                        .replace(",", "")
        );

        addToCartButton = driver.findElement(By.name("button"));
        addToCartButton.click();

        modalBody = waitForElementLocatedBy(driver, By.className("modal-body"));
        assertTrue(modalBody.isDisplayed());

        Double inCartTotalWithBothProducts = Double.parseDouble(
                driver
                        .findElement(By.xpath("//h3[@class='mbottom total-display']/span[2]"))
                        .getText()
                        .transform(s ->
                                s.replaceAll(",", "").substring(1, s.length() - 3))
        );
        assertEquals(firstProductPrice + secondProductPrice, inCartTotalWithBothProducts);

        WebElement checkoutButton = driver.findElement(By.linkText("Checkout Now"));
        checkoutButton.click();
        assertEquals("https://www.decluttr.com/us/store/cart", driver.getCurrentUrl());

        List<WebElement> cartItemsLinks = driver.findElements(By.xpath("//div[@class='basket__item']/*/*/*/strong/a"));
        List<String> cartItemsLinkStrings = new LinkedList<>();
        cartItemsLinks.forEach(i -> cartItemsLinkStrings.add(i.getAttribute("href")));
        assertArrayEquals(new String[]{FIRST_PRODUCT_PAGE, SECOND_PRODUCT_PAGE}, cartItemsLinkStrings.toArray());

        WebElement checkoutSecurelyButton = driver.findElement(By.id("checkout-btn2"));
        checkoutSecurelyButton.click();
        assertTrue(driver.getCurrentUrl().startsWith("https://www.decluttr.com/login"));
    }
}