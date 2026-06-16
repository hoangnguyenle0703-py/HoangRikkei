package com.ecommerce.userservice.service;

import com.ecommerce.userservice.dto.DbInfoDTO;
import com.ecommerce.userservice.dto.UserResponseDTO;

import java.util.List;

/**
 * Tầng nghiệp vụ cho User-Service.
 */
public interface UserService {

    /** Lấy danh sách user từ user_db */
    List<UserResponseDTO> getAllUsers();

    /** Lấy thông tin kết nối DB hiện tại (dùng để chứng minh tách biệt) */
    DbInfoDTO getDatabaseInfo();
}
