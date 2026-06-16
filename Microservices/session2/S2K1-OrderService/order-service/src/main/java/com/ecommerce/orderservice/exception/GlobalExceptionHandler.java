package com.ecommerce.orderservice.exception;

import com.ecommerce.orderservice.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Bộ xử lý ngoại lệ tập trung cho toàn bộ ứng dụng.
 * <p>
 * Annotation {@code @RestControllerAdvice} cho phép bắt mọi exception
 * ném ra từ tầng Controller và chuyển thành response chuẩn, thay vì
 * để Spring trả về lỗi mặc định khó kiểm soát.
 * <p>
 * Đây là minh họa cho việc tách riêng tầng {@code exception} — logic xử lý lỗi
 * không bị trộn lẫn vào Controller hay Service.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /** Xử lý khi không tìm thấy tài nguyên → HTTP 404 */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleResourceNotFound(ResourceNotFoundException ex) {
        ApiResponse<Void> body = ApiResponse.error(ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    /** Xử lý khi dữ liệu đầu vào không hợp lệ (validation thất bại) → HTTP 400 */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        ApiResponse<Map<String, String>> body = ApiResponse.<Map<String, String>>builder()
                .success(false)
                .message("Dữ liệu đầu vào không hợp lệ")
                .data(errors)
                .build();
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    /** Xử lý mọi lỗi không lường trước → HTTP 500 */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGeneric(Exception ex) {
        ApiResponse<Void> body = ApiResponse.error("Lỗi hệ thống: " + ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
