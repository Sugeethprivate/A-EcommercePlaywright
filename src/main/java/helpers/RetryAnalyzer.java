package helpers;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import utils.ConfigReader;

public class RetryAnalyzer implements IRetryAnalyzer {

    int counter = 0;
    int retryLimit = ConfigReader.getInt("testRetryCountOnFail");

    @Override
    public boolean retry(ITestResult result) {

        if(counter < retryLimit)
        {
            counter++;
            return true;
        }
        return false;
    }
}
