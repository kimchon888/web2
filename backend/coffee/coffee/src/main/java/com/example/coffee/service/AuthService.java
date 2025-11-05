package com.example.coffee.service;

import com.example.coffee.dto.LoginRequest;
import com.example.coffee.dto.RegisterRequest;
import com.example.coffee.dto.AuthResponse;
import com.example.coffee.model.Role;
import com.example.coffee.model.User;
import com.example.coffee.repository.RoleRepository;
import com.example.coffee.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public AuthResponse register(RegisterRequest request) {
        if (userRepo.findByUsername(request.getUsername()).isPresent()) {
            return new AuthResponse("❌ Username already exists!");
        }

        Role userRole = roleRepo.findByName("ROLE_USER").orElseThrow();
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(encoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setRoles(Set.of(userRole));
        userRepo.save(user);

        return new AuthResponse("✅ User registered successfully!");
    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepo.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (encoder.matches(request.getPassword(), user.getPassword())) {
            return new AuthResponse("✅ Login successful!");
        } else {
            return new AuthResponse("❌ Invalid password!");
        }
    }
}
