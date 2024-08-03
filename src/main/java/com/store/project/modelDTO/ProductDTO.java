package com.store.project.modelDTO;

public class ProductDTO {
    private String name;
    private String description;
    private double price;
    private int quantity;

    private ProductDTO() {}

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }
}
