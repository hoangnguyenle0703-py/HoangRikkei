package com.ecommerce.orderservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Điểm khởi động (entry point) của Order Service.
 * <p>
 * Annotation {@code @SpringBootApplication} kích hoạt tự động cấu hình,
 * quét component và khai báo cấu hình cho toàn bộ ứng dụng.
 */
@SpringBootApplication
public class OrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }
}
