package com.ecommerce.orderservice.exception;

/**
 * Exception nghiệp vụ: ném ra khi không tìm thấy tài nguyên được yêu cầu
 * (ví dụ: không tìm thấy đơn hàng theo id).
 * <p>
 * Kế thừa {@link RuntimeException} để không buộc khai báo throws ở mọi nơi
 * (unchecked exception), phù hợp với mô hình xử lý lỗi tập trung.
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    /**
     * Hàm dựng tiện ích tạo thông điệp lỗi chuẩn dạng:
     * "Order không tồn tại với id = '5'"
     */
    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s không tồn tại với %s = '%s'", resourceName, fieldName, fieldValue));
    }
}
