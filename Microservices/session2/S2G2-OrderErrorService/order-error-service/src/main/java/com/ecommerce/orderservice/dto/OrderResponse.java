package com.ecommerce.orderservice.dto;

import com.ecommerce.orderservice.entity.Order;
import lombok.*;

import java.math.BigDecimal;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class OrderResponse {
    private Long id;
    private String orderCode;
    private String customerName;
    private BigDecimal totalAmount;

    public static OrderResponse fromEntity(Order o) {
        return OrderResponse.builder()
                .id(o.getId())
                .orderCode(o.getOrderCode())
                .customerName(o.getCustomerName())
                .totalAmount(o.getTotalAmount())
                .build();
    }
}
