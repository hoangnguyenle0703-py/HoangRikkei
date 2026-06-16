package com.ecommerce.orderservice.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;

/**
 * DTO nhận dữ liệu đầu vào khi client tạo đơn hàng mới.
 * <p>
 * Theo quy tắc đặt tên: hậu tố "Request" cho DTO đầu vào → {@code OrderRequest}.
 * Việc tách riêng DTO khỏi Entity giúp kiểm soát đúng những trường client
 * được phép gửi lên, đồng thời gắn các ràng buộc validation.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequest {

    @NotNull(message = "customerId không được để trống")
    private Long customerId;

    @NotNull(message = "totalAmount không được để trống")
    @Positive(message = "totalAmount phải lớn hơn 0")
    private BigDecimal totalAmount;
}
