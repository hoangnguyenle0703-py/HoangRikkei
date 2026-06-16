package com.ecommerce.orderservice.service.impl;

import com.ecommerce.orderservice.dto.OrderResponse;
import com.ecommerce.orderservice.entity.Order;
import com.ecommerce.orderservice.exception.ResourceNotFoundException;
import com.ecommerce.orderservice.repository.OrderRepository;
import com.ecommerce.orderservice.service.OrderService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    @Transactional(readOnly = true)
    public OrderResponse getOrderById(Long id) {
        // Nếu không tìm thấy trong PostgreSQL -> ném custom exception
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order", id));
        return OrderResponse.fromEntity(order);
    }

    /** Nạp sẵn vài đơn hàng (id 1, 2) để test trường hợp tồn tại và không tồn tại */
    @PostConstruct
    public void seed() {
        if (orderRepository.count() == 0) {
            orderRepository.save(Order.builder()
                    .orderCode("ORD-0001").customerName("Nguyễn Văn Huy")
                    .totalAmount(new BigDecimal("199000")).build());
            orderRepository.save(Order.builder()
                    .orderCode("ORD-0002").customerName("Trần Thị An")
                    .totalAmount(new BigDecimal("499000")).build());
        }
    }
}
