package com.ecommerce.orderservice.service.impl;

import com.ecommerce.orderservice.client.ProductClient;
import com.ecommerce.orderservice.dto.OrderRequestDTO;
import com.ecommerce.orderservice.dto.OrderResponseDTO;
import com.ecommerce.orderservice.entity.Order;
import com.ecommerce.orderservice.exception.InvalidInputException;
import com.ecommerce.orderservice.exception.OrderPersistenceException;
import com.ecommerce.orderservice.exception.ResourceNotFoundException;
import com.ecommerce.orderservice.repository.OrderRepository;
import com.ecommerce.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductClient productClient;

    @Override
    @Transactional
    public OrderResponseDTO create(OrderRequestDTO request) {
        // Kiểm tra nghiệp vụ: quantity phải > 0
        if (request.getQuantity() == null || request.getQuantity() <= 0) {
            throw new InvalidInputException("quantity phải lớn hơn 0");
        }

        // Lấy giá sản phẩm từ Product Service (qua API), rồi tính totalAmount
        BigDecimal price = productClient.getProductPrice(request.getProductId());
        BigDecimal totalAmount = price.multiply(BigDecimal.valueOf(request.getQuantity()));

        Order order = Order.builder()
                .customerId(request.getCustomerId())
                .productId(request.getProductId())
                .orderDate(LocalDateTime.now())
                .totalAmount(totalAmount)
                .build();

        try {
            Order saved = orderRepository.save(order);
            return OrderResponseDTO.fromEntity(saved);
        } catch (Exception e) {
            // Lưu DB thất bại -> 500 Internal Server Error
            throw new OrderPersistenceException("Lưu đơn hàng vào cơ sở dữ liệu thất bại", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponseDTO getById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.order(id));
        return OrderResponseDTO.fromEntity(order);
    }
}
