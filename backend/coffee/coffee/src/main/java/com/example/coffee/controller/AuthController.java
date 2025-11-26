package com.example.coffee.controller;

import com.example.coffee.model.Role;
import com.example.coffee.model.User;
import com.example.coffee.repository.RoleRepository;
import com.example.coffee.repository.UserRepository;
import com.example.coffee.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(
        origins = {
                "http://localhost:5173",
                "http://localhost:5174",
                "https://web2-1-8zko.onrender.com", // frontend Render
                "https://web2-c48d.onrender.com"    // backend Render
        },
        allowedHeaders = "*",
        allowCredentials = "true"
)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    // ✅ REGISTER
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {

        Optional<User> existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error", "Username already exists"));
        }

        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseGet(() -> roleRepository.save(new Role("ROLE_USER")));

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Collections.singleton(userRole));
        userRepository.save(user);

        return ResponseEntity.ok(Map.of("message", "User registered successfully"));
    }

    // ✅ LOGIN
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginUser) {

        User user = userRepository.findByUsername(loginUser.getUsername())
                .or(() -> userRepository.findByEmail(loginUser.getUsername()))
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(loginUser.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid password"));
        }

        // ✅ Get roles
        Set<String> roles = user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toSet());

        // ✅ Generate JWT
        String token = jwtService.generateToken(user.getUsername(), roles);

        // ✅ Return token in correct format for frontend
        return ResponseEntity.ok(Map.of(
                "message", "Login successful",
                "token", "Bearer " + token,
                "username", user.getUsername(),
                "roles", roles
        ));
    }
}
