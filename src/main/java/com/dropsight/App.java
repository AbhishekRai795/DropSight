package com.dropsight;

/**
 *
 *  Hello world!
 * 
 */
public class App 
{
    public static void main(String[] args) {
        String excelFilePath = "C:\\Minor project\\dropsight\\Data\\Amazon_Mobile_Data 1.xlsx";  
        String outputDirectory = "C:\\Minor project\\dropsight\\outputcsv"; // Replace with your desired output directory
        String outputFileName = "Main_Data.csv";  // Name of the output file
        int[] columnIndices = {1, 2, 3, 4};  // Column indices for B, C, D, E (1-based)

        try {
            // Step 1: Extract multiple column data from the Excel file and store it in the file system
            ExcelExtractor extractor = new ExcelExtractor();
            extractor.extractAndStoreMultipleColumns(excelFilePath, columnIndices, outputDirectory, outputFileName);
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
}
