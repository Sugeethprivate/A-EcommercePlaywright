package tests;

import base.BaseTest;
import dataproviders.ExcelDataProvider;
import dataproviders.SauceDataProvider;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;

public class LoginTest extends BaseTest {
    @Test(dataProvider = "excelData", dataProviderClass = ExcelDataProvider.class)
    public void testValidLogin(String testcaseid, String username, String password) {
        LoginPage loginPage = new LoginPage(getPage());
        loginPage.navigateToApp();
        loginPage.login(username, password);
        Assert.assertEquals(loginPage.getPageTitle(), "Swag Labs");
    }

}
