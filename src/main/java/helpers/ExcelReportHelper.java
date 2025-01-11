package helpers;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelReportHelper {
    private static Workbook workbook;
    private static Sheet sheet;
    private static final String FILE_PATH = "C:/Users/HP/eclipse-workspace/EcommercePlaywright-Java/ExcelReports/EcommerceTestResults.xlsx";
    private static int rowCount = 1;

    public static synchronized void createExcelReport(String sheetName) {
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet(sheetName);

        // Create header row
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Test Case ID");
        header.createCell(1).setCellValue("Method Name");
        header.createCell(2).setCellValue("Status");
        saveToExcelReport();
    }

    public static synchronized void updateExcelReport(String testCaseId, String methodName, String status) {
        Row row = sheet.createRow(rowCount++);
        row.createCell(0).setCellValue(testCaseId);
        row.createCell(1).setCellValue(methodName);
        row.createCell(2).setCellValue(status);
        saveToExcelReport();
    }

    private static synchronized void saveToExcelReport() {
        try (FileOutputStream fileOut = new FileOutputStream(FILE_PATH)) {
            workbook.write(fileOut);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized void closeExcelReport() {
        try {
            if (workbook != null) {
                workbook.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
