package com.ecommerce.orderservice.controller;

import com.ecommerce.orderservice.dto.OrderDetailResponse;
import com.ecommerce.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    /**
     * GET /api/v1/orders/{id}
     * Trả về đơn hàng kèm thông tin khách hàng (ghép từ Customer Service qua API).
     */
    @GetMapping("/{id}")
    public OrderDetailResponse getOrderDetail(@PathVariable Long id) {
        return orderService.getOrderDetail(id);
    }
}
