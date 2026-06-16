package com.ecommerce.orderservice.service;

import com.ecommerce.orderservice.dto.OrderRequest;
import com.ecommerce.orderservice.dto.OrderResponse;

import java.util.List;

/**
 * Tầng Service: chứa logic nghiệp vụ (business logic layer).
 * <p>
 * Theo quy tắc đặt tên: tên Service = tên Entity + hậu tố "Service" → {@code OrderService}.
 * <p>
 * Việc tách interface khỏi lớp cài đặt ({@code OrderServiceImpl}) tuân theo
 * nguyên tắc lập trình hướng giao diện (program to an interface), giúp dễ
 * thay thế cài đặt và dễ viết unit test (mock).
 */
public interface OrderService {

    /** Kiểm tra tình trạng hoạt động của service */
    String healthCheck();

    /** Tạo một đơn hàng mới */
    OrderResponse createOrder(OrderRequest request);

    /** Lấy thông tin đơn hàng theo id */
    OrderResponse getOrderById(Long id);

    /** Lấy danh sách toàn bộ đơn hàng */
    List<OrderResponse> getAllOrders();
}
