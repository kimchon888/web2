package com.example.coffee.service;

import com.example.coffee.model.CoffeeTable;
import com.example.coffee.repository.CoffeeTableRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TableService {
    private final CoffeeTableRepository repo;

    public TableService(CoffeeTableRepository repo) {
        this.repo = repo;
    }

    public CoffeeTable create(CoffeeTable table) {
        return repo.save(table);
    }

    public List<CoffeeTable> listAll() {
        return repo.findAll();
    }

    public List<CoffeeTable> listAvailable() {
        return repo.findByStatus("EMPTY");
    }

    public CoffeeTable updateStatus(Long id, String status) {
        return repo.findById(id).map(t -> {
            t.setStatus(status);
            return repo.save(t);
        }).orElse(null);
    }
}
