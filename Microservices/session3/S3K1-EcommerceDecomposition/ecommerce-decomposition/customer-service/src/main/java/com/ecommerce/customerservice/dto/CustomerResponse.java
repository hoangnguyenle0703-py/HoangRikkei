package com.ecommerce.customerservice.dto;

import com.ecommerce.customerservice.entity.Customer;
import lombok.*;

/**
 * DTO trả thông tin khách hàng — KHÔNG chứa password.
 */
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class CustomerResponse {
    private Long id;
    private String fullName;
    private String email;
    private String address;

    public static CustomerResponse fromEntity(Customer c) {
        return CustomerResponse.builder()
                .id(c.getId()).fullName(c.getFullName())
                .email(c.getEmail()).address(c.getAddress()).build();
    }
}
