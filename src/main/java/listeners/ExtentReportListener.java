package listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import helpers.ScreenshotHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.ThreadContext;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import utils.ConfigReader;
import utils.LogManager;

public class ExtentReportListener implements ITestListener, ISuiteListener {

    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    private static ThreadLocal<String> suiteName = new ThreadLocal<>();
    private static ThreadLocal<String> testName = new ThreadLocal<>();
    // Fetching configurations from ConfigReader
    private static String testerName = ConfigReader.get("tester");
    private static String runEnvironment = ConfigReader.get("environment");
    private static String appName = ConfigReader.get("application");

    @Override
    public void onStart(ISuite suite) {
        // Setting up the report configuration
        suiteName.set(suite.getName());
        String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
        String reportPath = "./ExtentReports/ExtentReport_" + timestamp + ".html";
        ExtentSparkReporter htmlReporter = new ExtentSparkReporter(reportPath);
        htmlReporter.config().setTheme(Theme.DARK);
        htmlReporter.config().setDocumentTitle("Test Report");
        htmlReporter.config().setReportName("Test Execution Report");

        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);

        // Adding system information to the report
        extent.setSystemInfo("Environment", runEnvironment);
        extent.setSystemInfo("Tester", testerName);
        extent.setSystemInfo("Application", appName);
        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("Browser", ConfigReader.get("browser"));
    }

    @Override
    public void onFinish(ISuite suite) {
        // Flushing the reports after suite execution
        extent.flush();
    }

    @Override
    public void onTestStart(ITestResult result) {
        // Creating a new test in ExtentReport
        suiteName.set(result.getTestContext().getSuite().getName());
        testName.set(result.getMethod().getMethodName());
        ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName());
        test.set(extentTest);
        test.get().pass("Test started");
        LogManager.logConsole("Test Started...........");
        ThreadContext.put("suiteName", result.getTestContext().getSuite().getName());
        ThreadContext.put("testName", result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        // Logging the success in ExtentReport
        test.get().pass("Test passed");
        LogManager.logConsole("Test Success...........");
        ScreenshotHelper.screenshotPass();
        ThreadContext.clearAll();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        // Logging the failure and attaching the exception to ExtentReport
        test.get().fail(result.getThrowable());
        test.get().fail("Test failed");
        LogManager.logConsole("Test failure...........");
        ScreenshotHelper.screenshotFail();
        ThreadContext.clearAll();
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        // Logging the skipped test with a reason
        test.get().skip(result.getThrowable());
        test.get().skip("Test skipped");
        LogManager.logConsole("Test Skipped...........");
        ThreadContext.clearAll();
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // Not implemented
    }

    @Override
    public void onStart(ITestContext context) {
        // Not implemented
        suiteName.set(context.getSuite().getName());
        testName.set(context.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        // Not implemented
    }

    // Method to access the current thread's ExtentTest
    public static ThreadLocal<ExtentTest> getTest() {
        return test;
    }

    public static ThreadLocal<String> getSuiteName() {
        return suiteName;
    }

    public static ThreadLocal<String> getTestName() {
        return testName;
    }
}
