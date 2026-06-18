package com.ecommerce.productservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

/**
 * DTO nhận dữ liệu khi tạo sản phẩm mới, kèm các RÀNG BUỘC Bean Validation.
 * <p>
 * Các ràng buộc theo yêu cầu đề:
 *  - name: không được để trống (@NotBlank)
 *  - price: phải lớn hơn 0 (@Min(1))
 *  - stockQuantity: không được âm (@Min(0))
 */
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class ProductRequestDTO {

    @NotBlank(message = "name không được để trống")
    private String name;

    @NotNull(message = "price không được để trống")
    @Min(value = 1, message = "price phải lớn hơn 0")
    private BigDecimal price;

    @NotNull(message = "stockQuantity không được để trống")
    @Min(value = 0, message = "stockQuantity không được âm")
    private Integer stockQuantity;
}
