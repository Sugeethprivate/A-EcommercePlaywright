package pages;

import com.microsoft.playwright.Page;

public class LoginPage extends BasePage {
    private final String usernameSelector = "//input[@id='user-name']";
    private final String passwordSelector = "//input[@id='password']";
    private final String loginButtonSelector = "//input[@id='login-button']";
    private final String errorMessageSelector = "#error";

    public LoginPage(Page page) {
        super(page);
    }

    public void login(String username, String password) {
        typeText(usernameSelector, username);
        typeText(passwordSelector, password);
        clickElement(loginButtonSelector);
    }

    public String getErrorMessage() {
        return page.textContent(errorMessageSelector);
    }
}
