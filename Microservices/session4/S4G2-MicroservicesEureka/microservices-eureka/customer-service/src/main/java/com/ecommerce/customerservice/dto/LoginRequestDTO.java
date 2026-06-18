package com.ecommerce.customerservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * DTO nhận dữ liệu khi ĐĂNG NHẬP: email + password.
 */
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class LoginRequestDTO {

    @NotBlank(message = "email không được để trống")
    private String email;

    @NotBlank(message = "password không được để trống")
    private String password;
}
