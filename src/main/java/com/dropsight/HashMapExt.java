package com.dropsight;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class HashMapExt {
    public HashMap<Integer, String[]> readCSVToHashMap(String filePath) {
        HashMap<Integer, String[]> dataMap = new HashMap<>();
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] nextLine;
            int rowIndex = 0;
            while ((nextLine = reader.readNext()) != null) {
                dataMap.put(rowIndex++, nextLine);
            }
        } catch (IOException | CsvValidationException e) {
            System.out.println("Error reading CSV file: " + e.getMessage());
        }

        return dataMap;
    }
}
