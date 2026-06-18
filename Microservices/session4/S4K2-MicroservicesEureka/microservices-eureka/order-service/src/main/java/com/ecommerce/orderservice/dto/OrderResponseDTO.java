package com.ecommerce.orderservice.dto;

import com.ecommerce.orderservice.entity.Order;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO trả thông tin đơn hàng ra ngoài.
 */
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class OrderResponseDTO {

    private Long id;
    private Long customerId;
    private Long productId;
    private LocalDateTime orderDate;
    private BigDecimal totalAmount;

    public static OrderResponseDTO fromEntity(Order o) {
        return OrderResponseDTO.builder()
                .id(o.getId())
                .customerId(o.getCustomerId())
                .productId(o.getProductId())
                .orderDate(o.getOrderDate())
                .totalAmount(o.getTotalAmount())
                .build();
    }
}
