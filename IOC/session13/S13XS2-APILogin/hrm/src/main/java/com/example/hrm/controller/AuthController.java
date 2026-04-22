package com.example.hrm.controller;

import com.example.hrm.dto.LoginRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            // 1. Tạo token xác thực từ thông tin người dùng nhập
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(), loginRequest.getPassword());

            // 2. Thực hiện xác thực (AuthenticationManager sẽ gọi UserDetailsService và PasswordEncoder)
            Authentication authentication = authenticationManager.authenticate(token);

            // 3. Nếu thành công, lưu thông tin vào SecurityContext
            SecurityContextHolder.getContext().setAuthentication(authentication);

            return ResponseEntity.ok("Đăng nhập thành công! Chào mừng " + authentication.getName());

        } catch (AuthenticationException e) {
            // 4. Nếu sai (sai pass, sai user), trả về 401
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("username or password incorrect");
        }
    }
}