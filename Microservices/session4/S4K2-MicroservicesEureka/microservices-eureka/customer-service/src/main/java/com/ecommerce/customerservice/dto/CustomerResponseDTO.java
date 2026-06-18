package com.ecommerce.customerservice.dto;

import com.ecommerce.customerservice.entity.Customer;
import lombok.*;

/**
 * DTO trả dữ liệu khách hàng ra ngoài.
 * KHÔNG bao giờ chứa password — bảo vệ thông tin nhạy cảm.
 */
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class CustomerResponseDTO {

    private Long id;
    private String fullName;
    private String email;

    public static CustomerResponseDTO fromEntity(Customer c) {
        return CustomerResponseDTO.builder()
                .id(c.getId())
                .fullName(c.getFullName())
                .email(c.getEmail())
                .build();
    }
}
