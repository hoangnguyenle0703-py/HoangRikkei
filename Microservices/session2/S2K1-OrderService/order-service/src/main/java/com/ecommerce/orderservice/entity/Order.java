package com.ecommerce.orderservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entity đại diện cho một Đơn hàng trong hệ thống.
 * <p>
 * Theo quy tắc đặt tên: tên Entity là danh từ số ít, dạng PascalCase — {@code Order}.
 * Đây là đối tượng được ánh xạ trực tiếp tới bảng {@code orders} trong cơ sở dữ liệu.
 */
@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Mã đơn hàng duy nhất, ví dụ: ORD-20260615-0001 */
    @Column(name = "order_code", nullable = false, unique = true)
    private String orderCode;

    /** ID của khách hàng đặt đơn (tham chiếu sang User Service) */
    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    /** Tổng giá trị đơn hàng */
    @Column(name = "total_amount", nullable = false)
    private BigDecimal totalAmount;

    /** Trạng thái đơn hàng: PENDING, CONFIRMED, SHIPPING, COMPLETED, CANCELLED */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderStatus status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /** Tự động gán thời gian khi tạo mới bản ghi */
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (this.status == null) {
            this.status = OrderStatus.PENDING;
        }
    }

    /** Tự động cập nhật thời gian khi sửa bản ghi */
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Enum mô tả các trạng thái trong vòng đời của một đơn hàng.
     */
    public enum OrderStatus {
        PENDING,    // Chờ xác nhận
        CONFIRMED,  // Đã xác nhận
        SHIPPING,   // Đang giao
        COMPLETED,  // Hoàn thành
        CANCELLED   // Đã hủy
    }
}
