package com.dropsight;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class ExcelExtractor {

    // Method to handle full extraction and storage of multiple columns
    public void extractAndStoreMultipleColumns(String excelFilePath, int[] columnIndices, String outputDirectory, String outputFileName) throws IOException {
        // Step 1: Create output directory if it doesn't exist
        createDirectory(outputDirectory);

        // Step 2: Extract data from multiple columns in Excel
        List<List<String>> columnsData = readMultipleColumns(excelFilePath, columnIndices);

        // Step 3: Write the extracted data to a CSV file in the output directory
        writeMultipleColumnsToCSV(columnsData, outputDirectory + File.separator + outputFileName);

        System.out.println("Column data has been written to " + outputDirectory + File.separator + outputFileName);
    }

    // Function to create a directory if it doesn't exist
    private void createDirectory(String directoryPath) throws IOException {
        Path path = Paths.get(directoryPath);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
            System.out.println("Directory created: " + directoryPath);
        } else {
            System.out.println("Directory already exists: " + directoryPath);
        }
    }

    // Function to read data from multiple columns in an Excel file
    private List<List<String>> readMultipleColumns(String excelFilePath, int[] columnIndices) throws IOException {
        List<List<String>> columnsData = new ArrayList<>();

        // Initialize lists to hold data for each column
        for (int i = 0; i < columnIndices.length; i++) {
            columnsData.add(new ArrayList<>());
        }

        try (FileInputStream file = new FileInputStream(new File(excelFilePath))) {
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0);  // Assuming data is in the first sheet

            // Iterate through each row
            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    continue;  // Skip header row
                }

                for (int i = 0; i < columnIndices.length; i++) {
                    int columnIndex = columnIndices[i];
                    Cell cell = row.getCell(columnIndex);  // Get the cell at the specified column index

                    // Add cell value to the respective column data list
                    if (cell != null) {
                        columnsData.get(i).add(cell.toString());
                    } else {
                        columnsData.get(i).add("");  // Add empty string for empty cells
                    }
                }
            }

            workbook.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return columnsData;
    }

    // Function to write extracted column data to a CSV file
    private void writeMultipleColumnsToCSV(List<List<String>> columnsData, String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            int numRows = columnsData.get(0).size();  // Number of rows, assuming all columns have the same number of rows

            for (int row = 0; row < numRows; row++) {
                StringBuilder line = new StringBuilder();

                // Append each column's data to the same row
                for (int col = 0; col < columnsData.size(); col++) {
                    line.append(columnsData.get(col).get(row));

                    if (col < columnsData.size() - 1) {
                        line.append(",");  // Separate columns with commas
                    }
                }

                writer.write(line.toString());
                writer.newLine();
            }
        }

        System.out.println("Data successfully written to " + filePath);
    }
}
