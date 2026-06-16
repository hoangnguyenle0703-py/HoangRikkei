package com.ecommerce.productservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entity đại diện cho một Sản phẩm trong cơ sở dữ liệu.
 * <p>
 * Theo quy tắc đặt tên: tên class dạng PascalCase. Đề bài yêu cầu tên
 * {@code ProductEntity} nên giữ nguyên hậu tố "Entity" để nhấn mạnh đây là
 * đối tượng tầng dữ liệu.
 * <p>
 * <b>Lưu ý bảo mật:</b> Entity này chứa các trường NHẠY CẢM mang tính nội bộ
 * doanh nghiệp — {@code importPrice} (giá nhập), {@code sku} (mã kho),
 * {@code stockQuantity} (số lượng tồn) — TUYỆT ĐỐI không được trả thẳng ra cho
 * khách hàng. Đây chính là lý do phải dùng DTO để lọc dữ liệu trước khi trả về.
 */
@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Tên sản phẩm — thông tin công khai */
    @Column(name = "name", nullable = false)
    private String name;

    /** Mã kho (Stock Keeping Unit) — THÔNG TIN NỘI BỘ, không công khai */
    @Column(name = "sku", nullable = false, unique = true)
    private String sku;

    /** Giá nhập — THÔNG TIN NHẠY CẢM, lộ ra sẽ tiết lộ biên lợi nhuận */
    @Column(name = "import_price", nullable = false)
    private BigDecimal importPrice;

    /** Giá bán — thông tin công khai cho khách hàng */
    @Column(name = "sell_price", nullable = false)
    private BigDecimal sellPrice;

    /** Số lượng tồn kho — THÔNG TIN NỘI BỘ, đối thủ có thể lợi dụng */
    @Column(name = "stock_quantity", nullable = false)
    private Integer stockQuantity;

    /** Ngày cập nhật — thông tin nội bộ */
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    @PreUpdate
    protected void onSave() {
        this.updatedAt = LocalDateTime.now();
    }
}
