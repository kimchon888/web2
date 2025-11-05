package com.example.coffee.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 🔹 Người dùng đã đặt
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // 🔹 Bàn đã chọn
    @ManyToOne
    @JoinColumn(name = "table_id")
    private CoffeeTable table;

    // 🔹 Danh sách món trong order
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items;

    private double totalPrice;
    private LocalDateTime createdAt;
    private String status = "PENDING"; // PENDING, PAID

    public Order() {
        this.createdAt = LocalDateTime.now();
    }

    // Getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public CoffeeTable getTable() { return table; }
    public void setTable(CoffeeTable table) { this.table = table; }

    public List<OrderItem> getItems() { return items; }
    public void setItems(List<OrderItem> items) { this.items = items; }

    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
