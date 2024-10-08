package com.dropsight;

public class StringUtils {

    // Method to extract numeric value from a string like "1 rating" or "19.99 price"
    public static double extractNumericValue(String input) {
        // Use a regular expression to extract the numeric part
        String numericPart = input.replaceAll("[^\\d.]+", ""); // Retain digits and decimal points
        if (!numericPart.isEmpty()) {
            return Double.parseDouble(numericPart); // Convert to double
        } else {
            throw new IllegalArgumentException("No numeric value found in the input: " + input);
        }
    }
}
