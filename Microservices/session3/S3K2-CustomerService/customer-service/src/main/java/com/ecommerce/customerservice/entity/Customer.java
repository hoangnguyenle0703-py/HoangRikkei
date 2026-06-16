package com.ecommerce.customerservice.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Thực thể Customer, ánh xạ bảng customers trong customer_db (PostgreSQL).
 */
@Entity
@Table(name = "customers")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    /** Mật khẩu đã được mã hóa BCrypt — không lưu mật khẩu gốc */
    @Column(name = "password", nullable = false)
    private String password;
}
