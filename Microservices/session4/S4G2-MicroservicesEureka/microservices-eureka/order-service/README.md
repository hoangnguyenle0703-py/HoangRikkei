# Order Service — Cân bằng tải phía Client với @LoadBalanced

Order Service gọi Product Service bằng **tên service** (`http://PRODUCT-SERVICE/...`) qua `RestTemplate` có `@LoadBalanced`. Khi chạy nhiều instance Product Service, request được **chia đều tự động** mà không cần quan tâm port.

---

## 1. Mục tiêu

- **Kiến thức**: Cơ chế Load Balancing cơ bản.
- **Kỹ năng**: Tích hợp Load Balancer vào RestTemplate.

---

## 2. Cấu hình Bean @LoadBalanced

```java
@Configuration
public class RestTemplateConfig {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
```

`@LoadBalanced` "nâng cấp" RestTemplate: khi gọi URL dùng tên service, nó tự hỏi Eureka danh sách instance, chọn 1 theo round-robin, rồi thay tên bằng host:port thật.

---

## 3. Gọi bằng tên service (không cần port)

```java
@Component
@RequiredArgsConstructor
public class ProductClient {

    private final RestTemplate restTemplate;  // bean @LoadBalanced

    // Dùng TÊN service, không phải localhost:8082
    private static final String PRODUCT_SERVICE_URL =
        "http://PRODUCT-SERVICE/api/v1/products/";

    public ProductInfo getProduct(Long productId) {
        String targetUrl = PRODUCT_SERVICE_URL + productId;
        return restTemplate.getForObject(targetUrl, ProductInfo.class);
    }
}
```

So sánh với bài trước:

| DiscoveryClient (bài trước) | @LoadBalanced RestTemplate (bài này) |
|------------------------------|--------------------------------------|
| Tự gọi `getInstances()`, tự lấy URI, tự ghép URL | Chỉ viết `http://PRODUCT-SERVICE/...` |
| Tự chọn instance (get(0)) | Tự động round-robin giữa các instance |
| Nhiều dòng code thủ công | Gọn, framework lo cân bằng tải |

---

## 4. Cách chạy 2 instance Product Service

`product-service/application.yml` đã để port linh hoạt: `server.port=${PORT:8082}`.

**Instance 1 (port 8082):**
```bash
cd product-service
mvn spring-boot:run                 # mặc định PORT=8082
```

**Instance 2 (port 8084) — terminal khác:**
```bash
cd product-service
PORT=8084 mvn spring-boot:run
# hoặc: mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=8084
```

Cả 2 instance cùng đăng ký vào Eureka dưới tên `PRODUCT-SERVICE`.

> Nếu dùng IntelliJ: vào Edit Configurations → Modify options → tick "Allow multiple instances", rồi với instance 2 điền Program arguments `--server.port=8084`.

---

## 5. Kết quả mong muốn: tải được chia đều

Gọi API nhiều lần:

```bash
for i in $(seq 1 6); do
  curl -s http://localhost:8083/api/v1/orders/getProduct/1 > /dev/null
done
```

Quan sát log của 2 instance Product Service — request được chia luân phiên (round-robin):

```
[PRODUCT-SERVICE port 8082] xử lý request lấy sản phẩm id=1   ← lần 1
[PRODUCT-SERVICE port 8084] xử lý request lấy sản phẩm id=1   ← lần 2
[PRODUCT-SERVICE port 8082] xử lý request lấy sản phẩm id=1   ← lần 3
[PRODUCT-SERVICE port 8084] xử lý request lấy sản phẩm id=1   ← lần 4
...
```

Hai instance thay phiên nhau xử lý → chứng minh cân bằng tải hoạt động.

---

## 6. Lưu ý về cấu hình port linh hoạt

`product-service/application.yml`:
```yaml
server:
  port: ${PORT:8082}    # đọc biến môi trường PORT, mặc định 8082
```

Nhờ vậy có thể khởi chạy nhiều instance trên các port khác nhau từ cùng một mã nguồn.
