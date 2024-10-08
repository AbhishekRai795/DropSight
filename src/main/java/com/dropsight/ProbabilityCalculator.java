package com.dropsight;

import java.util.HashMap;

public class ProbabilityCalculator {
    private HashMap<String, double[]> dataMap;

    // Constructor to initialize the data map
    public ProbabilityCalculator(HashMap<String, double[]> dataMap) {
        this.dataMap = dataMap;
    }

    // Method to calculate success probability using Bayes' Theorem
    public double calculateSuccessProbability(String productKey, double priorSuccess) {
        double[] productData = dataMap.get(productKey);
        if (productData == null || productData.length < 3) {
            throw new IllegalArgumentException("Invalid product data for key: " + productKey);
        }

        // Extract reviews, price, sales from product data
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

        // Apply Bayes' Theorem
        double numerator = pReviewsGivenSuccess * pPriceGivenSuccess * pSalesGivenSuccess * priorSuccess;
        double denominator = pReviews * pPrice * pSales;

        return numerator / denominator;
    }

    // Example logic for calculating conditional probability
    private double calculateConditionalProbability(double value, String factor) {
        double threshold = getThresholdForFactor(factor);
        if (value >= threshold) {
            return 0.9; // Higher probability of success if value meets/exceeds threshold
        } else {
            return 0.1; // Lower probability of success otherwise
        }
    }

    // Example marginal probabilities
    private double calculateMarginalProbability(String factor) {
        // Static values for simplicity; customize based on actual dataset analysis
        switch (factor) {
            case "reviews":
                return 0.6;
            case "price":
                return 0.7;
            case "sales":
                return 0.8;
            default:
                return 0.5;
        }
    }

    // Example threshold values for reviews, price, and sales
    private double getThresholdForFactor(String factor) {
        switch (factor) {
            case "reviews":
                return 100; // Example: 100 reviews
            case "price":
                return 20; // Example: $20
            case "sales":
                return 500; // Example: 500 sales
            default:
                return 0;
        }
    }
}
