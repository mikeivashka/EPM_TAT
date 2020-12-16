package by.epam.framework.page;


import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

@Slf4j
public class ShopMainPage extends AbstractPage {

    private final By specificFeedbackLocator = By.xpath("//div[@class= 'icon'][1]");

    private final By feedbackButtonLocator = By.xpath("//iframe[@title='Usabilla Feedback Button']");

    private final By iframeLocator = By.xpath("//iframe[@title='Usabilla Feedback Form Frame']");

    private final By smilesLocator = By.xpath("//ul[@class='rating']//li");

    private final By reasonForFeedbackLocator = By.name("Reason");

    private final By submitFeedbackButtonLocator = By.xpath("//button[@class='submit']");

    private final By feedbackTextAreaLocator = By.xpath("//textarea[@name='feedback']");

    private final By invalidInputNotificationLocator = By.xpath("//*[text()='This field is required']");

    private final By feedbackAcceptedNotificationLocator = By.xpath("//h2[text()='We got your feedback!']");

    private short chosenFeedback = -1;

    public ShopMainPage(RemoteWebDriver driver) {
        super(driver);
    }

    public ShopMainPage clickFeedbackButton() {
        WebElement iframe = new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions
                        .visibilityOfElementLocated(feedbackButtonLocator));
        iframe.click();
        return this;
    }

    public ShopMainPage clickSpecificFeedbackButton() {
        switchToFrameLocatedBy(iframeLocator);
        new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions
                        .presenceOfElementLocated(specificFeedbackLocator)).click();
        return this;
    }

    public int getSmilesCountOnFeedbackForm() {
        switchToFrameLocatedBy(iframeLocator);
        return new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(smilesLocator))
                .size();
    }

    public ShopMainPage chooseReasonForFeedbackWithIndex(int index) {
        Select select = new Select(
                new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                        .until(ExpectedConditions
                                .visibilityOfElementLocated(reasonForFeedbackLocator)));
        select.selectByIndex(index);
        chosenFeedback = (short) (index - 1);
        return this;
    }

    public ShopMainPage clickOnFeedbackSmileWithIndex(int index) {
        driver.findElements(smilesLocator).get(index).click();
        return this;
    }

    public ShopMainPage clickOnAnyElement() {
        new Actions(driver)
                .moveByOffset(200, 200)
                .click()
                .perform();
        return this;
    }

    public ShopMainPage clickSubmitFeedbackButton() {
        new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions
                        .presenceOfElementLocated(submitFeedbackButtonLocator)).click();
        return this;
    }

    public String getFailedFeedbackInputNotificationText() {
        return driver.findElement(invalidInputNotificationLocator).getText();
    }

    private void switchToFrameLocatedBy(By by) {
        driver.switchTo()
                .frame(
                        new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                                .until(ExpectedConditions
                                        .visibilityOfElementLocated(by))
                );
    }

    public ShopMainPage putTextIntoFeedbackField(String text) {
        new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions
                        .presenceOfAllElementsLocatedBy(feedbackTextAreaLocator))
                .get(chosenFeedback)
                .sendKeys(text);
        return this;
    }

    public boolean hasFeedbackSuccessNotification() {
        new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions
                        .visibilityOfElementLocated(feedbackAcceptedNotificationLocator)
                );
        return true;
    }

    @Override
    public ShopMainPage openPage() {
        driver.get(AbstractPage.BASIC_SHOP_URL);
        setRequiredCookies();
        return this;
    }
}
