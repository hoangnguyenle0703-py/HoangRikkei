package com.ecommerce.orderservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Định dạng lỗi chuẩn hóa: timestamp, status, error, message.
 */
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class ApiResponseError {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timestamp;

    private int status;
    private String error;
    private String message;
}
