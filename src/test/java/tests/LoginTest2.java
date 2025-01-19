package tests;

import base.BaseTest;
import dataproviders.ExcelDataProvider;
import org.testng.annotations.Test;
import pages.LoginPage;


import static constants.TestGroupConstants.SMOKE;
import static org.assertj.core.api.Assertions.assertThat;


public class LoginTest2 extends BaseTest {

    @Test(dataProvider = "multipleLoginData", dataProviderClass = ExcelDataProvider.class, groups={SMOKE})
    public void testValidLogin3(String testcaseid, String password, String description, String username) {
        LoginPage loginPage = new LoginPage(getPage());
        loginPage.navigateToApp();
        loginPage.login(username, password);
        assertThat(loginPage.getPageTitle()).as("Page Title not Matching").isEqualTo("Swag Labs");
    }

}
