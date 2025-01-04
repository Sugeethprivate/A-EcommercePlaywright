package dataproviders;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.testng.annotations.DataProvider;
import utils.ExcelUtility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ExcelDataProvider {
    private static final String EXCEL_FILE_PATH = "C:/Users/HP/eclipse-workspace/EcommercePlaywright-Java/src/test/resources/testdata/Testdata.xlsx"; // Update the path
    private static final String MULTI_USER_DATA = "Multi User Credentials";
    private static final String SINGLE_USER_DATA = "Single User Credentials";

    @DataProvider(name = "multipleLoginData")
    public Iterator<Object[]> getMultipleLoginData() throws InvalidFormatException, IOException {
        ExcelUtility excel = new ExcelUtility(EXCEL_FILE_PATH, MULTI_USER_DATA);
        List<Map<String, String>> testData = excel.getAllRowsAsMap();
        excel.close();
        List<Object[]> dataProviderData = new ArrayList<>();
        for (Map<String, String> row : testData) {
            dataProviderData.add(row.values().toArray());
        }
        return dataProviderData.iterator();
    }

    @DataProvider(name = "singleLoginData")
    public Iterator<Object[]> getSingleLoginData() throws InvalidFormatException, IOException {
        ExcelUtility excel = new ExcelUtility(EXCEL_FILE_PATH, SINGLE_USER_DATA);
        List<Map<String, String>> testData = excel.getAllRowsAsMap();
        excel.close();
        List<Object[]> dataProviderData = new ArrayList<>();
        for (Map<String, String> row : testData) {
            dataProviderData.add(row.values().toArray());
        }
        return dataProviderData.iterator();
    }
}
