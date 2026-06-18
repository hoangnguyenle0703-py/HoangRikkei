package com.ecommerce.orderservice.exception;

/**
 * Ném ra khi không tìm thấy instance của service cần gọi trên Eureka
 * (hoặc service bị tắt) -> trả về HTTP 503 Service Unavailable.
 */
public class ServiceUnavailableException extends RuntimeException {
    public ServiceUnavailableException(String message) {
        super(message);
    }
}
