package by.epam.framework.test;


import by.epam.framework.page.ShopMainPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MainPageFeedBackTest extends CommonConditions {

    @DisplayName("decluttr-1")
    @Test
    void specificFeedbackMainPageTest() {
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
        assertEquals(failureMessage, "This field is required");
        feedbackForm
                .putTextIntoFeedbackField("Sample text")
                .clickSubmitFeedbackButton();
        assertTrue(feedbackForm.hasFeedbackSuccessNotification());
    }
}
