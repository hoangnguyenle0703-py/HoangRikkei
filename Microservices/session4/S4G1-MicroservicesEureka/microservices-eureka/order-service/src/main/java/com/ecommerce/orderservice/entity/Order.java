package com.ecommerce.orderservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Thực thể Order, ánh xạ bảng orders trong order_db (PostgreSQL).
 * Gồm: id, customerId, productId, orderDate, totalAmount.
 * <p>
 * LƯU Ý QUAN TRỌNG VỀ THIẾT KẾ:
 * Vì Customer và Product nằm ở DB riêng (customer_db, product_db), nên trong
 * bảng Order tại order_db KHÔNG thể dùng @ManyToOne hay khóa ngoại (@JoinColumn)
 * vật lý sang các bảng kia. Chỉ lưu customerId và productId dưới dạng số (Long)
 * — gọi là "tham chiếu mềm" (soft reference).
 */
@Entity
@Table(name = "orders")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** ID khách hàng (tham chiếu logic tới Customer Service, KHÔNG phải FK) */
    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    /** ID sản phẩm (tham chiếu logic tới Product Service, KHÔNG phải FK) */
    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "order_date", nullable = false)
    private LocalDateTime orderDate;

    @Column(name = "total_amount", nullable = false)
    private BigDecimal totalAmount;
}
