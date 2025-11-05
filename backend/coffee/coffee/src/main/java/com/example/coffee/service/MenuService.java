package com.example.coffee.service;

import com.example.coffee.model.MenuItem;
import com.example.coffee.repository.MenuItemRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MenuService {
    private final MenuItemRepository repo;
    public MenuService(MenuItemRepository repo) { this.repo = repo; }
    public MenuItem create(MenuItem m) { return repo.save(m); }
    public List<MenuItem> listAll(){ return repo.findAll(); }
}
