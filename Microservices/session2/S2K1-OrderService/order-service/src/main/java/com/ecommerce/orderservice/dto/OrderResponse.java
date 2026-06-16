package com.ecommerce.orderservice.dto;

import com.ecommerce.orderservice.entity.Order;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO trả dữ liệu đơn hàng ra cho client.
 * <p>
 * Theo quy tắc đặt tên: hậu tố "Response" cho DTO đầu ra → {@code OrderResponse}.
 * Chỉ chứa những trường an toàn để hiển thị, không lộ cấu trúc Entity nội bộ.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponse {

    private Long id;
    private String orderCode;
    private Long customerId;
    private BigDecimal totalAmount;
    private String status;
    private LocalDateTime createdAt;

    /**
     * Hàm tiện ích chuyển đổi từ Entity sang DTO (mapping).
     * Trong dự án lớn nên dùng thư viện MapStruct, ở đây giữ đơn giản.
     */
    public static OrderResponse fromEntity(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .orderCode(order.getOrderCode())
                .customerId(order.getCustomerId())
                .totalAmount(order.getTotalAmount())
                .status(order.getStatus().name())
                .createdAt(order.getCreatedAt())
                .build();
    }
}
