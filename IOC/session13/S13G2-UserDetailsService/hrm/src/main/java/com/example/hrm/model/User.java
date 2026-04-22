package com.example.hrm.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "users") // Tránh đặt tên bảng là 'user' vì dễ trùng từ khóa SQL
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private String role;

    private boolean enabled = true;
}