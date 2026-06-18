package com.ecommerce.productservice.exception;

/**
 * Ném ra khi không tìm thấy tài nguyên.
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public static ResourceNotFoundException product(Object id) {
        return new ResourceNotFoundException(
                String.format("Sản phẩm với ID %s không tồn tại!", id));
    }
}
