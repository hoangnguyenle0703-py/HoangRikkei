package com.ecommerce.orderservice.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO trả chi tiết đơn hàng. Dữ liệu Order lấy từ order_db, dữ liệu khách
 * (customer) lấy từ Customer Service qua API rồi ghép lại tại tầng service.
 */
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class OrderDetailResponse {
    private Long orderId;
    private Long customerId;
    private CustomerInfo customer;   // ghép từ Customer Service
    private LocalDateTime orderDate;
    private BigDecimal totalAmount;
    private String status;
}
