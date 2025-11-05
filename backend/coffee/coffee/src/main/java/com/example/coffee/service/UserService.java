package com.example.coffee.service;

import com.example.coffee.model.User;
import com.example.coffee.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Lấy danh sách tất cả người dùng
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Lấy user theo ID
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // Thêm hoặc cập nhật user
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    // Xóa user theo ID
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // Tìm user theo username
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
