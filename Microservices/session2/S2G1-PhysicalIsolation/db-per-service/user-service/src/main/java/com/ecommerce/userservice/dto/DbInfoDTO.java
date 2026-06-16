package com.ecommerce.userservice.dto;

import lombok.*;

/**
 * DTO mô tả thông tin kết nối database hiện tại của service.
 * <p>
 * Dùng để CHỨNG MINH rằng user-service kết nối tới một database riêng
 * (user_db) — khi so sánh với inventory-service sẽ thấy chúng trỏ tới
 * hai database, hai URL, hai connection pool khác nhau, không hề dùng chung.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DbInfoDTO {

    /** Tên service */
    private String serviceName;

    /** Tên database đang kết nối (lấy trực tiếp từ PostgreSQL: current_database()) */
    private String databaseName;

    /** JDBC URL của kết nối */
    private String jdbcUrl;

    /** Tên connection pool (HikariCP) */
    private String connectionPool;

    /** Tài khoản DB đang dùng */
    private String dbUser;
}
