package com.ecommerce.orderservice.service.impl;

import com.ecommerce.orderservice.client.CustomerClient;
import com.ecommerce.orderservice.dto.CustomerInfo;
import com.ecommerce.orderservice.dto.OrderDetailResponse;
import com.ecommerce.orderservice.entity.Order;
import com.ecommerce.orderservice.repository.OrderRepository;
import com.ecommerce.orderservice.service.OrderService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CustomerClient customerClient;

    @Override
    public OrderDetailResponse getOrderDetail(Long orderId) {
        // 1. Lấy đơn hàng từ order_db (chỉ có customerId, không có thông tin khách)
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order " + orderId + " không tồn tại"));

        // 2. Gọi Customer Service QUA API để lấy thông tin khách theo customerId
        CustomerInfo customer = customerClient.getCustomer(order.getCustomerId());

        // 3. Ghép dữ liệu từ 2 nguồn rồi trả về
        return OrderDetailResponse.builder()
                .orderId(order.getId())
                .customerId(order.getCustomerId())
                .customer(customer)
                .orderDate(order.getOrderDate())
                .totalAmount(order.getTotalAmount())
                .status(order.getStatus())
                .build();
    }

    @PostConstruct
    public void seed() {
        if (orderRepository.count() == 0) {
            orderRepository.save(Order.builder()
                    .customerId(1L)              // chỉ lưu ID khách hàng
                    .orderDate(LocalDateTime.now())
                    .totalAmount(new BigDecimal("199000"))
                    .status("PENDING").build());
        }
    }
}
