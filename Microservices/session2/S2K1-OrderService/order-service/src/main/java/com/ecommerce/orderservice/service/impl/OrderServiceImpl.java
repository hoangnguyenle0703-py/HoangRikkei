package com.ecommerce.orderservice.service.impl;

import com.ecommerce.orderservice.dto.OrderRequest;
import com.ecommerce.orderservice.dto.OrderResponse;
import com.ecommerce.orderservice.entity.Order;
import com.ecommerce.orderservice.exception.ResourceNotFoundException;
import com.ecommerce.orderservice.repository.OrderRepository;
import com.ecommerce.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Lớp cài đặt cụ thể cho {@link OrderService}.
 * <p>
 * Annotation {@code @Service} đánh dấu đây là một Spring Bean ở tầng nghiệp vụ.
 * Lớp này phụ thuộc vào {@link OrderRepository} thông qua constructor injection
 * (được Lombok sinh tự động qua {@code @RequiredArgsConstructor}).
 * <p>
 * Nguyên tắc kiến trúc: Controller gọi Service, Service gọi Repository.
 * Logic nghiệp vụ chỉ nằm ở đây, không bị lẫn vào Controller hay Repository.
 */
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public String healthCheck() {
        return "Order Service is Up";
    }

    @Override
    @Transactional
    public OrderResponse createOrder(OrderRequest request) {
        Order order = Order.builder()
                .orderCode(generateOrderCode())
                .customerId(request.getCustomerId())
                .totalAmount(request.getTotalAmount())
                .status(Order.OrderStatus.PENDING)
                .build();

        Order saved = orderRepository.save(order);
        return OrderResponse.fromEntity(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponse getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", id));
        return OrderResponse.fromEntity(order);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(OrderResponse::fromEntity)
                .toList();
    }

    /**
     * Sinh mã đơn hàng dạng: ORD-yyyyMMdd-XXXX (XXXX là số ngẫu nhiên 4 chữ số).
     */
    private String generateOrderCode() {
        String datePart = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        int randomPart = ThreadLocalRandom.current().nextInt(1000, 10000);
        return String.format("ORD-%s-%d", datePart, randomPart);
    }
}
