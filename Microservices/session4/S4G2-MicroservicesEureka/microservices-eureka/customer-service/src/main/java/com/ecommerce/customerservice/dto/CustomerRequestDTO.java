package com.ecommerce.customerservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * DTO nhận dữ liệu khi ĐĂNG KÝ khách hàng mới.
 * Chứa mật khẩu gốc do client gửi lên (sẽ được mã hóa trước khi lưu).
 */
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class CustomerRequestDTO {

    @NotBlank(message = "fullName không được để trống")
    private String fullName;

    @NotBlank(message = "email không được để trống")
    @Email(message = "email không hợp lệ")
    private String email;

    @NotBlank(message = "password không được để trống")
    @Size(min = 6, message = "password tối thiểu 6 ký tự")
    private String password;
}
