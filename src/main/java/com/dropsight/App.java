package com.dropsight;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Scanner;


/**
 *
 *  Hello world!
 * 
 */
public class App 
{
    public static void main(String[] args) {
        // Prompt user for the Excel file path
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String excelFilePath;
        String outputDirectory;
        String outputFileName;
        int[] columnIndices;

        try {
            // Get user input for Excel file path
            System.out.print("Enter the path to the Excel file: ");
            excelFilePath = reader.readLine();

            // Get user input for output directory
            System.out.print("Enter the output directory path: ");
            outputDirectory = reader.readLine();

            // Get user input for output file name
            System.out.print("Enter the output file name (e.g., output.csv): ");
            outputFileName = reader.readLine();

            // Get user input for column indices to extract
            System.out.print("Enter the column indices to extract (comma-separated, e.g., 2,3,4): ");
            String columnInput = reader.readLine();
            String[] columnStrings = columnInput.split(",");
            columnIndices = new int[columnStrings.length];

            for (int i = 0; i < columnStrings.length; i++) {
                columnIndices[i] = Integer.parseInt(columnStrings[i].trim()); // Convert to int
            }

            // Extract multiple column data from the Excel file and store it in the file system
            ExcelExtractor extractor = new ExcelExtractor();
            extractor.extractAndStoreMultipleColumns(excelFilePath, columnIndices, outputDirectory, outputFileName);
            System.out.println("Data extracted successfully!");
            //Hashmap extraction
            HashMapExt hashMapExt = new HashMapExt();
            String filePath = "C:\\Minor project\\dropsight\\outputcsv\\output.csv";  // Update with the correct path
            HashMap<String, double[]> dataMap = hashMapExt.readCSVToHashMap(filePath);

            ProbabilityCalculator calculator = new ProbabilityCalculator(dataMap);
        
            
            double priorSuccess = 0.6;
           

            Scanner scanner = new Scanner(System.in);

            System.out.println("Available products: ");
            for (String productKey : dataMap.keySet()) {
                System.out.println(productKey);
            }


            System.out.print("Enter the product key to calculate its success probability: ");
            String productKey = scanner.nextLine();

            if (dataMap.containsKey(productKey)) {
                double successProbability = calculator.calculateSuccessProbability(productKey, priorSuccess);
                System.out.println("Success Probability for " + productKey + ": " + successProbability*100);
            } else {
                System.out.println("Product not found.");
            }
    
            scanner.close();
    
           
            
        

        } catch (IOException e) {
            System.out.println("An error occurred while reading input: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid column index format. Please ensure you're entering numbers.");
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
        

       
    }
}
