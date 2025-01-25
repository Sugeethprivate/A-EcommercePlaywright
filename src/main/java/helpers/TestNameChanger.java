package helpers;

import org.testng.ITestResult;

public class TestNameChanger {

    public static String changedTestName(ITestResult result) {
        String testName = result.getMethod().getQualifiedName();
        Object[] parameters = result.getParameters();
        if (parameters.length > 0) {
            testName = result.getMethod().getQualifiedName() + " [" + String.join(", ", toStringArrayWithoutLast(parameters)) + "]";
            result.setTestName(testName);
        }

        return testName;
    }

    private static String[] toStringArrayWithoutLast(Object[] parameters) {
        int length = parameters.length;
        String[] newArray = new String[length - 1];

        for (int i = 0; i < length - 1; i++) {
            newArray[i] = parameters[i].toString();
        }

        return newArray;
    }
}
