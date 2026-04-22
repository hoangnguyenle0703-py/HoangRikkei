package com.example.hrm.service;

import com.example.hrm.dto.RegisterRequest;
import com.example.hrm.model.User;
import com.example.hrm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User register(RegisterRequest dto) {
        // 1. Check trùng username
        if (userRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new RuntimeException("Username đã tồn tại!");
        }

        // 2. Tạo Entity mới và map dữ liệu
        User user = new User();
        user.setUsername(dto.getUsername());

        // QUAN TRỌNG: Mã hóa mật khẩu trước khi lưu
        String encodedPassword = passwordEncoder.encode(dto.getPassword());
        user.setPassword(encodedPassword);

        // Bạn có thể thêm trường fullName vào Entity User nếu cần,
        // ở đây tôi set mặc định role và enabled theo yêu cầu bài trước
        user.setRole("USER");
        user.setEnabled(true);

        // 3. Lưu vào DB
        return userRepository.save(user);
    }
}