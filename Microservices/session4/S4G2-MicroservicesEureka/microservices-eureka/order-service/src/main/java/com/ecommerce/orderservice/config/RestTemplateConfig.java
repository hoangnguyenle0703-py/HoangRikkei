package com.ecommerce.orderservice.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Cấu hình RestTemplate có khả năng CÂN BẰNG TẢI (Client-side Load Balancing).
 * <p>
 * Annotation @LoadBalanced "nâng cấp" RestTemplate: khi gọi tới một URL dùng
 * TÊN SERVICE (vd http://PRODUCT-SERVICE/...), nó sẽ:
 *   1. Hỏi Eureka lấy danh sách các instance của PRODUCT-SERVICE.
 *   2. Tự chọn một instance theo thuật toán cân bằng tải (mặc định round-robin).
 *   3. Thay tên service bằng host:port thật của instance được chọn rồi gọi.
 * <p>
 * Nhờ vậy code chỉ cần viết tên service, không quan tâm có bao nhiêu instance
 * hay chúng chạy ở port nào — tải được chia đều tự động.
 */
@Configuration
public class RestTemplateConfig {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
