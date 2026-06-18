# Order Service — Gọi Service qua Eureka (DiscoveryClient)

Order Service gọi sang Product Service bằng cách **hỏi Eureka** ("PRODUCT-SERVICE ở đâu?") thay vì dùng URL cứng `http://localhost:8082`. Nhờ đó dù Product Service đổi port, Order Service vẫn gọi được mà không cần sửa code.

---

## 1. Mục tiêu

- **Kiến thức**: Hiểu cách lấy thông tin Service từ Registry thay vì dùng URL cứng.
- **Kỹ năng**: Sử dụng `DiscoveryClient` để lấy danh sách instance.

---

## 2. Cách hoạt động của DiscoveryClient

`ProductClient` thực hiện 6 bước:

```java
public ProductInfo getProduct(Long productId) {
    // A. Hỏi Eureka lấy danh sách instance đang chạy của PRODUCT-SERVICE
    List<ServiceInstance> instances =
        discoveryClient.getInstances("PRODUCT-SERVICE");

    // B. Xử lý lỗi: không có instance nào -> 503
    if (instances == null || instances.isEmpty()) {
        throw new ServiceUnavailableException(
            "PRODUCT-SERVICE hiện không khả dụng (không tìm thấy instance nào)");
    }

    // C. Lấy instance đầu tiên (sau này có thể random để cân bằng tải)
    ServiceInstance productInstance = instances.get(0);

    // D. Lấy URL gốc (vd http://192.168.1.10:9090) từ instance
    String baseUrl = productInstance.getUri().toString();

    // E. Nối chuỗi tạo URL hoàn chỉnh
    String targetUrl = baseUrl + "/api/v1/products/" + productId;

    // F. Gọi API lấy về sản phẩm
    return restClient.get().uri(targetUrl).retrieve().body(ProductInfo.class);
}
```

Điểm mấu chốt: địa chỉ host:port KHÔNG hề được viết cứng trong code — nó lấy động từ Eureka qua discoveryClient.getInstances("PRODUCT-SERVICE").

---

## 3. API

| Method | Endpoint | Mô tả |
|--------|----------|-------|
| GET | `/api/v1/orders/getProduct/{id}` | Hỏi Eureka tìm PRODUCT-SERVICE rồi lấy sản phẩm |
| POST | `/api/v1/orders` | Tạo đơn (giá lấy từ Product Service qua Eureka) |
| GET | `/api/v1/orders/{id}` | Lấy thông tin đơn hàng |

---

## 4. Xử lý lỗi 503

Nếu không tìm thấy instance nào (Product Service chưa chạy / đã tắt / chưa kịp đăng ký), trả về `ApiResponseError` với HTTP 503 Service Unavailable:

```json
{
  "timestamp": "2026-02-23T01:44:53Z",
  "status": 503,
  "error": "Service Unavailable",
  "message": "PRODUCT-SERVICE hiện không khả dụng (không tìm thấy instance nào)",
  "path": "/api/v1/orders/getProduct/1"
}
```

---

## 5. Test (theo đúng kết quả mong muốn của đề)

### 5.1. Product Service ĐANG TẮT -> 503

```bash
# Chưa bật Product Service, hoặc đã tắt nó
curl -i http://localhost:8083/api/v1/orders/getProduct/1
```

Kết quả: HTTP 503 với body như mục 4.

### 5.2. Đổi Product Service sang port 9090 -> vẫn gọi thành công, KHÔNG sửa code

```bash
# Chạy Product Service ở port 9090 thay vì 8082:
cd product-service
mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=9090
```

Product Service tự đăng ký vào Eureka với port mới (9090). Khi gọi lại:

```bash
curl http://localhost:8083/api/v1/orders/getProduct/1
```

Kết quả: HTTP 200, trả về sản phẩm — Order Service tự lấy port 9090 từ Eureka mà không cần thay đổi gì trong code.

```json
{
  "id": 1,
  "name": "Iphone 17",
  "price": 123466.0,
  "stockQuantity": 50
}
```

---

## 6. Tại sao đây là sức mạnh của Service Discovery?

| Cách cũ (URL cứng) | Cách mới (DiscoveryClient) |
|--------------------|----------------------------|
| http://localhost:8082 viết cứng trong code | Lấy địa chỉ động từ Eureka theo tên service |
| Đổi port -> phải sửa code, build lại | Đổi port -> không cần sửa gì |
| Một địa chỉ cố định | Tự động cân bằng tải khi có nhiều instance |
| Không biết service còn sống không | Eureka chỉ trả instance còn gửi heartbeat |
