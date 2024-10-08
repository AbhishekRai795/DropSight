package com.dropsight;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class HashMapExt {
    public HashMap<String, double[]> readCSVToHashMap(String filePath) {
        HashMap<String, double[]> dataMap = new HashMap<>();
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                // Check if the row has at least one column
                if (nextLine.length > 1) {
                    String key = nextLine[0];  // First column is ProductID

                    // Extract numeric values for reviews, price, and sales
                    double reviews = StringUtils.extractNumericValue(nextLine[1]);
                    double price = StringUtils.extractNumericValue(nextLine[2]);
                    double sales = StringUtils.extractNumericValue(nextLine[3]);

                    // Store in HashMap with double[] array for reviews, price, sales
                    dataMap.put(key, new double[]{reviews, price, sales});
                }
            }
        } catch (IOException | CsvValidationException e) {
            System.out.println("Error reading CSV file: " + e.getMessage());
        }

        return dataMap;
    }
}
