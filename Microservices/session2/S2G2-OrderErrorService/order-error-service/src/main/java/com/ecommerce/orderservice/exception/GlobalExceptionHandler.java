package com.ecommerce.orderservice.exception;

import com.ecommerce.orderservice.dto.ApiResponseError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

/**
 * Bộ xử lý ngoại lệ TẬP TRUNG cho toàn bộ ứng dụng.
 * <p>
 * Annotation {@code @RestControllerAdvice} biến class này thành "lớp tư vấn"
 * bao quanh mọi Controller: bất kỳ exception nào được ném ra từ Controller
 * đều bị bắt tại đây và chuyển thành {@link ApiResponseError} chuẩn hóa.
 * <p>
 * Nhờ đó, code nghiệp vụ chỉ cần ném exception — không phải lặp lại logic
 * tạo response lỗi ở mỗi nơi (DRY - Don't Repeat Yourself).
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Bắt ngoại lệ ResourceNotFoundException → trả về HTTP 404 với định dạng chuẩn.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponseError> handleResourceNotFound(ResourceNotFoundException ex) {
        ApiResponseError body = ApiResponseError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())          // 404
                .error(HttpStatus.NOT_FOUND.getReasonPhrase()) // "Not Found"
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    /**
     * Bắt mọi ngoại lệ không lường trước → trả về HTTP 500 với định dạng chuẩn.
     * Đảm bảo client không bao giờ nhận về stack trace thô.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseError> handleGeneric(Exception ex) {
        ApiResponseError body = ApiResponseError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())          // 500
                .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()) // "Internal Server Error"
                .message("Đã xảy ra lỗi hệ thống: " + ex.getMessage())
                .build();
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
