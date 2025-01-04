package helpers;

import base.BaseTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.microsoft.playwright.Page;
import listeners.ExtentReportListener;
import utils.ConfigReader;
import utils.LogManager;

import java.util.Base64;

public class ScreenshotHelper {

    public static void captureScreenshotIfEnabled(Page page, String status) {
        String screenshotOnPass = ConfigReader.get("takeScreenshotOnPass").toLowerCase();
        String screenshotOnFail = ConfigReader.get("takeScreenshotOnFail").toLowerCase();

        boolean shouldCapture = (status.equalsIgnoreCase("pass") && screenshotOnPass.equals("yes")) || (status.equalsIgnoreCase("fail") && screenshotOnFail.equals("yes"));

        if (shouldCapture) {
            try {
                byte[] screenshotBytes = page.screenshot(new Page.ScreenshotOptions().setFullPage(true));
                String base64Screenshot = Base64.getEncoder().encodeToString(screenshotBytes);

                // Log screenshot in the report
                String message = status.equalsIgnoreCase("pass") ? "Test passed. Screenshot captured." : "Test failed. Screenshot captured.";
                ExtentReportListener.getTest().get().log(com.aventstack.extentreports.Status.valueOf(status.toUpperCase()), message, MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());
            } catch (Exception e) {
                ExtentReportListener.getTest().get().info("Failed to capture screenshot: " + e.getMessage());
            }
        }
    }

    public static void screenshotPass() {
        Page page = BaseTest.getPage();
        if (page != null) {
            ScreenshotHelper.captureScreenshotIfEnabled(page, "pass");
        } else {
            LogManager.logInfo("Page object is null. Skipping screenshot.");
        }

    }

    public static void screenshotFail() {
        Page page = BaseTest.getPage();
        if (page != null) {
            ScreenshotHelper.captureScreenshotIfEnabled(page, "fail");
        } else {
            LogManager.logInfo("Page object is null. Skipping screenshot.");
        }

    }

    public static void screenshotSkip() {
        Page page = BaseTest.getPage();
        if (page != null) {
            ScreenshotHelper.captureScreenshotIfEnabled(page, "fail");
        } else {
            LogManager.logInfo("Page object is null. Skipping screenshot.");
        }

    }


}
