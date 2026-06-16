package com.ecommerce.orderservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;

/**
 * DTO bao bọc (wrapper) chung cho mọi phản hồi của API.
 * <p>
 * Giúp định dạng response nhất quán toàn hệ thống, tách biệt cấu trúc
 * dữ liệu trả ra ngoài khỏi Entity nội bộ (nguyên tắc không lộ Entity ra client).
 *
 * @param <T> kiểu dữ liệu của trường data
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    /** Trạng thái xử lý: true = thành công, false = thất bại */
    private boolean success;

    /** Thông điệp mô tả kết quả */
    private String message;

    /** Dữ liệu trả về (có thể null nếu chỉ trả thông báo) */
    private T data;

    /** Thời điểm phản hồi */
    private LocalDateTime timestamp;

    /** Tạo nhanh một response thành công kèm dữ liệu */
    public static <T> ApiResponse<T> success(String message, T data) {
        return ApiResponse.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .timestamp(LocalDateTime.now())
                .build();
    }

    /** Tạo nhanh một response lỗi */
    public static <T> ApiResponse<T> error(String message) {
        return ApiResponse.<T>builder()
                .success(false)
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();
    }
}
