package base;

import com.microsoft.playwright.*;
import helpers.ExcelReportHelper;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utils.ConfigReader;

import java.lang.reflect.Method;


public class BaseTest {
    protected static ThreadLocal<Playwright> playwrightThreadLocal = new ThreadLocal<>();
    protected static ThreadLocal<Browser> browserThreadLocal = new ThreadLocal<>();
    protected static ThreadLocal<Page> pageThreadLocal = new ThreadLocal<>();
    private static boolean isHeadLess = ConfigReader.getBoolean("headless");
    private static String browserName = ConfigReader.get("browser");
    private static String proxyUrl = ConfigReader.get("proxy");

    @BeforeMethod
    public void setup() {
        Playwright playwright = Playwright.create(); // Initialize Playwright
        Browser browser = null;
        if (browserName.equals("chrome")) {
            browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(isHeadLess));
        } else if (browserName.equals("firefox")) {
            browser = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(isHeadLess));
        } else if (browserName.equals("safari")) {
            browser = playwright.webkit().launch(new BrowserType.LaunchOptions().setHeadless(isHeadLess));
        } else if (browserName.equals("msedge")) {
            browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(isHeadLess).setChannel(browserName));
        }
        Page page = browser.newPage();
        playwrightThreadLocal.set(playwright);
        browserThreadLocal.set(browser);
        pageThreadLocal.set(page);
    }

    public static Page getPage() {
        return pageThreadLocal.get();
    }

    @AfterMethod
    public void tearDown() {
        if (browserThreadLocal.get() != null) browserThreadLocal.get().close();
        if (playwrightThreadLocal.get() != null) playwrightThreadLocal.get().close();
    }

    @BeforeSuite
    public void setupExcelReport() {
        ExcelReportHelper.createExcelReport("ValidationResults");
    }

    @AfterSuite
    public void tearDownExcelReport() {
        ExcelReportHelper.closeExcelReport();
    }

    @AfterMethod
    public void logTestResultToExcel(Method method, ITestResult result) {
        String testCaseId = "TC-" + result.getMethod().getMethodName().toUpperCase();
        String methodName = method.getName();
        String status = result.isSuccess() ? "PASS" : "FAIL";
        ExcelReportHelper.updateExcelReport(testCaseId, methodName, status);
    }

}
