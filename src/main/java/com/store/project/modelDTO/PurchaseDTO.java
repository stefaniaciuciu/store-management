package com.store.project.modelDTO;

public class PurchaseDTO {
    private String productName;
    private int quantity;
    private String date;

    private PurchaseDTO() {}

    public String getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getDate() {
        return date;
    }
}
