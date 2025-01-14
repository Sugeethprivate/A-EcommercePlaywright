package tests;

import base.BaseTest;
import dataproviders.ExcelDataProvider;
import org.testng.annotations.*;
import pages.LoginPage;


import static constants.TestGroupConstants.REGRESSION;
import static constants.TestGroupConstants.SANITY;
import static constants.CodeAuthors.HASHIRAMA;
import static constants.CodeAuthors.TOBIRAMA;
import static org.assertj.core.api.Assertions.assertThat;


public class LoginTest extends BaseTest {

    @Test(dataProvider = "singleLoginData", dataProviderClass = ExcelDataProvider.class,groups={SANITY,REGRESSION,HASHIRAMA})
    public void testValidLogin(String testcaseid, String password, String description, String username) {
        LoginPage loginPage = new LoginPage(getPage());
        loginPage.navigateToApp();
        loginPage.login(username, password);
        assertThat(loginPage.getPageTitle()).as("Page Title not Matching").isEqualTo("Swag Labs");
    }

    @Test(dataProvider = "singleLoginData", dataProviderClass = ExcelDataProvider.class, groups={REGRESSION,TOBIRAMA})
    public void testValidLogin2(String testcaseid, String password, String description, String username) {
        LoginPage loginPage = new LoginPage(getPage());
        loginPage.navigateToApp();
        loginPage.login(username, password);
        assertThat(loginPage.getPageTitle()).as("Page Title not Matching").isEqualTo("Swag Labs");
    }

}
