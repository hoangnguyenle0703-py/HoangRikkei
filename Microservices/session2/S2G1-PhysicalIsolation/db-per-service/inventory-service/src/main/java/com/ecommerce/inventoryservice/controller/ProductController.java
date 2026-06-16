package com.ecommerce.inventoryservice.controller;

import com.ecommerce.inventoryservice.dto.DbInfoDTO;
import com.ecommerce.inventoryservice.dto.ProductResponseDTO;
import com.ecommerce.inventoryservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller cho Inventory-Service.
 */
@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    /** GET /api/v1/products → danh sách sản phẩm trong inventory_db */
    @GetMapping
    public List<ProductResponseDTO> getAllProducts() {
        return productService.getAllProducts();
    }

    /**
     * GET /api/v1/products/db-info
     * Trả về thông tin kết nối DB — dùng để chứng minh service trỏ tới inventory_db.
     */
    @GetMapping("/db-info")
    public DbInfoDTO getDatabaseInfo() {
        return productService.getDatabaseInfo();
    }
}
