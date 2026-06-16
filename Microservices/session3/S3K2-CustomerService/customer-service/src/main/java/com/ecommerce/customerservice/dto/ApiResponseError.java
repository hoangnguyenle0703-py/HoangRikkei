package com.ecommerce.customerservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Định dạng lỗi chuẩn hóa toàn hệ thống — 4 thuộc tính:
 * timestamp, status, error, message.
 */
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class ApiResponseError {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timestamp;

    private int status;     // 404, 401, 400...
    private String error;   // "Not Found", "Unauthorized"...
    private String message; // mô tả chi tiết
}
