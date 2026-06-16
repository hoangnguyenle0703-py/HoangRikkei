package com.ecommerce.orderservice.controller;

import com.ecommerce.orderservice.dto.OrderResponse;
import com.ecommerce.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * Controller cho đơn hàng.
 * <p>
 * Lưu ý: Controller KHÔNG có khối try-catch. Khi service ném
 * ResourceNotFoundException, GlobalExceptionHandler sẽ tự bắt và trả lỗi chuẩn.
 */
@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    /**
     * GET /api/v1/orders/{id}
     * - id tồn tại  -> trả OrderResponse (200)
     * - id không tồn tại -> ném lỗi -> trả ApiResponseError (404)
     */
    @GetMapping("/{id}")
    public OrderResponse getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }
}
