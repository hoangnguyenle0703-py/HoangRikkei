package com.ecommerce.orderservice.dto;

import lombok.*;

/**
 * DTO ánh xạ dữ liệu khách hàng NHẬN VỀ từ Customer Service qua API.
 * Đây là bản sao tạm thời, không được lưu vào order_db.
 */
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class CustomerInfo {
    private Long id;
    private String fullName;
    private String email;
    private String address;
}
