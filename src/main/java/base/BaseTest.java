package base;

import com.microsoft.playwright.*;
import helpers.ExcelReportHelper;
import helpers.TestNameChanger;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utils.ConfigReader;

import java.lang.reflect.Method;
import java.nio.file.Paths;


public class BaseTest {
    protected static ThreadLocal<Playwright> playwrightThreadLocal = new ThreadLocal<>();
    protected static ThreadLocal<Browser> browserThreadLocal = new ThreadLocal<>();
    protected static ThreadLocal<Page> pageThreadLocal = new ThreadLocal<>();
    private static boolean isHeadLess = ConfigReader.getBoolean("headless");
    private static String browserName = ConfigReader.get("browser");
    private static String proxyUrl = ConfigReader.get("proxy");
    private static String setTrace = ConfigReader.get("traceViewer");

    @BeforeMethod(alwaysRun = true)
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

    public static Playwright getPlaywright() {
        return playwrightThreadLocal.get();
    }

    public static Browser getBrowser() {
        return browserThreadLocal.get();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (browserThreadLocal.get() != null) browserThreadLocal.get().close();
        if (playwrightThreadLocal.get() != null) playwrightThreadLocal.get().close();
    }

    @BeforeSuite(alwaysRun = true)
    public void setupExcelReport() {
        ExcelReportHelper.createExcelReport("ValidationResults");
    }

    @AfterSuite(alwaysRun = true)
    public void tearDownExcelReport() {
        ExcelReportHelper.closeExcelReport();
    }

    @AfterMethod(alwaysRun = true)
    public void logTestResultToExcel(Method method, ITestResult result) {
        String testCaseId = "TC-" + TestNameChanger.changedTestName(result).toUpperCase();
        String methodName = method.getName();
        String status;
        if (result.getStatus() == ITestResult.SUCCESS) {
            status = "PASS";
        } else if (result.getStatus() == ITestResult.FAILURE) {
            status = "FAIL";
        } else if (result.getStatus() == ITestResult.SKIP) {
            status = "SKIP";
        } else {
            status = "UNKNOWN";
        }
        ExcelReportHelper.updateExcelReport(testCaseId, methodName, status);
    }

    @BeforeMethod(alwaysRun = true)
    public void startTrace(){
        if (setTrace.equals("on")) {
            getPage().context().tracing().start(new Tracing.StartOptions()
                    .setScreenshots(true)
                    .setSnapshots(true)
                    .setSources(true));
        }
    }

    @AfterMethod(alwaysRun = true)
    public void stopTrace(ITestResult result){
        if (setTrace.equals("on")) {
            String tracePath = "traces/" + TestNameChanger.changedTestName(result) + ".zip";
            try {
                getPage().context().tracing().stop(new Tracing.StopOptions().setPath(Paths.get(tracePath)));
            } catch (Exception e) {
                System.out.println("Could not attach trace: " + e.getMessage());
            }
        }
    }
}
