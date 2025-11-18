package com.example.coffee.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class CorsConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        // 🌐 Cho phép FE gọi BE
        config.setAllowedOrigins(List.of(
                "https://web2-1-8zko.onrender.com"   // ← domain frontend Render
        ));

        // ✔ Cho phép các HTTP method
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // ✔ Cho phép header
        config.setAllowedHeaders(List.of("Authorization", "Content-Type", "X-Requested-With"));

        config.setAllowCredentials(true);   // Cho phép gửi token
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // Áp dụng cho toàn bộ API
        source.registerCorsConfiguration("/**", config);

        return source;
    }
}
