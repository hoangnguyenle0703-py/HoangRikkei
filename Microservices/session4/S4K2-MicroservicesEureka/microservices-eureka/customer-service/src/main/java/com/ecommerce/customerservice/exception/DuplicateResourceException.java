package com.ecommerce.customerservice.exception;

/**
 * Ném ra khi đăng ký với email đã tồn tại.
 */
public class DuplicateResourceException extends RuntimeException {
    public DuplicateResourceException(String message) {
        super(message);
    }
}
