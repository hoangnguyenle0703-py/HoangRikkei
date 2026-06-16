package com.ecommerce.inventoryservice.dto;

import lombok.*;

/**
 * DTO mô tả thông tin kết nối database hiện tại của service.
 * Dùng để chứng minh inventory-service kết nối tới inventory_db.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DbInfoDTO {
    private String serviceName;
    private String databaseName;
    private String jdbcUrl;
    private String connectionPool;
    private String dbUser;
}
