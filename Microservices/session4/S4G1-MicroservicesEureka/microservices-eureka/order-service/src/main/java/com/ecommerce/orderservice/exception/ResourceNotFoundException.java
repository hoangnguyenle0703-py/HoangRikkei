package com.ecommerce.orderservice.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
    public static ResourceNotFoundException order(Object id) {
        return new ResourceNotFoundException(
                String.format("Đơn hàng với ID %s không tồn tại!", id));
    }
}
