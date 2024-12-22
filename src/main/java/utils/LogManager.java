package utils;

import com.aventstack.extentreports.Status;
import helpers.ScreenshotHelper;
import listeners.ExtentReportListener;
import org.apache.logging.log4j.Logger;

public class LogManager {

    // Create a logger instance for logging to the console
    private static final Logger logger = org.apache.logging.log4j.LogManager.getLogger(LogManager.class);

    // for only Console logging
    public static void logConsole(String message) {
        logToConsole("INFO", message);

    }

    // Log Info
    public static void logInfo(String message) {
        logToExtentReport(Status.INFO, message);
        logToConsole("INFO", message);
    }

    // Log Pass
    public static void logPass(String message) {
        logToExtentReport(Status.PASS, message);
        logToConsole("PASS", message);
        ScreenshotHelper.screenshotPass();
    }

    // Log Fail
    public static void logFail(String message) {
        logToExtentReport(Status.FAIL, message);
        logToConsole("FAIL", message);
        ScreenshotHelper.screenshotFail();
    }

    // Log Skip
    public static void logSkip(String message) {
        logToExtentReport(Status.SKIP, message);
        logToConsole("SKIP", message);
    }

    // Log to Extent Report
    private static void logToExtentReport(Status status, String message) {
        ExtentReportListener.getTest().get().log(status, message);
    }

    // Log to Console using log4j
    private static void logToConsole(String status, String message) {

        String logMessage = message;

        switch (status) {
            case "INFO":
                logger.info(logMessage);
                break;
            case "PASS":
                logger.info(logMessage);
                break;
            case "FAIL":
                logger.error(logMessage);
                break;
            case "SKIP":
                logger.warn(logMessage);
                break;
            default:
                logger.info(logMessage);
        }
    }
}
