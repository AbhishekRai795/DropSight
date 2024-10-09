package com.dropsight;

import java.util.HashMap;
import java.util.Arrays;

public class ProbabilityCalculator {
    private HashMap<String, double[]> dataMap;
    private HashMap<String, Double> thresholdValues;

    // Constructor to initialize the data map and calculate thresholds
    public ProbabilityCalculator(HashMap<String, double[]> dataMap) {
        this.dataMap = dataMap;
        this.thresholdValues = new HashMap<>();
        calculateThresholdValues();
    }

    // Method to calculate threshold values for each factor
    private void calculateThresholdValues() {
        thresholdValues.put("reviews", calculateMedian(dataMap, "reviews"));
        thresholdValues.put("price", calculateMedian(dataMap, "price"));
        thresholdValues.put("sales", calculateMedian(dataMap, "sales"));
    }

    // Method to calculate the median for a given factor
    private double calculateMedian(HashMap<String, double[]> dataMap, String factor) {
        double[] values = dataMap.values().stream()
            .mapToDouble(productData -> productData[getIndex(factor)])
            .toArray();

        Arrays.sort(values);
        int mid = values.length / 2;
        return values.length % 2 == 0 ? (values[mid - 1] + values[mid]) / 2.0 : values[mid];
    }

    // Helper method to get the index of the factor
    private int getIndex(String factor) {
        return switch (factor) {
            case "reviews" -> 0;
            case "price" -> 1;
            case "sales" -> 2;
            default -> throw new IllegalArgumentException("Invalid factor: " + factor);
        };
    }

    // Method to calculate success probability using Bayes' Theorem
    public double calculateSuccessProbability(String productKey, double priorSuccess) {
        double[] productData = dataMap.get(productKey);
        if (productData == null || productData.length < 3) {
            throw new IllegalArgumentException("Invalid product data for key: " + productKey);
        }

        double reviews = productData[0];
        double price = productData[1];
        double sales = productData[2];

        // Calculate conditional probabilities
        double pReviewsGivenSuccess = calculateConditionalProbability(reviews, "reviews");
        double pPriceGivenSuccess = calculateConditionalProbability(price, "price");
        double pSalesGivenSuccess = calculateConditionalProbability(sales, "sales");

        // Calculate marginal probabilities
        double pReviews = calculateMarginalProbability("reviews");
        double pPrice = calculateMarginalProbability("price");
        double pSales = calculateMarginalProbability("sales");

        // Bayes' Theorem: Calculate the posterior probability
        double numerator = pReviewsGivenSuccess * pPriceGivenSuccess * pSalesGivenSuccess * priorSuccess;
        double denominator = pReviews * pPrice * pSales;

        // Ensure the result is within [0, 1]
        return Math.min(numerator / denominator, 1.0);
    }

    // Calculate conditional probability based on threshold
    private double calculateConditionalProbability(double value, String factor) {
        double threshold = thresholdValues.get(factor);
      
        return Math.min(0.1 + (0.8 * (value / threshold)), 0.9); // Adjust probability based on the value-to-threshold ratio
    }

    // Example marginal probabilities for each factor (adjust based on actual data)
    private double calculateMarginalProbability(String factor) {
        return switch (factor) {
            case "reviews" -> 0.6; // Adjust based on dataset
            case "price" -> 0.7;
            case "sales" -> 0.8;
            default -> throw new IllegalArgumentException("Invalid factor: " + factor);
        };
    }
}
