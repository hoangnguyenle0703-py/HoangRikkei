package com.example.hrm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    // 1. Cấu hình PasswordEncoder sử dụng BCrypt để mã hóa mật khẩu
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 2. Cấu hình SecurityFilterChain để phân quyền API
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Tắt CSRF vì chúng ta đang làm REST API (không dùng Session dựa trên trình duyệt)
                .csrf(csrf -> csrf.disable())

                // Cấu hình phân quyền
                .authorizeHttpRequests(auth -> auth
                        // Cho phép truy cập tự do (permitAll) vào các đường dẫn bắt đầu bằng /api/v1/auth/
                        .requestMatchers("/api/v1/auth/**").permitAll()

                        // Tất cả các yêu cầu còn lại đều phải xác thực (authenticated)
                        .anyRequest().authenticated()
                )

                // Sử dụng Basic Auth (hiện popup hoặc gửi qua Header trong Postman)
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}