package com.example.coffee.controller;

import com.example.coffee.dto.CreateOrderRequest;
import com.example.coffee.model.*;
import com.example.coffee.repository.MenuItemRepository;
import com.example.coffee.repository.UserRepository;
import com.example.coffee.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174"}, allowCredentials = "true")
public class OrderController {

    @Autowired private OrderService orderService;
    @Autowired private UserRepository userRepo;
    @Autowired private MenuItemRepository menuRepo;

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody CreateOrderRequest req, Principal principal) {
        User user = null;

        // Nếu khách đăng nhập thì lấy user
        if (principal != null) {
            user = userRepo.findByUsername(principal.getName())
                    .orElseThrow(() -> new RuntimeException("User not found"));
        } else {
            // Nếu khách chưa đăng nhập → dùng "guest"
            user = userRepo.findByUsername("guest").orElse(null);
        }

        List<OrderItem> items = req.getItems().stream().map(i -> {
            MenuItem menu = menuRepo.findById(i.getMenuItemId())
                    .orElseThrow(() -> new RuntimeException("Menu not found"));
            OrderItem item = new OrderItem();
            item.setMenuItem(menu);
            item.setQuantity(i.getQuantity());
            item.setPrice(menu.getPrice() * i.getQuantity());
            return item;
        }).toList();

        Order order = orderService.createOrder(user, req.getTableId(), items);
        return ResponseEntity.ok(order);
    }

    @GetMapping
    public List<Order> listAll() {
        return orderService.listAll();
    }

    @GetMapping("/today")
    public List<Order> getTodayOrders() {
        LocalDateTime start = LocalDate.now().atStartOfDay();
        LocalDateTime end = start.plusDays(1);
        return orderService.getOrdersBetween(start, end);
    }
    @GetMapping("/report/today")
    public ResponseEntity<?> getTodayReport() {
        LocalDateTime start = LocalDate.now().atStartOfDay();
        LocalDateTime end = start.plusDays(1);

        List<Order> orders = orderService.getOrdersBetween(start, end);

        double totalRevenue = orders.stream()
                .flatMap(o -> o.getItems().stream())
                .mapToDouble(OrderItem::getPrice)
                .sum();

        long totalOrders = orders.size();

        return ResponseEntity.ok(Map.of(
                "totalOrders", totalOrders,
                "totalRevenue", totalRevenue
        ));
    }
}
