package com.ecommerce.orderservice.service;

import com.ecommerce.orderservice.dto.OrderResponse;

public interface OrderService {
    /** Lấy đơn hàng theo id; ném ResourceNotFoundException nếu không tồn tại */
    OrderResponse getOrderById(Long id);
}
