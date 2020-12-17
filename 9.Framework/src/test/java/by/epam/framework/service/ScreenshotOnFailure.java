package by.epam.framework.service;

import by.epam.framework.driver.DriverSingleton;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FileUtils;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@Log4j2
public class ScreenshotOnFailure extends TestWatcher {

    private final WebDriver driver;

    public ScreenshotOnFailure(WebDriver driver) {
        this.driver = driver;
    }

    @Override
    protected void failed(Throwable e, Description description) {
        log.info("Saving screenshot after failed test");
        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
        File scrFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
        File destFile = getDestinationFile(description.getMethodName());
        try {
            FileUtils.copyFile(scrFile, destFile);
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    @Override
    protected void finished(Description description) {
        DriverSingleton.closeDriver();
    }

    private File getDestinationFile(String failedMethodName) {
        String directory = "target/screenshots";
        String fileName = new Date().getTime() + failedMethodName + ".png";
        String absoluteFileName = directory + "/" + fileName;
        return new File(absoluteFileName);
    }
}