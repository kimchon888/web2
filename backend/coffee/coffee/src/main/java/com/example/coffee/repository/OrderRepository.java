package com.example.coffee.repository;

import com.example.coffee.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
}
