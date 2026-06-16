package com.ecommerce.productservice.dto;

import com.ecommerce.productservice.entity.Product;
import lombok.*;

import java.math.BigDecimal;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class ProductResponse {
    private Long id;
    private String name;
    private BigDecimal price;
    private Integer stockQuantity;
    private String description;

    public static ProductResponse fromEntity(Product p) {
        return ProductResponse.builder()
                .id(p.getId()).name(p.getName()).price(p.getPrice())
                .stockQuantity(p.getStockQuantity()).description(p.getDescription()).build();
    }
}
