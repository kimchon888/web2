package com.example.coffee.controller;

import com.example.coffee.model.User;
import com.example.coffee.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*") // Cho phép gọi API từ frontend
public class UserController {

    @Autowired
    private UserService userService;

    // Lấy danh sách tất cả người dùng
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // Lấy thông tin người dùng theo ID
    @GetMapping("/{id}")
    public Optional<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    // Tạo mới hoặc cập nhật người dùng
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    // Cập nhật người dùng
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        Optional<User> optionalUser = userService.getUserById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setUsername(userDetails.getUsername());
            user.setEmail(userDetails.getEmail());
            user.setPassword(userDetails.getPassword());
            user.setRoles(userDetails.getRoles());
            return userService.saveUser(user);
        } else {
            throw new RuntimeException("Không tìm thấy người dùng với ID: " + id);
        }
    }

    // Xóa người dùng theo ID
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "✅ Đã xóa người dùng có ID: " + id;
    }
}
