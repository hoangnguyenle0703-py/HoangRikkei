package com.ecommerce.customerservice.exception;

import com.ecommerce.customerservice.dto.ApiResponseError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

/**
 * Xử lý ngoại lệ TẬP TRUNG bằng @RestControllerAdvice.
 * Mọi exception đều được chuyển thành ApiResponseError chuẩn hóa.
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

    /** 404 — không tìm thấy tài nguyên */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponseError> handleNotFound(ResourceNotFoundException ex) {
        return new ResponseEntity<>(build(HttpStatus.NOT_FOUND, ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    /** 401 — sai thông tin đăng nhập */
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ApiResponseError> handleInvalidCredentials(InvalidCredentialsException ex) {
        return new ResponseEntity<>(build(HttpStatus.UNAUTHORIZED, ex.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    /** 409 — email đã tồn tại */
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ApiResponseError> handleDuplicate(DuplicateResourceException ex) {
        return new ResponseEntity<>(build(HttpStatus.CONFLICT, ex.getMessage()), HttpStatus.CONFLICT);
    }

    /** 400 — dữ liệu đầu vào không hợp lệ */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseError> handleValidation(MethodArgumentNotValidException ex) {
        String msg = ex.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining("; "));
        return new ResponseEntity<>(build(HttpStatus.BAD_REQUEST, msg), HttpStatus.BAD_REQUEST);
    }

    /** 500 — lỗi không lường trước */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseError> handleGeneric(Exception ex) {
        return new ResponseEntity<>(build(HttpStatus.INTERNAL_SERVER_ERROR,
                "Lỗi hệ thống: " + ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
