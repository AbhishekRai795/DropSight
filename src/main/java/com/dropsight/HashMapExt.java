package com.dropsight;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class HashMapExt {
    public HashMap<String, String[]> readCSVToHashMap(String filePath) {
        HashMap<String, String[]> dataMap = new HashMap<>();
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                // Check if the row has at least one column
                if (nextLine.length > 1) {
                    // First column (index 0) will be the key
                    String key = nextLine[0];

                    // Rest of the columns will be the values
                    String[] values = new String[nextLine.length - 1];
                    System.arraycopy(nextLine, 1, values, 0, nextLine.length - 1);

                    // Add to the HashMap
                    dataMap.put(key, values);
                }
            }
        } catch (IOException | CsvValidationException e) {
            System.out.println("Error reading CSV file: " + e.getMessage());
        }

        return dataMap;
    }
}
