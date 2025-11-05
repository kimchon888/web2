package com.example.coffee.controller;

import com.example.coffee.dto.CreateTableRequest;
import com.example.coffee.model.CoffeeTable;
import com.example.coffee.service.TableService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tables")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174"})
public class TableController {

    private final TableService service;

    public TableController(TableService service) {
        this.service = service;
    }

    //  ADMIN: Tạo bàn mới
    @PostMapping
    public ResponseEntity<CoffeeTable> create(@RequestBody CreateTableRequest req) {
        CoffeeTable t = new CoffeeTable();
        t.setTableNumber(req.getTableNumber());
        t.setStatus(req.getStatus() == null ? "EMPTY" : req.getStatus());
        return ResponseEntity.ok(service.create(t));
    }

    //  ADMIN: Xem tất cả bàn
    @GetMapping
    public ResponseEntity<List<CoffeeTable>> list() {
        return ResponseEntity.ok(service.listAll());
    }

    //  USER: Xem bàn trống
    @GetMapping("/available")
    public ResponseEntity<List<CoffeeTable>> listAvailable() {
        return ResponseEntity.ok(service.listAvailable());
    }

    //  ADMIN: Cập nhật trạng thái bàn
    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable Long id, @RequestParam String status) {
        var t = service.updateStatus(id, status);
        if (t == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(t);
    }
}
