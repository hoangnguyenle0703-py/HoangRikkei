package com.ecommerce.customerservice.exception;

/**
 * Ném ra khi không tìm thấy tài nguyên (vd: khách hàng theo id).
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    /** Tạo thông điệp: "Khách hàng với ID 99 không tồn tại!" */
    public static ResourceNotFoundException customer(Object id) {
        return new ResourceNotFoundException(
                String.format("Khách hàng với ID %s không tồn tại!", id));
    }
}
