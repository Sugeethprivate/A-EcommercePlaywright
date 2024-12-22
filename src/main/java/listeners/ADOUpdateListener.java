package listeners;

import helpers.ADOHelper;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ADOUpdateListener implements ITestListener {

    private final String testRunId = "11";

    @Override
    public void onTestSuccess(ITestResult result) {
        String testCaseId = (String) result.getParameters()[0];
        String testResultId = ADOHelper.createTestResult(testRunId, testCaseId);
        if (testResultId != null) {
            ADOHelper.updateTestResult(testRunId, testResultId, "Passed");
            ADOHelper.updateTestCaseState(testCaseId, "Closed");
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String testCaseId = (String) result.getParameters()[0];
        String testResultId = ADOHelper.createTestResult(testRunId, testCaseId);
        if (testResultId != null) {
            ADOHelper.updateTestResult(testRunId, testResultId, "Failed");
            ADOHelper.updateTestCaseState(testCaseId, "Active");
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        String testCaseId = (String) result.getParameters()[0];
        String testResultId = ADOHelper.createTestResult(testRunId, testCaseId);
        if (testResultId != null) {
            ADOHelper.updateTestResult(testRunId, testResultId, "NotExecuted");
            ADOHelper.updateTestCaseState(testCaseId, "Ready");
        }
    }

    @Override
    public void onStart(ITestContext context) {
    }

    @Override
    public void onFinish(ITestContext context) {
    }
}
