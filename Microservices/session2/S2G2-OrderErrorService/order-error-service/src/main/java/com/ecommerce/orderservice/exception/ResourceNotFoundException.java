package com.ecommerce.orderservice.exception;

/**
 * Custom Exception (ngoại lệ tự định nghĩa): ném ra khi không tìm thấy
 * tài nguyên được yêu cầu trong hệ thống.
 * <p>
 * Kế thừa {@link RuntimeException} (unchecked exception) để không phải khai
 * báo {@code throws} ở mọi tầng. Khi exception này được ném ra, nó sẽ được
 * {@link GlobalExceptionHandler} bắt và chuyển thành response lỗi chuẩn.
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    /**
     * Hàm dựng tiện ích tạo thông điệp dạng:
     * "Order với ID 999 không tồn tại trên hệ thống!"
     *
     * @param resourceName tên tài nguyên (vd: "Order")
     * @param id           giá trị định danh không tìm thấy
     */
    public ResourceNotFoundException(String resourceName, Object id) {
        super(String.format("%s với ID %s không tồn tại trên hệ thống!", resourceName, id));
    }
}
