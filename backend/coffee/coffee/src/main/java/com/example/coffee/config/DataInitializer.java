package com.example.coffee.config;

import com.example.coffee.model.Role;
import com.example.coffee.model.User;
import com.example.coffee.repository.RoleRepository;
import com.example.coffee.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Set;

@AllArgsConstructor
@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(UserRepository userRepo, RoleRepository roleRepo) {
        return args -> {
            if (roleRepo.findByName("ROLE_ADMIN").isEmpty()) {
                roleRepo.save(new Role("ROLE_ADMIN"));
            }
            if (roleRepo.findByName("ROLE_USER").isEmpty()) {
                roleRepo.save(new Role("ROLE_USER"));
            }

            if (userRepo.findByUsername("root").isEmpty()) {
                Role adminRole = roleRepo.findByName("ROLE_ADMIN").get();
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                User root = new User();
                root.setUsername("root");
                root.setPassword(encoder.encode("root123"));
                root.setEmail("root@cafe.com");
                root.setRoles(Set.of(adminRole));
                userRepo.save(root);
                System.out.println("✅ Root user created: root / root123");
            }
        };
    }
}
