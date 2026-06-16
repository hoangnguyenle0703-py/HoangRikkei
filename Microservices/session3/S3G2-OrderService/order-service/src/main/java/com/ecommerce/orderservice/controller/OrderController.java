package com.ecommerce.orderservice.controller;

import com.ecommerce.orderservice.dto.OrderRequestDTO;
import com.ecommerce.orderservice.dto.OrderResponseDTO;
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

    /**
     * POST /api/v1/orders — nhận customerId, productId, quantity.
     * Tính totalAmount (lấy giá từ Product Service) rồi lưu vào PostgreSQL.
     */
    @PostMapping
    public ResponseEntity<OrderResponseDTO> create(@Valid @RequestBody OrderRequestDTO request) {
        return new ResponseEntity<>(orderService.create(request), HttpStatus.CREATED);
    }

    /** GET /api/v1/orders/{id} — trả thông tin đơn hàng */
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getById(id));
    }
}
