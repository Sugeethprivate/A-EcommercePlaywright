package dataproviders;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.testng.annotations.DataProvider;
import utils.ExcelUtility;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ExcelDataProvider {

    // Path to the Excel file and sheet name
    private static final String EXCEL_FILE_PATH = "C:/Users/HP/eclipse-workspace/EcommercePlaywright-Java/src/test/resources/testdata/Testdata.xlsx";  // Update the path as per your project structure
    private static final String SHEET_NAME = "Sheet1";  // Update the sheet name as per your requirement

    // DataProvider method that will provide data for test methods
    @SuppressWarnings("unchecked")
    @DataProvider(name = "excelData")
    public Iterator<Object[]> getTestData() throws InvalidFormatException, IOException {
        // Instantiate the ExcelUtility to fetch the test data
        ExcelUtility excel = new ExcelUtility(EXCEL_FILE_PATH, SHEET_NAME);

        // Get data from Excel sheet as a List of Maps
        List<Map<String, String>> testData = excel.getAllRowsAsMap();

        // Close the Excel utility
        excel.close();

        // Return the iterator
        return (Iterator<Object[]>) testData;
    }
}
