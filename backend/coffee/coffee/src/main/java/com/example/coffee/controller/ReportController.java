package com.example.coffee.controller;

import com.example.coffee.dto.ReportResponse;
import com.example.coffee.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/report")
public class ReportController {
    private final OrderService orderService;
    public ReportController(OrderService orderService) { this.orderService = orderService; }

    @GetMapping("/daily")
    // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ReportResponse> dailyReport() {
        LocalDate today = LocalDate.now();
        LocalDateTime start = today.atStartOfDay();
        LocalDateTime end = start.plusDays(1);
        var orders = orderService.getOrdersBetween(start, end);
        long customers = orders.size();
        double revenue = orders.stream().mapToDouble(o -> o.getTotalPrice()).sum();
        return ResponseEntity.ok(new ReportResponse(customers, revenue));
    }
}
