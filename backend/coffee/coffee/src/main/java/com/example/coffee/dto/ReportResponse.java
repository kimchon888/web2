package com.example.coffee.dto;

public class ReportResponse {
    private long totalCustomers;
    private double totalRevenue;

    public ReportResponse(long totalCustomers, double totalRevenue) {
        this.totalCustomers = totalCustomers;
        this.totalRevenue = totalRevenue;
    }
    // getters/setters
    public long getTotalCustomers() { return totalCustomers; }
    public void setTotalCustomers(long totalCustomers) { this.totalCustomers = totalCustomers; }
    public double getTotalRevenue() { return totalRevenue; }
    public void setTotalRevenue(double totalRevenue) { this.totalRevenue = totalRevenue; }
}
