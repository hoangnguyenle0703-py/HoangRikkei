package com.ecommerce.productservice.controller;

import com.ecommerce.productservice.dto.ApiResponse;
import com.ecommerce.productservice.dto.ProductResponseDTO;
import com.ecommerce.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Tầng Controller cho sản phẩm.
 * <p>
 * <b>Điểm cốt lõi của bài tập:</b> Controller trả về {@link ProductResponseDTO}
 * (đã được map và lọc dữ liệu từ Entity ở tầng Service), KHÔNG bao giờ trả
 * {@code ProductEntity} trực tiếp. Nhờ đó JSON gửi cho khách hàng chỉ chứa
 * id, name, sellPrice — mọi thông tin nội bộ (giá nhập, mã kho, tồn kho) được
 * bảo mật.
 */
@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    /**
     * Lấy thông tin một sản phẩm cho khách hàng.
     * GET /api/v1/products/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponseDTO>> getProductById(@PathVariable Long id) {
        ProductResponseDTO product = productService.getProductById(id);
        return ResponseEntity.ok(ApiResponse.success("Lấy sản phẩm thành công", product));
    }

    /**
     * Lấy danh sách sản phẩm cho khách hàng.
     * GET /api/v1/products
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductResponseDTO>>> getAllProducts() {
        List<ProductResponseDTO> products = productService.getAllProducts();
        return ResponseEntity.ok(ApiResponse.success("Lấy danh sách sản phẩm thành công", products));
    }
}
