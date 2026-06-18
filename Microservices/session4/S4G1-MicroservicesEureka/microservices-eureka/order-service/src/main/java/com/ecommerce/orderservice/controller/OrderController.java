package com.ecommerce.orderservice.controller;

import com.ecommerce.orderservice.dto.OrderRequestDTO;
import com.ecommerce.orderservice.dto.OrderResponseDTO;
import com.ecommerce.orderservice.dto.ProductInfo;
import com.ecommerce.orderservice.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    /** POST /api/v1/orders — tạo đơn hàng (giá lấy từ Product Service qua Eureka) */
    @PostMapping
    public ResponseEntity<OrderResponseDTO> create(@Valid @RequestBody OrderRequestDTO request) {
        return new ResponseEntity<>(orderService.create(request), HttpStatus.CREATED);
    }

    /** GET /api/v1/orders/{id} — trả thông tin đơn hàng */
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getById(id));
    }

    /**
     * GET /api/v1/orders/getProduct/{id}
     * Order Service hỏi Eureka để tìm PRODUCT-SERVICE rồi gọi lấy sản phẩm.
     * - Tìm được & gọi OK -> trả thông tin sản phẩm (200).
     * - Không tìm thấy instance / service bị tắt -> 503 Service Unavailable.
     */
    @GetMapping("/getProduct/{id}")
    public ResponseEntity<ProductInfo> getProduct(@PathVariable("id") Long productId) {
        return ResponseEntity.ok(orderService.getProductFromProductService(productId));
    }
}
