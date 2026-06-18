package com.ecommerce.orderservice.exception;

/**
 * Ném ra khi dữ liệu đầu vào không hợp lệ về mặt nghiệp vụ (vd quantity <= 0).
 */
public class InvalidInputException extends RuntimeException {
    public InvalidInputException(String message) {
        super(message);
    }
}
