package com.ecommerce.orderservice.client;

import com.ecommerce.orderservice.dto.ProductInfo;
import com.ecommerce.orderservice.exception.ServiceUnavailableException;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.util.List;

/**
 * Client gọi sang Product Service bằng cách HỎI EUREKA (DiscoveryClient),
 * KHÔNG dùng URL cứng như http://localhost:8082.
 * <p>
 * Luồng hoạt động:
 *  - Hỏi Eureka: "PRODUCT-SERVICE đang ở đâu?" -> nhận về danh sách instance.
 *  - Lấy địa chỉ thật (host:port) của một instance.
 *  - Ghép URL và gọi API lấy sản phẩm.
 * <p>
 * Nhờ vậy, dù Product Service đổi port (vd 8082 -> 9090), Order Service vẫn gọi
 * thành công mà KHÔNG cần sửa code, vì địa chỉ được lấy động từ Eureka.
 */
@Component
@RequiredArgsConstructor
public class ProductClient {

    private final DiscoveryClient discoveryClient;
    private final RestClient restClient = RestClient.create();

    /** Tên service đã đăng ký trên Eureka (không phân biệt hoa thường) */
    private static final String PRODUCT_SERVICE_ID = "PRODUCT-SERVICE";

    /**
     * Lấy thông tin sản phẩm theo id từ Product Service, qua Eureka.
     */
    public ProductInfo getProduct(Long productId) {
        // BƯỚC A: Hỏi Eureka lấy danh sách instance đang chạy của PRODUCT-SERVICE
        List<ServiceInstance> instances = discoveryClient.getInstances(PRODUCT_SERVICE_ID);

        // BƯỚC B: Xử lý lỗi - nếu không có instance nào, trả về 503
        if (instances == null || instances.isEmpty()) {
            throw new ServiceUnavailableException(
                    "PRODUCT-SERVICE hiện không khả dụng (không tìm thấy instance nào)");
        }

        // BƯỚC C: Lấy instance đầu tiên (sau này có thể random/round-robin để cân bằng tải)
        ServiceInstance productInstance = instances.get(0);

        // BƯỚC D: Lấy URL gốc (vd http://192.168.1.10:9090) từ instance
        String baseUrl = productInstance.getUri().toString();

        // BƯỚC E: Nối chuỗi tạo URL hoàn chỉnh
        String targetUrl = baseUrl + "/api/v1/products/" + productId;

        // BƯỚC F: Gọi API lấy về sản phẩm
        try {
            return restClient.get()
                    .uri(targetUrl)
                    .retrieve()
                    .body(ProductInfo.class);
        } catch (Exception e) {
            throw new ServiceUnavailableException(
                    "Không gọi được PRODUCT-SERVICE: " + e.getMessage());
        }
    }

    /**
     * Lấy giá sản phẩm (phục vụ tính totalAmount khi tạo đơn).
     * Dùng lại getProduct ở trên để đảm bảo vẫn đi qua Eureka.
     */
    public BigDecimal getProductPrice(Long productId) {
        ProductInfo product = getProduct(productId);
        if (product != null && product.getPrice() != null) {
            return product.getPrice();
        }
        throw new ServiceUnavailableException("Không lấy được giá từ PRODUCT-SERVICE");
    }
}
