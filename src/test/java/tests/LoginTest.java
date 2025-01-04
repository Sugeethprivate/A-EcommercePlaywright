package tests;

import base.BaseTest;
import dataproviders.ExcelDataProvider;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;

public class LoginTest extends BaseTest {

    @Test(dataProvider = "singleLoginData", dataProviderClass = ExcelDataProvider.class,groups = "sanity")
    public void testValidLogin(String testcaseid, String password, String description, String username) {
        LoginPage loginPage = new LoginPage(getPage());
        loginPage.navigateToApp();
        loginPage.login(username, password);
        Assert.assertEquals(loginPage.getPageTitle(), "Swag Labs");
    }

}
