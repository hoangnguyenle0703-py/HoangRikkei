package com.ecommerce.productservice.dto;

import com.ecommerce.productservice.entity.ProductEntity;
import lombok.*;

import java.math.BigDecimal;

/**
 * DTO (Data Transfer Object) trả thông tin sản phẩm ra cho KHÁCH HÀNG.
 * <p>
 * Đây là "lớp bọc dữ liệu" — chỉ chứa những trường AN TOÀN để công khai:
 * {@code id}, {@code name}, {@code sellPrice}.
 * <p>
 * Các trường nhạy cảm của {@link ProductEntity} như giá nhập
 * ({@code importPrice}), mã kho ({@code sku}), số lượng tồn ({@code stockQuantity})
 * KHÔNG xuất hiện ở đây → khi serialize sang JSON, chúng hoàn toàn không bị lộ.
 * <p>
 * Đây là câu trả lời cho câu hỏi "tại sao không nên trả về Entity trực tiếp":
 * trả Entity thẳng ra sẽ phơi bày toàn bộ thông tin nội bộ doanh nghiệp.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponseDTO {

    private Long id;
    private String name;
    private BigDecimal sellPrice;

    /**
     * Hàm chuyển đổi (mapping) từ Entity sang DTO.
     * <p>
     * Chỉ copy 3 trường an toàn; mọi trường nhạy cảm bị "lọc" ở bước này.
     * Trong dự án lớn nên dùng thư viện MapStruct để tự sinh mapper, ở đây
     * viết tay cho dễ hình dung bản chất.
     */
    public static ProductResponseDTO fromEntity(ProductEntity entity) {
        return ProductResponseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .sellPrice(entity.getSellPrice())
                .build();
    }
}
