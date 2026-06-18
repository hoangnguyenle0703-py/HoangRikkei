package com.ecommerce.orderservice.client;

import com.ecommerce.orderservice.dto.ProductInfo;
import com.ecommerce.orderservice.exception.ServiceUnavailableException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

/**
 * Client gọi sang Product Service bằng RestTemplate có @LoadBalanced.
 * <p>
 * Điểm mấu chốt: chỉ cần gọi URL dùng TÊN SERVICE — http://PRODUCT-SERVICE/...
 * KHÔNG quan tâm service đang chạy ở port nào, có bao nhiêu instance.
 * RestTemplate @LoadBalanced sẽ tự:
 *   - hỏi Eureka danh sách instance của PRODUCT-SERVICE,
 *   - chọn 1 instance theo round-robin (cân bằng tải),
 *   - thay tên service bằng host:port thật rồi gọi.
 * <p>
 * Khi chạy 2 instance Product Service (vd port 8082 và 8084), gọi nhiều lần thì
 * request được chia đều cho cả 2 instance.
 */
@Component
@RequiredArgsConstructor
public class ProductClient {

    private final RestTemplate restTemplate;

    /** Gọi bằng TÊN service, không phải host:port cứng */
    private static final String PRODUCT_SERVICE_URL = "http://PRODUCT-SERVICE/api/v1/products/";

    /**
     * Lấy thông tin sản phẩm theo id qua RestTemplate cân bằng tải.
     */
    public ProductInfo getProduct(Long productId) {
        try {
            String targetUrl = PRODUCT_SERVICE_URL + productId;
            return restTemplate.getForObject(targetUrl, ProductInfo.class);
        } catch (Exception e) {
            // Không có instance nào của PRODUCT-SERVICE / gọi thất bại -> 503
            throw new ServiceUnavailableException(
                    "PRODUCT-SERVICE hiện không khả dụng: " + e.getMessage());
        }
    }

    /**
     * Lấy giá sản phẩm (phục vụ tính totalAmount khi tạo đơn).
     */
    public BigDecimal getProductPrice(Long productId) {
        ProductInfo product = getProduct(productId);
        if (product != null && product.getPrice() != null) {
            return product.getPrice();
        }
        throw new ServiceUnavailableException("Không lấy được giá từ PRODUCT-SERVICE");
    }
}
