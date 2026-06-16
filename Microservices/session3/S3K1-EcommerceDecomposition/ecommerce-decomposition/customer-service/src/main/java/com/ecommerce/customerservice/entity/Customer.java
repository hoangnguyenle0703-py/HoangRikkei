package com.ecommerce.customerservice.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Thực thể Customer — sở hữu bởi Customer Service, lưu trong customer_db.
 */
@Entity
@Table(name = "customers")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String email;

    /** Mật khẩu (đã hash) — dữ liệu nhạy cảm, KHÔNG bao giờ trả ra ngoài */
    @Column(nullable = false)
    private String password;

    @Column
    private String address;
}
