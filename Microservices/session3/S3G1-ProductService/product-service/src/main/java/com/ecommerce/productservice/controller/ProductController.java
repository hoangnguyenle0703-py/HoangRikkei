package com.ecommerce.productservice.controller;

import com.ecommerce.productservice.dto.ProductRequestDTO;
import com.ecommerce.productservice.dto.ProductResponseDTO;
import com.ecommerce.productservice.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    /**
     * POST /api/v1/products — tạo sản phẩm mới.
     * @Valid kích hoạt Bean Validation; nếu dữ liệu sai (vd price = -500),
     * Spring ném MethodArgumentNotValidException -> GlobalExceptionHandler bắt -> 400.
     */
    @PostMapping
    public ResponseEntity<ProductResponseDTO> create(@Valid @RequestBody ProductRequestDTO request) {
        return new ResponseEntity<>(productService.create(request), HttpStatus.CREATED);
    }

    /** GET /api/v1/products/{id} — lấy chi tiết sản phẩm */
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getById(id));
    }

    /** GET /api/v1/products — lấy danh sách toàn bộ sản phẩm */
    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAll() {
        return ResponseEntity.ok(productService.getAll());
    }
}
