package com.example.coffee.service;

import com.example.coffee.model.*;
import com.example.coffee.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepo;
    private final MenuItemRepository menuRepo;
    private final CoffeeTableRepository tableRepo;

    public OrderService(OrderRepository orderRepo, MenuItemRepository menuRepo, CoffeeTableRepository tableRepo) {
        this.orderRepo = orderRepo;
        this.menuRepo = menuRepo;
        this.tableRepo = tableRepo;
    }

    public Order createOrder(User user, Long tableId, List<OrderItem> items) {
        Order order = new Order();
        order.setUser(user);

        CoffeeTable table = tableRepo.findById(tableId)
                .orElseThrow(() -> new RuntimeException("Table not found"));
        order.setTable(table);

        double total = items.stream()
                .mapToDouble(i -> i.getMenuItem().getPrice() * i.getQuantity())
                .sum();

        for (OrderItem i : items) {
            i.setOrder(order); // 🔹 gắn lại mối quan hệ
        }

        order.setItems(items);
        order.setTotalPrice(total);

        // Cập nhật trạng thái bàn
        table.setStatus("OCCUPIED");
        tableRepo.save(table);

        return orderRepo.save(order);
    }

    public List<Order> getOrdersBetween(LocalDateTime from, LocalDateTime to) {
        return orderRepo.findByCreatedAtBetween(from, to);
    }

    public List<Order> listAll() {
        return orderRepo.findAll();
    }
}
