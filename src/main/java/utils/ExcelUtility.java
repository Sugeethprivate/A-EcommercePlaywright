package utils;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class ExcelUtility {

    private Workbook workbook;
    private Sheet sheet;
    private String excelFilePath;

    public ExcelUtility(String excelFilePath, String sheetName) throws IOException, InvalidFormatException {
        this.excelFilePath = excelFilePath;

        // Load the workbook and sheet
        try (FileInputStream file = new FileInputStream(new File(excelFilePath))) {
            this.workbook = new XSSFWorkbook(file);
            this.sheet = workbook.getSheet(sheetName);

            // Check if the sheet exists
            if (this.sheet == null) {
                throw new IllegalArgumentException("Sheet with name '" + sheetName + "' does not exist in the Excel file.");
            }
        } catch (IOException e) {
            throw new IOException("Error reading the Excel file: " + excelFilePath, e);
        }
    }

    // Fetching all rows as a List of Map<String, String> (Key = Column Name, Value = Cell Value)
    public List<Map<String, String>> getAllRowsAsMap() {
        List<Map<String, String>> rowsData = new ArrayList<>();
        Row headerRow = sheet.getRow(0); // First row contains headers

        // Check if the header row exists
        if (headerRow == null) {
            throw new IllegalStateException("Header row is missing in the sheet.");
        }

        // Iterate through rows, starting from the second row (index 1)
        for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (row == null) {
                continue; // Skip empty rows
            }

            Map<String, String> rowData = new HashMap<>();

            // Iterate through columns (cells) and map each header to the corresponding cell value
            for (int colIndex = 0; colIndex < headerRow.getLastCellNum(); colIndex++) {
                Cell headerCell = headerRow.getCell(colIndex);
                Cell cell = row.getCell(colIndex);

                // Get header name
                String header = (headerCell != null) ? headerCell.getStringCellValue() : "Column" + colIndex;

                // Get cell value as a string
                String cellValue = getCellValueAsString(cell);

                rowData.put(header, cellValue);
            }
            rowsData.add(rowData);
        }
        return rowsData;
    }

    // Utility method to get cell value as a String
    private String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return ""; // Return empty string for null cells
        }

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString(); // Return date as string
                }
                return Double.toString(cell.getNumericCellValue()); // Return numeric value as string
            case BOOLEAN:
                return Boolean.toString(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula(); // Return formula as string
            case BLANK:
                return "";
            default:
                return "";
        }
    }

    // Close the workbook
    public void close() {
        try {
            if (workbook != null) {
                workbook.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
