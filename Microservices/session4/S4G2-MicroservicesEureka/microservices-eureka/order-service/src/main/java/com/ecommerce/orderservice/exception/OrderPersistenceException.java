package com.ecommerce.orderservice.exception;

/**
 * Ném ra khi lưu đơn hàng vào DB thất bại -> trả về 500 Internal Server Error.
 */
public class OrderPersistenceException extends RuntimeException {
    public OrderPersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
