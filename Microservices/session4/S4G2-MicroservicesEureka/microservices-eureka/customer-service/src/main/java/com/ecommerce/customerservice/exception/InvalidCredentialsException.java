package com.ecommerce.customerservice.exception;

/**
 * Ném ra khi đăng nhập sai email hoặc mật khẩu.
 */
public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException(String message) {
        super(message);
    }
}
