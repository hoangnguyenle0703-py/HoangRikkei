package com.ecommerce.orderservice.controller;

import com.ecommerce.orderservice.dto.ApiResponse;
import com.ecommerce.orderservice.dto.OrderRequest;
import com.ecommerce.orderservice.dto.OrderResponse;
import com.ecommerce.orderservice.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Tầng Controller: điểm tiếp nhận request HTTP (presentation layer).
 * <p>
 * Theo quy tắc đặt tên: tên Controller = tên Entity + hậu tố "Controller" → {@code OrderController}.
 * <p>
 * Đường dẫn gốc {@code /api/v1/orders} áp dụng chuẩn versioning (v1) cho REST API.
 * Controller chỉ làm nhiệm vụ điều phối: nhận request, gọi Service, trả response —
 * KHÔNG chứa logic nghiệp vụ.
 */
@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    /**
     * API kiểm tra tình trạng hoạt động của service.
     * <p>
     * GET /api/v1/orders/health-check → trả về chuỗi "Order Service is Up".
     */
    @GetMapping("/health-check")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok(orderService.healthCheck());
    }

    /**
     * API tạo đơn hàng mới.
     * POST /api/v1/orders
     */
    @PostMapping
    public ResponseEntity<ApiResponse<OrderResponse>> createOrder(
            @Valid @RequestBody OrderRequest request) {
        OrderResponse created = orderService.createOrder(request);
        ApiResponse<OrderResponse> body =
                ApiResponse.success("Tạo đơn hàng thành công", created);
        return new ResponseEntity<>(body, HttpStatus.CREATED);
    }

    /**
     * API lấy đơn hàng theo id.
     * GET /api/v1/orders/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderResponse>> getOrderById(@PathVariable Long id) {
        OrderResponse order = orderService.getOrderById(id);
        return ResponseEntity.ok(ApiResponse.success("Lấy đơn hàng thành công", order));
    }

    /**
     * API lấy danh sách toàn bộ đơn hàng.
     * GET /api/v1/orders
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<OrderResponse>>> getAllOrders() {
        List<OrderResponse> orders = orderService.getAllOrders();
        return ResponseEntity.ok(ApiResponse.success("Lấy danh sách đơn hàng thành công", orders));
    }
}
