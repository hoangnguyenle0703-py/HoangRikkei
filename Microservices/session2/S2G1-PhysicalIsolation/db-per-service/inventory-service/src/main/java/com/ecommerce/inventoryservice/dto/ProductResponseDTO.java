package com.ecommerce.inventoryservice.dto;

import com.ecommerce.inventoryservice.entity.Product;
import lombok.*;

import java.math.BigDecimal;

/**
 * DTO trả thông tin sản phẩm ra ngoài.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponseDTO {

    private Long id;
    private String name;
    private BigDecimal price;
    private Integer stockQuantity;

    public static ProductResponseDTO fromEntity(Product p) {
        return ProductResponseDTO.builder()
                .id(p.getId())
                .name(p.getName())
                .price(p.getPrice())
                .stockQuantity(p.getStockQuantity())
                .build();
    }
}
