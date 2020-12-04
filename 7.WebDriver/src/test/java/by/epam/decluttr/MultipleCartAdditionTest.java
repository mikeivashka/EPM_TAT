package by.epam.decluttr;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

class MultipleCartAdditionTest extends Assertions {

    public static final String FIRST_PRODUCT_PAGE = "https://www.decluttr.com/us/store/products/apple-iphone-x-256gb-space-grey-unlocked-64244e81-1188-4719-8c2f-b62505bea0e6";
    public static final String SECOND_PRODUCT_PAGE = "https://www.decluttr.com/us/store/products/samsung-galaxy-s20-ultra-5g-128gb-cosmic-black-unlocked-b951d712-a716-4961-ad1a-47257612a71f";
    private static WebDriver driver;

    @BeforeAll
    static void setUp() {
        //Setting driver location for Windows machines
        String currentDir = System.getProperty("user.dir");
        System.setProperty("webdriver.chrome.driver", currentDir + "\\chromedriver.exe");
        driver = new ChromeDriver();
        //Entering fullscreen mode
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @AfterAll
    public static void tearDown() {
        driver.quit();
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
        WebElement modalBody = driver.findElement(By.className("modal-body"));
        assertTrue(modalBody.isDisplayed());
        Double inCartTotalWithFirstProduct = Double.parseDouble(
                driver
                        .findElement(By.xpath("//h3[@class='mbottom total-display']/span[2]"))
                        .getText()
                        .transform(s ->
                                s.replaceAll(",", "").substring(1, s.length() - 3))
        );
        assertEquals(firstProductPrice, inCartTotalWithFirstProduct);

    }
}
