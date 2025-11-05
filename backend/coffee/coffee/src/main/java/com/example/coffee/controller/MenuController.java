package com.example.coffee.controller;

import com.example.coffee.dto.CreateMenuItemRequest;
import com.example.coffee.model.MenuItem;
import com.example.coffee.service.MenuService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/menu")
public class MenuController {
    private final MenuService service;
    public MenuController(MenuService service) { this.service = service; }

    @PostMapping
    // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MenuItem> create(@RequestBody CreateMenuItemRequest req) {
        MenuItem m = new MenuItem();
        m.setName(req.getName());
        m.setPrice(req.getPrice());
        return ResponseEntity.ok(service.create(m));
    }

    @GetMapping
    public ResponseEntity<List<MenuItem>> list() {
        return ResponseEntity.ok(service.listAll());
    }
}
