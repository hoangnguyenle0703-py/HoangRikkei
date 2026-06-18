# Discovery Server — Eureka Server (Trạm điều hướng)

Service Registry trung tâm — "cuốn danh bạ" của hệ thống Microservices. Các service (Customer, Product, Order) đăng ký địa chỉ tại đây và tra cứu nhau qua Eureka, thay vì phải nhớ IP/port cứng của nhau.

---

## 1. Mục tiêu

- **Kiến thức**: Hiểu vai trò của Service Registry trong hệ thống Microservices.
- **Kỹ năng**: Cài đặt và cấu hình Eureka Server độc lập.

---

## 2. Service Registry là gì? Vì sao cần?

Trong hệ thống Microservices, mỗi service chạy ở một địa chỉ (host:port) và có thể thay đổi, nhân bản nhiều instance. Nếu Order Service muốn gọi Product Service, nó không nên "nhớ cứng" địa chỉ `http://localhost:8082` vì:

- Địa chỉ có thể đổi khi triển khai.
- Có thể có nhiều instance của cùng một service (load balancing).

**Service Registry (Eureka Server)** giải quyết việc này như một cuốn danh bạ:

```
        ┌────────────────────────────┐
        │   Eureka Server (:8761)     │
        │   "cuốn danh bạ"            │
        └────────────────────────────┘
           ▲          ▲          ▲
   đăng ký │  đăng ký │  đăng ký │
           │          │          │
   Customer Svc   Product Svc   Order Svc
   (:8081)        (:8082)       (:8083)

→ Order Service hỏi Eureka: "Product Service ở đâu?" → Eureka trả về địa chỉ.
```

---

## 3. Thông tin dự án

| Mục | Giá trị |
|-----|---------|
| Tên project | `discovery-server` |
| Port | **8761** (cổng mặc định của Eureka) |
| Spring Boot | 3.3.2 |
| Spring Cloud | 2023.0.3 |
| Dependency chính | `spring-cloud-starter-netflix-eureka-server` |

---

## 4. Các bước cấu hình (đã làm sẵn)

### 4.1. Dependency (pom.xml hoặc build.gradle)

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
</dependency>
```

Kèm `spring-cloud-dependencies` BOM phiên bản `2023.0.3` để quản lý version.

### 4.2. Annotation @EnableEurekaServer

```java
@SpringBootApplication
@EnableEurekaServer
public class DiscoveryServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(DiscoveryServerApplication.class, args);
    }
}
```

### 4.3. application.properties

```properties
server.port=8761
spring.application.name=discovery-server

# Eureka Server độc lập: không tự đăng ký, không kéo registry
eureka.client.register-with-eureka=false
eureka.client.fetch-registry=false
eureka.client.service-url.defaultZone=http://localhost:8761/eureka/
eureka.server.enable-self-preservation=false
```

> Hai dòng `register-with-eureka=false` và `fetch-registry=false` là điểm mấu chốt cho chế độ **độc lập (standalone)** — vì bản thân Eureka Server là registry, nó không cần đăng ký với chính mình hay kéo danh sách về.

---

## 5. Cách chạy

**Dùng Maven:**
```bash
mvn spring-boot:run
```

**Dùng Gradle:**
```bash
./gradlew bootRun
```

Eureka Server sẽ khởi động ở port 8761.

> Lưu ý: dự án dùng song song `pom.xml` (Maven) và `build.gradle` (Gradle) để tham khảo. Chọn một công cụ build, không cần cả hai.

---

## 6. Kết quả mong muốn

Mở trình duyệt truy cập **http://localhost:8761** sẽ thấy Dashboard quản lý của Eureka (Spring Eureka):

- **Trạng thái hệ thống**: môi trường, trung tâm dữ liệu, thời gian hoạt động.
- **DS Replicas**: các bản sao Eureka (ở chế độ độc lập thì trống).
- **Instances currently registered with Eureka**: danh sách service đang đăng ký. Lúc đầu hiển thị "Không có trường hợp nào khả dụng" vì chưa có service nào đăng ký.
- **General Info**: bộ nhớ, CPU...

Khi các service khác (Customer/Product/Order) bật lên với `spring-cloud-starter-netflix-eureka-client` và trỏ `defaultZone` về `http://localhost:8761/eureka/`, chúng sẽ tự xuất hiện trong mục "Instances currently registered".

---

## 7. Bước tiếp theo (cho các service client)

Để Customer/Product/Order đăng ký vào Eureka, mỗi service thêm:

Dependency:
```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
```

Cấu hình:
```properties
eureka.client.service-url.defaultZone=http://localhost:8761/eureka/
```

Sau đó các service có thể gọi nhau bằng TÊN service (vd `http://product-service/...`) thay vì IP:port cứng.
