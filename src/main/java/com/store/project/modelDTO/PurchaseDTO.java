package com.store.project.modelDTO;

public class PurchaseDTO {
    private String productName;
    private int quantity;
    private double price;
    private String date;

    private PurchaseDTO() {}

    public String getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public String getDate() {
        return date;
    }
}
