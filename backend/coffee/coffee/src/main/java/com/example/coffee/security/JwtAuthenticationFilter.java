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

        // ✅ Cho phép các API public đi thẳng (không kiểm tra token)
        if (path.startsWith("/auth") ||
                path.startsWith("/api/auth") ||
                path.startsWith("/api/menu") ||
                path.startsWith("/api/tables") ||
                path.startsWith("/api/orders"))
        {
            filterChain.doFilter(request, response);
            return;
        }

        // ✅ Lấy Authorization header
        String authHeader = request.getHeader("Authorization");

        // ✅ Nếu không có token thì cho request đi tiếp (Spring Security sẽ xử lý tiếp)
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);

        try {
            // ✅ Lấy username từ token
            String username = jwtService.extractUsername(token);

            // ✅ Nếu token hợp lệ thì tiếp tục request
            if (jwtService.validateToken(token, username)) {
                filterChain.doFilter(request, response);
                return;
            }

            // ❌ Token không hợp lệ
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired JWT token");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT token");
        }
    }
}
