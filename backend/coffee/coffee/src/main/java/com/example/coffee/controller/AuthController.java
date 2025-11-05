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
        origins = {"http://localhost:5173", "http://localhost:5174"},
        allowedHeaders = "*",
        allowCredentials = "true",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS}
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

    // 🔹 Đăng ký người dùng mới
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        Optional<User> existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(Map.of("error", "Username already exists"));
        }

        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseGet(() -> roleRepository.save(new Role("ROLE_USER")));

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Collections.singleton(userRole));
        userRepository.save(user);

        return ResponseEntity.ok(Map.of("message", "User registered successfully"));
    }

    // 🔹 Đăng nhập
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginUser) {
        System.out.println("📩 Login request: " + loginUser.getUsername());

        try {
            User user = userRepository.findByUsername(loginUser.getUsername())
                    .or(() -> userRepository.findByEmail(loginUser.getUsername()))
                    .orElseThrow(() -> new RuntimeException("User not found"));

            if (!passwordEncoder.matches(loginUser.getPassword(), user.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("error", "Invalid password"));
            }

            //  Lấy danh sách quyền
            Set<String> roles = user.getRoles().stream()
                    .map(Role::getName)
                    .collect(Collectors.toSet());

            //  Sinh JWT token kèm roles
            String token = jwtService.generateToken(user.getUsername(), roles);

            return ResponseEntity.ok(Map.of(
                    "message", "Login successful",
                    "token", token,
                    "username", user.getUsername(),
                    "roles", roles
            ));

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", e.getMessage()));
        }
    }
}
