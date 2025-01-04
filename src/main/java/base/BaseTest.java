package base;

import com.microsoft.playwright.*;
import utils.ConfigReader;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;


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
}
