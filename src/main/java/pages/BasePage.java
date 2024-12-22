package pages;

import com.microsoft.playwright.Page;
import utils.ConfigReader;
import utils.LogManager;

public class BasePage {
    protected Page page;
    private static String appUrl = ConfigReader.get("baseUrl");

    public BasePage(Page page) {
        this.page = page;

    }

    public String getPageTitle() {
        return page.title();
    }

    public void navigateToApp() {
        try {
            page.navigate(appUrl);
            LogManager.logPass("Navigated to URL: " + appUrl);
        } catch (Exception e) {
            LogManager.logFail("Failed to navigate to URL: " + appUrl + " - " + e.getMessage());
        }
    }

    public void reloadPage() {
        try {
            page.reload();
            LogManager.logPass("Page reloaded successfully");
        } catch (Exception e) {
            LogManager.logFail("Failed to reload page - " + e.getMessage());
        }
    }

    public void goBack() {
        try {
            page.goBack();
            LogManager.logPass("Navigated back successfully");
        } catch (Exception e) {
            LogManager.logFail("Failed to go back - " + e.getMessage());
        }
    }

    public void goForward() {
        try {
            page.goForward();
            LogManager.logPass("Navigated forward successfully");
        } catch (Exception e) {
            LogManager.logFail("Failed to go forward - " + e.getMessage());
        }
    }

    // Element Interaction Methods
    public void clickElement(String selector) {
        try {
            page.click(selector);
            LogManager.logPass("Clicked on element: " + selector);
        } catch (Exception e) {
            LogManager.logFail("Failed to click on element: " + selector + " - " + e.getMessage());
        }
    }

    public void typeText(String selector, String text) {
        try {
            page.fill(selector, text);
            LogManager.logPass("Typed " + text + " into element: " + selector);
        } catch (Exception e) {
            LogManager.logFail("Failed to type text into element: " + selector + " - " + e.getMessage());
        }
    }

    public void checkCheckbox(String selector) {
        try {
            page.check(selector);
            LogManager.logPass("Checked checkbox: " + selector);
        } catch (Exception e) {
            LogManager.logFail("Failed to check checkbox: " + selector + " - " + e.getMessage());
        }
    }

    public void uncheckCheckbox(String selector) {
        try {
            page.uncheck(selector);
            LogManager.logPass("Unchecked checkbox: " + selector);
        } catch (Exception e) {
            LogManager.logFail("Failed to uncheck checkbox: " + selector + " - " + e.getMessage());
        }
    }

    public void selectOption(String selector, String value) {
        try {
            page.selectOption(selector, value);
            LogManager.logPass("Selected option: " + value + " from dropdown: " + selector);
        } catch (Exception e) {
            LogManager.logFail("Failed to select option: " + value + " from dropdown: " + selector + " - " + e.getMessage());
        }
    }

    public void hoverOverElement(String selector) {
        try {
            page.hover(selector);
            LogManager.logPass("Hovered over element: " + selector);
        } catch (Exception e) {
            LogManager.logFail("Failed to hover over element: " + selector + " - " + e.getMessage());
        }
    }

    // Waiting Methods
    public void waitForElement(String selector) {
        try {
            page.waitForSelector(selector);
            LogManager.logPass("Element is visible: " + selector);
        } catch (Exception e) {
            LogManager.logFail("Failed to wait for element: " + selector + " - " + e.getMessage());
        }
    }

    public void waitForTimeout(long milliseconds) {
        try {
            page.waitForTimeout(milliseconds);
            LogManager.logPass("Waited for " + milliseconds + " milliseconds");
        } catch (Exception e) {
            LogManager.logFail("Failed to wait for timeout - " + e.getMessage());
        }
    }

    public String getTextContent(String selector) {
        try {
            String text = page.textContent(selector);
            LogManager.logPass("Successfully fetched text content of element: '" + selector + "'. Text: " + text);
            return text;
        } catch (Exception e) {
            LogManager.logFail("Failed to fetch text content of element: '" + selector + "' - Error: " + e.getMessage());
            return null;
        }
    }

}
