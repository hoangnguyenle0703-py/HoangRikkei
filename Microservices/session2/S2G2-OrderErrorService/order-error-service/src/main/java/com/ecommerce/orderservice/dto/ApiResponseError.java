package com.ecommerce.orderservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Class chuẩn hóa định dạng lỗi trả về cho toàn hệ thống.
 * <p>
 * Mọi lỗi (404, 400, 500...) đều được trả ra dưới CÙNG MỘT cấu trúc này,
 * giúp Frontend dễ dàng xử lý và hiển thị thông báo cho người dùng.
 * <p>
 * Định dạng JSON đầu ra:
 * <pre>
 * {
 *   "timestamp": "2024-03-20T10:00:00",
 *   "status": 404,
 *   "error": "Not Found",
 *   "message": "Order với ID 999 không tồn tại trên hệ thống!"
 * }
 * </pre>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponseError {

    /** Thời điểm phát sinh lỗi */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timestamp;

    /** Mã trạng thái HTTP, ví dụ: 404, 400, 500 */
    private int status;

    /** Tên loại lỗi HTTP, ví dụ: "Not Found", "Bad Request" */
    private String error;

    /** Thông điệp mô tả chi tiết lỗi cho người dùng/Frontend */
    private String message;
}
