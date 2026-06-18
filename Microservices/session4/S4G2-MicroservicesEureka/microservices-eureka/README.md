# Microservices E-commerce + Eureka (Service Registry)

Hệ thống gồm 4 ứng dụng Spring Boot: một **Eureka Server** (danh bạ trung tâm) và 3 service nghiệp vụ (**Customer, Product, Order**) đã được đăng ký làm **Eureka Client**. Khi chạy, cả 3 service sẽ tự xuất hiện trên Dashboard Eureka ở trạng thái UP.

---

## 1. Mục tiêu của bài

- **Kiến thức**: Hiểu cách các Client kết nối và gửi tín hiệu Heartbeat cho Server.
- **Kỹ năng**: Chuyển đổi các Service có sẵn (Customer, Product, Order) thành Eureka Clients.

---

## 2. Eureka Client là gì? Heartbeat hoạt động thế nào?

Mỗi service khi có dependency `spring-cloud-starter-netflix-eureka-client` sẽ:

1. **Đăng ký** (register) địa chỉ của mình với Eureka Server lúc khởi động — như "đăng ký hộ khẩu".
2. **Gửi Heartbeat** định kỳ (mặc định mỗi 30 giây) để báo "tôi vẫn còn sống".
3. Nếu Eureka không nhận được heartbeat trong một khoảng thời gian, nó coi service đã chết và loại khỏi danh bạ.

```
  Customer/Product/Order Service
            │
   1. register (đăng ký)
   2. heartbeat mỗi 30s ──────────►  Eureka Server (:8761)
            │                          "danh bạ"
   3. hỏi địa chỉ service khác ◄──────
```

---

## 3. Cấu trúc hệ thống

```
microservices-eureka/
├── docker-compose.yml          # PostgreSQL + tạo 3 database
├── init-db/init.sql            # customer_db, product_db, order_db
├── discovery-server/           # Eureka Server (:8761)
├── customer-service/           # Eureka Client (:8081) → customer_db
├── product-service/            # Eureka Client (:8082) → product_db
└── order-service/              # Eureka Client (:8083) → order_db
```

| Ứng dụng | Port | Vai trò |
|----------|------|---------|
| discovery-server | 8761 | Eureka Server (danh bạ) |
| customer-service | 8081 | Eureka Client |
| product-service | 8082 | Eureka Client |
| order-service | 8083 | Eureka Client |

---

## 4. 3 thay đổi để biến một Service thành Eureka Client

Áp dụng cho cả Customer, Product, Order:

### 4.1. Thêm dependency (pom.xml)

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
```

Kèm BOM quản lý phiên bản:

```xml
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-dependencies</artifactId>
            <version>${spring-cloud.version}</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

### 4.2. Thêm @EnableDiscoveryClient vào class Application

```java
@SpringBootApplication
@EnableDiscoveryClient
public class CustomerServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(CustomerServiceApplication.class, args);
    }
}
```

### 4.3. Cấu hình trỏ về Eureka Server (application.yml)

```yaml
eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka/   # địa chỉ Eureka Server
  instance:
    prefer-ip-address: true                        # hiển thị IP thay vì hostname
```

---

## 5. ⚠️ Lưu ý quan trọng về phiên bản Spring Cloud

Đề bài nêu ví dụ `springCloudVersion = 2025.1.1`. Tuy nhiên **phiên bản đó dành cho Spring Boot 4.0.x**. Các service ở đây dùng **Spring Boot 3.3.2**, nên phải dùng **Spring Cloud 2023.0.3** mới tương thích.

Nguyên tắc: luôn ghép đúng "release train" Spring Cloud với phiên bản Spring Boot:

| Spring Boot | Spring Cloud |
|-------------|--------------|
| 3.3.x | **2023.0.x** ← dùng trong dự án này |
| 3.5.x | 2025.0.x |
| 4.0.x | 2025.1.x (ví dụ trong đề) |

Nếu bạn nâng các service lên Spring Boot 4.0.x thì mới đổi sang `2025.1.1` như đề.

---

## 6. Cách chạy (theo đúng thứ tự)

```bash
# Bước 1: PostgreSQL + 3 database
docker compose up -d

# Bước 2: Eureka Server TRƯỚC (rất quan trọng)
cd discovery-server && mvn spring-boot:run     # :8761

# Bước 3: Lần lượt 3 service (mỗi cái một terminal)
cd customer-service && mvn spring-boot:run     # :8081
cd product-service  && mvn spring-boot:run     # :8082
cd order-service    && mvn spring-boot:run     # :8083
```

> Phải bật Eureka Server (8761) trước, rồi mới bật 3 service. Mỗi service mất ~30 giây để xuất hiện trên Dashboard sau khi gửi heartbeat đầu tiên.

---

## 7. Kết quả mong muốn

Mở **http://localhost:8761** → mục **"Instances currently registered with Eureka"** hiển thị cả 3 service ở trạng thái **UP**:

```
Application        AMIs    Availability Zones    Status
CUSTOMER-SERVICE   n/a (1) (1)                   UP (1) - .../CUSTOMER-SERVICE:8081
PRODUCT-SERVICE    n/a (1) (1)                   UP (1) - .../PRODUCT-SERVICE:8082
ORDER-SERVICE      n/a (1) (1)                   UP (1) - .../ORDER-SERVICE:8083
```

Đúng như ảnh đề bài: ba dòng CUSTOMERSERVICE, PRODUCTSERVICE, ORDERSERVICE đều UP.

---

## 8. Lợi ích sau khi đăng ký Eureka

Sau khi cả 3 đã lên danh bạ, các service có thể gọi nhau **bằng TÊN** thay vì IP:port cứng. Ví dụ Order Service gọi Product Service:

```
http://product-service/api/v1/products/1   (thay vì http://localhost:8082/...)
```

Eureka tự phân giải tên `product-service` thành địa chỉ thật, hỗ trợ cả load balancing khi có nhiều instance.

---

## 9. Tự động phát hiện & gọi Service (DiscoveryClient)

Order Service đã được nâng cấp để gọi Product Service **qua Eureka** thay vì URL cứng:

- Endpoint mới: `GET /api/v1/orders/getProduct/{id}` — Order Service hỏi Eureka "PRODUCT-SERVICE ở đâu?" rồi gọi lấy sản phẩm.
- Không tìm thấy instance / service bị tắt → trả về **503 Service Unavailable** (định dạng `ApiResponseError` có cả trường `path`).
- Đổi port Product Service (vd 8082 → 9090) → Order Service **vẫn gọi thành công, không cần sửa code**, vì địa chỉ lấy động từ Eureka.

Chi tiết xem `order-service/README.md`.

### Cách kiểm chứng nhanh

```bash
# 1. Bật discovery-server, product-service, order-service như mục 6.
# 2. Gọi:
curl http://localhost:8083/api/v1/orders/getProduct/1     # → 200, trả sản phẩm

# 3. Tắt product-service, gọi lại:
curl -i http://localhost:8083/api/v1/orders/getProduct/1  # → 503 Service Unavailable

# 4. Bật lại product-service ở port khác (9090), gọi lại:
curl http://localhost:8083/api/v1/orders/getProduct/1     # → 200, vẫn OK, không sửa code
```

---

## 10. Cân bằng tải phía Client với @LoadBalanced

Order Service đã được nâng cấp dùng `RestTemplate` có `@LoadBalanced` để gọi Product Service bằng tên (`http://PRODUCT-SERVICE/...`):

- Bean `RestTemplate` đánh dấu `@LoadBalanced` (trong `order-service/config/RestTemplateConfig.java`).
- Gọi bằng tên service, không cần biết port → khi có nhiều instance, request chia đều round-robin.
- `product-service` để port linh hoạt `${PORT:8082}` để chạy được nhiều instance từ cùng mã nguồn.

### Kiểm chứng tải chia đều

```bash
# 1. Bật discovery-server.
# 2. Bật Product Service instance 1 (port 8082):
cd product-service && mvn spring-boot:run

# 3. Bật Product Service instance 2 (port 8084) ở terminal khác:
cd product-service && PORT=8084 mvn spring-boot:run

# 4. Bật order-service (8083).
# 5. Gọi nhiều lần:
for i in $(seq 1 6); do curl -s http://localhost:8083/api/v1/orders/getProduct/1 > /dev/null; done
```

Log 2 instance Product Service sẽ thấy request chia luân phiên giữa port 8082 và 8084.
Chi tiết: `order-service/README.md`.
