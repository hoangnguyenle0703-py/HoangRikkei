package com.ecommerce.productservice.exception;

/**
 * Exception nghiệp vụ: ném ra khi không tìm thấy tài nguyên được yêu cầu.
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s không tồn tại với %s = '%s'", resourceName, fieldName, fieldValue));
    }
}
