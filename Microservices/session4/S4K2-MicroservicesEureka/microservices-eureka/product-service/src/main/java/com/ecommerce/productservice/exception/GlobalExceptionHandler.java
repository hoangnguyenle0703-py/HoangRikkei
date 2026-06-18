package com.ecommerce.productservice.exception;

import com.ecommerce.productservice.dto.ApiResponseError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

/**
 * Xử lý ngoại lệ tập trung bằng @RestControllerAdvice.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private ApiResponseError build(HttpStatus status, String message) {
        return ApiResponseError.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(message)
                .build();
    }

    /**
     * Bắt ngoại lệ khi Bean Validation thất bại (xảy ra khi dữ liệu @Valid không hợp lệ,
     * vd: price = -500). Trả về HTTP 400 Bad Request.
     * <p>
     * MAP các thông báo lỗi từ Validation vào trường message của ApiResponseError.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseError> handleValidation(MethodArgumentNotValidException ex) {
        // Gom tất cả message lỗi từ các field không hợp lệ
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining("; "));
        return new ResponseEntity<>(build(HttpStatus.BAD_REQUEST, message), HttpStatus.BAD_REQUEST);
    }

    /** 404 — không tìm thấy sản phẩm */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponseError> handleNotFound(ResourceNotFoundException ex) {
        return new ResponseEntity<>(build(HttpStatus.NOT_FOUND, ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    /** 500 — lỗi không lường trước */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseError> handleGeneric(Exception ex) {
        return new ResponseEntity<>(build(HttpStatus.INTERNAL_SERVER_ERROR,
                "Lỗi hệ thống: " + ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
