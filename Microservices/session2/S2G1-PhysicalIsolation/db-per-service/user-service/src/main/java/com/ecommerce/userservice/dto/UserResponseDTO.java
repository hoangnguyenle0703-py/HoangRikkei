package com.ecommerce.userservice.dto;

import com.ecommerce.userservice.entity.User;
import lombok.*;

/**
 * DTO trả thông tin user ra ngoài (không lộ trường nội bộ).
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDTO {

    private Long id;
    private String username;
    private String email;
    private String fullName;

    public static UserResponseDTO fromEntity(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .build();
    }
}
