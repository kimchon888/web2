package com.example.coffee.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String path = request.getServletPath();

        //  Bỏ qua xác thực cho các API công khai
        if (path.startsWith("/api/auth") ||
                path.startsWith("/api/menu") ||
                path.startsWith("/api/tables") ||
                path.startsWith("/api/orders")) {

            filterChain.doFilter(request, response);
            return;
        }

        //  Lấy header Authorization
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing or invalid Authorization header");
            return;
        }

        String token = authHeader.substring(7);

        try {
            //  Lấy username từ token
            String username = jwtService.extractUsername(token);

            //  Kiểm tra token hợp lệ
            if (!jwtService.validateToken(token, username)) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired JWT token");
                return;
            }

            //  Nếu hợp lệ thì cho phép request đi tiếp
            filterChain.doFilter(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT token: " + e.getMessage());
        }
    }
}
