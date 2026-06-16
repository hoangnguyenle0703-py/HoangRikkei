package com.ecommerce.userservice.controller;

import com.ecommerce.userservice.dto.DbInfoDTO;
import com.ecommerce.userservice.dto.UserResponseDTO;
import com.ecommerce.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller cho User-Service.
 */
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /** GET /api/v1/users → danh sách user trong user_db */
    @GetMapping
    public List<UserResponseDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    /**
     * GET /api/v1/users/db-info
     * Trả về thông tin kết nối DB — dùng để chứng minh service trỏ tới user_db.
     */
    @GetMapping("/db-info")
    public DbInfoDTO getDatabaseInfo() {
        return userService.getDatabaseInfo();
    }
}
