package com.ecommerce.productservice.controller;

import com.ecommerce.productservice.dto.ProductRequestDTO;
import com.ecommerce.productservice.dto.ProductResponseDTO;
import com.ecommerce.productservice.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;

    /** Port của instance hiện tại — dùng để thấy request rơi vào instance nào */
    @Value("${server.port}")
    private String port;

    @PostMapping
    public ResponseEntity<ProductResponseDTO> create(@Valid @RequestBody ProductRequestDTO request) {
        return new ResponseEntity<>(productService.create(request), HttpStatus.CREATED);
    }

    /**
     * GET /api/v1/products/{id} — lấy chi tiết sản phẩm.
     * In log để CHỨNG MINH cân bằng tải: mỗi lần gọi sẽ thấy instance nào (port nào)
     * xử lý request, qua đó kiểm chứng yêu cầu được chia đều giữa 2 instance.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getById(@PathVariable Long id) {
        log.info(">>> [PRODUCT-SERVICE port {}] xử lý request lấy sản phẩm id={}", port, id);
        return ResponseEntity.ok(productService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAll() {
        log.info(">>> [PRODUCT-SERVICE port {}] xử lý request lấy danh sách sản phẩm", port);
        return ResponseEntity.ok(productService.getAll());
    }
}
