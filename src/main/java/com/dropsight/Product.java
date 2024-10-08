package com.dropsight;

public class Product {
    private double reviews;
    private double price;
    private double sales;

    public Product(double reviews, double price, double sales) {
        this.reviews = reviews;
        this.price = price;
        this.sales = sales;
    }

    public double getReviews() {
        return reviews;
    }

    public double getPrice() {
        return price;
    }

    public double getSales() {
        return sales;
    }
}
