package com.ecommerce.orderservice.exception;

import com.ecommerce.orderservice.dto.ApiResponseError;
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

    /** 400 — quantity <= 0 hoặc lỗi nghiệp vụ đầu vào */
    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<ApiResponseError> handleInvalidInput(InvalidInputException ex) {
        return new ResponseEntity<>(build(HttpStatus.BAD_REQUEST, ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    /** 400 — Bean Validation thất bại (vd quantity <= 0 bắt bởi @Min) */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseError> handleValidation(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining("; "));
        return new ResponseEntity<>(build(HttpStatus.BAD_REQUEST, message), HttpStatus.BAD_REQUEST);
    }

    /** 404 — không tìm thấy đơn hàng */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponseError> handleNotFound(ResourceNotFoundException ex) {
        return new ResponseEntity<>(build(HttpStatus.NOT_FOUND, ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    /** 500 — lưu DB thất bại */
    @ExceptionHandler(OrderPersistenceException.class)
    public ResponseEntity<ApiResponseError> handlePersistence(OrderPersistenceException ex) {
        return new ResponseEntity<>(build(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /** 500 — lỗi không lường trước khác */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseError> handleGeneric(Exception ex) {
        return new ResponseEntity<>(build(HttpStatus.INTERNAL_SERVER_ERROR,
                "Lỗi hệ thống: " + ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
