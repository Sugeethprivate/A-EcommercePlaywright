package tests;

import base.BaseTest;
import dataproviders.ExcelDataProvider;
import helpers.ExcelReportHelper;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.LoginPage;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;


public class LoginTest extends BaseTest {

    @BeforeClass
    public void setupExcelReport() {
        ExcelReportHelper.createExcelReport("LoginTestClassResults");
    }

    @Test(dataProvider = "singleLoginData", dataProviderClass = ExcelDataProvider.class)
    public void testValidLogin(String testcaseid, String password, String description, String username) {
        LoginPage loginPage = new LoginPage(getPage());
        loginPage.navigateToApp();
        loginPage.login(username, password);
        assertThat(loginPage.getPageTitle()).as("Page Title not Matching").isEqualTo("Swag Labs");
    }

    @AfterMethod
    public void logTestResultToExcel(Method method, ITestResult result) {
        System.out.println(result);
        String testCaseId = "TC-" + result.getMethod().getMethodName().toUpperCase();
        String methodName = method.getName();
        String status = result.isSuccess() ? "PASS" : "FAIL";
        ExcelReportHelper.updateExcelReport(testCaseId, methodName, status);
    }

    @AfterClass
    public void tearDownExcelReport() {
        ExcelReportHelper.closeExcelReport();
    }

}
