package com.ecommerce.orderservice.client;

import com.ecommerce.orderservice.dto.ProductInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;

/**
 * Client gọi sang Product Service QUA REST API để lấy thông tin sản phẩm (giá).
 * <p>
 * Vì Product nằm ở DB/Service riêng, Order Service không truy cập trực tiếp
 * product_db mà phải gọi API. Ở đây có cơ chế fallback giả lập giá nếu Product
 * Service chưa chạy, để bài tập vẫn demo được việc tính totalAmount.
 */
@Component
public class ProductClient {

    private final WebClient webClient;

    public ProductClient(@Value("${product-service.url:http://localhost:8082}") String baseUrl) {
        this.webClient = WebClient.builder().baseUrl(baseUrl).build();
    }

    /**
     * Lấy giá sản phẩm theo productId từ Product Service.
     * Nếu không gọi được (Product Service chưa chạy), dùng giá giả lập để demo.
     */
    public BigDecimal getProductPrice(Long productId) {
        try {
            ProductInfo product = webClient.get()
                    .uri("/api/v1/products/{id}", productId)
                    .retrieve()
                    .bodyToMono(ProductInfo.class)
                    .block();
            if (product != null && product.getPrice() != null) {
                return product.getPrice();
            }
        } catch (Exception e) {
            // Fallback: giả lập giá (mục tiêu bài tập là minh họa luồng tính totalAmount)
        }
        return new BigDecimal("100000"); // giá giả lập mặc định
    }
}
