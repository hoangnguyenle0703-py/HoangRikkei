package com.ecommerce.productservice.dto;

import com.ecommerce.productservice.entity.Product;
import lombok.*;

import java.math.BigDecimal;

/**
 * DTO trả dữ liệu sản phẩm ra ngoài.
 */
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
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
