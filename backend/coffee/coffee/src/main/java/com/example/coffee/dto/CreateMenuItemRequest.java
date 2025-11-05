package com.example.coffee.dto;

public class CreateMenuItemRequest {
    private String name;
    private double price;

    // getters/setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
}
