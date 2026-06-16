package com.ecommerce.customerservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Cấu hình bộ mã hóa mật khẩu.
 * <p>
 * Dùng BCrypt — thuật toán băm một chiều có salt, là chuẩn để lưu mật khẩu an toàn.
 * Mật khẩu gốc KHÔNG bao giờ được lưu thẳng vào DB.
 */
@Configuration
public class PasswordConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
