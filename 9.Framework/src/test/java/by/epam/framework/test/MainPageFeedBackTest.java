package by.epam.framework.test;


import by.epam.framework.page.ShopMainPage;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

public class MainPageFeedBackTest extends CommonConditions {

    @DisplayName("decluttr-1")
    @Test
    public void specificFeedbackMainPageTest() {
        ShopMainPage feedbackForm =
                new ShopMainPage(driver).openPage()
                        .clickFeedbackButton()
                        .clickSpecificFeedbackButton()
                        .clickOnAnyElement();
        int smilesOnFeedbackForm = feedbackForm.getSmilesCountOnFeedbackForm();
        assertEquals(5, smilesOnFeedbackForm);
        String failureMessage = feedbackForm
                .clickOnFeedbackSmileWithIndex(4)
                .chooseReasonForFeedbackWithIndex(4)
                .clickSubmitFeedbackButton()
                .getFailedFeedbackInputNotificationText();
        assertEquals("This field is required", failureMessage);
        feedbackForm
                .putTextIntoFeedbackField("Sample text")
                .clickSubmitFeedbackButton();
        assertTrue(feedbackForm.hasFeedbackSuccessNotification());
    }
}
