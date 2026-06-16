package com.ecommerce.orderservice.service;

import com.ecommerce.orderservice.dto.OrderDetailResponse;

public interface OrderService {
    /** Lấy chi tiết đơn hàng kèm thông tin khách (ghép từ Customer Service) */
    OrderDetailResponse getOrderDetail(Long orderId);
}
