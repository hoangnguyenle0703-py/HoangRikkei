package com.ecommerce.orderservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Thực thể Order — sở hữu bởi Order Service, lưu trong order_db.
 * <p>
 * ĐIỂM MẤU CHỐT: Order chỉ lưu {@code customerId} (một số Long), KHÔNG nhúng
 * cả đối tượng Customer. Dữ liệu khách hàng thuộc về Customer Service; khi cần
 * Order Service gọi sang Customer Service qua REST API.
 */
@Entity
@Table(name = "orders")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Chỉ lưu ID tham chiếu tới khách hàng ở Customer Service (không phải FK CSDL) */
    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @Column(name = "order_date", nullable = false)
    private LocalDateTime orderDate;

    @Column(name = "total_amount", nullable = false)
    private BigDecimal totalAmount;

    @Column(nullable = false)
    private String status;
}
