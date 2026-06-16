package com.ecommerce.productservice.service;

import com.ecommerce.productservice.dto.ProductResponseDTO;

import java.util.List;

/**
 * Tầng Service: logic nghiệp vụ cho sản phẩm.
 * <p>
 * Điểm mấu chốt: các phương thức trả về {@link ProductResponseDTO}, KHÔNG trả
 * về Entity. Việc chuyển đổi Entity → DTO được thực hiện ngay trong tầng này,
 * đảm bảo dữ liệu nhạy cảm không bao giờ "đi" lên tới Controller.
 */
public interface ProductService {

    /** Lấy thông tin một sản phẩm cho khách hàng (đã lọc dữ liệu nhạy cảm) */
    ProductResponseDTO getProductById(Long id);

    /** Lấy danh sách sản phẩm cho khách hàng */
    List<ProductResponseDTO> getAllProducts();
}
