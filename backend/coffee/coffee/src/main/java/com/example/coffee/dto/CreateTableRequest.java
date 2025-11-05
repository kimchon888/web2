package com.example.coffee.dto;

public class CreateTableRequest {
    private int tableNumber;
    private String status;

    // getters/setters
    public int getTableNumber() { return tableNumber; }
    public void setTableNumber(int tableNumber) { this.tableNumber = tableNumber; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
