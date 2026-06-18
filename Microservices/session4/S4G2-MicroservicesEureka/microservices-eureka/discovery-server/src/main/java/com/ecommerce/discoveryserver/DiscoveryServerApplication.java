package com.ecommerce.discoveryserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Eureka Server — "Trạm điều hướng" (Service Registry) cho hệ thống Microservices.
 * <p>
 * Annotation @EnableEurekaServer biến ứng dụng Spring Boot này thành một
 * "cuốn danh bạ" trung tâm: các service khác (Customer, Product, Order) đăng ký
 * địa chỉ của mình tại đây, và tra cứu địa chỉ của nhau qua Eureka thay vì phải
 * nhớ IP/port cứng của từng service.
 */
@SpringBootApplication
@EnableEurekaServer
public class DiscoveryServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DiscoveryServerApplication.class, args);
    }
}
