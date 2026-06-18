package com.ecommerce.orderservice.service;

import com.ecommerce.orderservice.dto.OrderRequestDTO;
import com.ecommerce.orderservice.dto.OrderResponseDTO;
import com.ecommerce.orderservice.dto.ProductInfo;

public interface OrderService {
    OrderResponseDTO create(OrderRequestDTO request);
    OrderResponseDTO getById(Long id);

    /** Lấy sản phẩm từ Product Service qua RestTemplate @LoadBalanced (cân bằng tải) */
    ProductInfo getProductFromProductService(Long productId);
}
