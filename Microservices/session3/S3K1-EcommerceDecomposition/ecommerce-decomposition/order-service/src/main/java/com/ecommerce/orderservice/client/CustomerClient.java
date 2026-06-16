package com.ecommerce.orderservice.client;

import com.ecommerce.orderservice.dto.CustomerInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Client gọi sang Customer Service QUA REST API.
 * <p>
 * ĐIỂM MẤU CHỐT VỀ RANH GIỚI: Order Service KHÔNG import code, KHÔNG truy cập
 * database của Customer Service. Nó chỉ giao tiếp qua HTTP API công khai.
 * Đây là cách hai service tách biệt mà vẫn phối hợp được.
 */
@Component
public class CustomerClient {

    private final WebClient webClient;

    public CustomerClient(@Value("${customer-service.url:http://localhost:8081}") String baseUrl) {
        this.webClient = WebClient.builder().baseUrl(baseUrl).build();
    }

    /** Lấy thông tin khách hàng theo id từ Customer Service */
    public CustomerInfo getCustomer(Long customerId) {
        return webClient.get()
                .uri("/api/v1/customers/{id}", customerId)
                .retrieve()
                .bodyToMono(CustomerInfo.class)
                .block();
    }
}
