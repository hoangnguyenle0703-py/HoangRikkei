package com.ecommerce.orderservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * DTO nhận dữ liệu khi tạo đơn hàng: customerId, productId, quantity.
 * <p>
 * quantity phải > 0 (ràng buộc @Min(1)) — nếu <= 0 sẽ trả lỗi 400.
 */
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class OrderRequestDTO {

    @NotNull(message = "customerId không được để trống")
    private Long customerId;

    @NotNull(message = "productId không được để trống")
    private Long productId;

    @NotNull(message = "quantity không được để trống")
    @Min(value = 1, message = "quantity phải lớn hơn 0")
    private Integer quantity;
}
