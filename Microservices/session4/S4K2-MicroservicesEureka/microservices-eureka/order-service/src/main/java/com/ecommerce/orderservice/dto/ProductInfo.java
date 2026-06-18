package com.ecommerce.orderservice.dto;

import lombok.*;

import java.math.BigDecimal;

/**
 * DTO ánh xạ dữ liệu sản phẩm NHẬN VỀ từ Product Service qua API.
 * Dùng để lấy giá (price) phục vụ tính totalAmount.
 */
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class ProductInfo {
    private Long id;
    private String name;
    private BigDecimal price;
    private Integer stockQuantity;
}
