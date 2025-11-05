package com.example.coffee.model;

import jakarta.persistence.*;

@Entity
@Table(name = "coffee_table")
public class CoffeeTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int tableNumber;

    // "EMPTY", "OCCUPIED", "PAID"
    private String status = "EMPTY";

    // getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public int getTableNumber() { return tableNumber; }
    public void setTableNumber(int tableNumber) { this.tableNumber = tableNumber; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
