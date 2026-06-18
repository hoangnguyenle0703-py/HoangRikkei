package com.ecommerce.orderservice.service;

import com.ecommerce.orderservice.dto.OrderRequestDTO;
import com.ecommerce.orderservice.dto.OrderResponseDTO;

public interface OrderService {
    OrderResponseDTO create(OrderRequestDTO request);
    OrderResponseDTO getById(Long id);
}
