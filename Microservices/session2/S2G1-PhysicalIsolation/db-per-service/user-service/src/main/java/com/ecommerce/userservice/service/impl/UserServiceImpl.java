package com.ecommerce.userservice.service.impl;

import com.ecommerce.userservice.dto.DbInfoDTO;
import com.ecommerce.userservice.dto.UserResponseDTO;
import com.ecommerce.userservice.entity.User;
import com.ecommerce.userservice.repository.UserRepository;
import com.ecommerce.userservice.service.UserService;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.List;

/**
 * Cài đặt UserService.
 * <p>
 * Phương thức {@link #getDatabaseInfo()} truy vấn trực tiếp PostgreSQL bằng
 * {@code current_database()} để lấy tên database thật mà service đang kết nối,
 * từ đó chứng minh service trỏ tới user_db.
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final DataSource dataSource;

    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserResponseDTO::fromEntity)
                .toList();
    }

    @Override
    public DbInfoDTO getDatabaseInfo() {
        try (Connection conn = dataSource.getConnection()) {
            // Hỏi thẳng PostgreSQL: bạn đang ở database nào?
            String dbName;
            try (var stmt = conn.createStatement();
                 var rs = stmt.executeQuery("SELECT current_database()")) {
                rs.next();
                dbName = rs.getString(1);
            }

            String poolName = (dataSource instanceof HikariDataSource hikari)
                    ? hikari.getPoolName() : "unknown";

            var meta = conn.getMetaData();
            return DbInfoDTO.builder()
                    .serviceName("user-service")
                    .databaseName(dbName)
                    .jdbcUrl(meta.getURL())
                    .connectionPool(poolName)
                    .dbUser(meta.getUserName())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Không lấy được thông tin DB: " + e.getMessage(), e);
        }
    }

    /** Nạp dữ liệu mẫu vào user_db khi khởi động */
    @PostConstruct
    public void seed() {
        if (userRepository.count() == 0) {
            userRepository.save(User.builder()
                    .username("huy.nguyen").email("huy@example.com").fullName("Nguyễn Văn Huy").build());
            userRepository.save(User.builder()
                    .username("an.tran").email("an@example.com").fullName("Trần Thị An").build());
        }
    }
}
