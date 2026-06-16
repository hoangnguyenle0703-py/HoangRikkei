# Order Service — Triển khai & Tư duy liên kết Microservices

Microservice quản lý đơn hàng, minh họa cách lưu trữ dữ liệu khi các thực thể nằm ở các database khác nhau và cách thiết kế DTO tổng hợp.

---

## 1. Mục tiêu

- Hiểu cách lưu trữ dữ liệu trong Microservices khi các thực thể nằm ở các database khác nhau.
- Thực hành thiết kế DTO tổng hợp.

---

## 2. Thông tin dự án

| Mục | Giá trị |
|-----|---------|
| Tên project | `order-service` |
| Port | **8083** |
| Database | `order_db` (PostgreSQL) |
| Java | 17 |
| Spring Boot | 3.3.2 |

---

## 3. Thực thể Order

`Order` gồm: `id`, `customerId`, `productId`, `orderDate`, `totalAmount`.

```java
@Entity
@Table(name = "orders")
public class Order {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_id", nullable = false)
    private Long customerId;     // chỉ lưu ID, KHÔNG @ManyToOne

    @Column(name = "product_id", nullable = false)
    private Long productId;      // chỉ lưu ID, KHÔNG @ManyToOne

    private LocalDateTime orderDate;
    private BigDecimal totalAmount;
}
```

---

## 4. Tại sao KHÔNG dùng @JoinColumn / @ManyToOne / khóa ngoại?

Đây là câu hỏi trọng tâm (kết quả mong muốn của bài). Lý do:

1. **Customer và Product nằm ở database khác** (`customer_db`, `product_db`), trong khi Order nằm ở `order_db`. Khóa ngoại (foreign key) và `@ManyToOne` chỉ hoạt động khi các bảng **nằm trong cùng một database** — vì DBMS cần truy vấn JOIN và kiểm tra ràng buộc toàn vẹn trên cùng một kết nối.

2. **Không thể JOIN xuyên database/service**: `@JoinColumn` yêu cầu Hibernate thực hiện JOIN ở tầng SQL. Hai bảng ở hai database vật lý khác nhau (thậm chí hai server khác nhau) thì không thể JOIN.

3. **Vi phạm ranh giới Microservices**: nếu Order có khóa ngoại trỏ tới bảng `customers`, hai service bị ghép chặt (tight coupling) ở tầng dữ liệu — đi ngược nguyên tắc mỗi service sở hữu database riêng.

4. **Giải pháp — tham chiếu mềm (soft reference)**: Order chỉ lưu `customerId` và `productId` dưới dạng số `Long`. Khi cần thông tin chi tiết, Order Service gọi sang Customer/Product Service **qua REST API**, không qua JOIN database.

```
SAI:  Order ──@ManyToOne──► Customer   (không thể, khác DB)
ĐÚNG: Order lưu customerId: Long ──REST API──► Customer Service
```

---

## 5. Luồng tạo đơn hàng & tính totalAmount

```
POST /orders {customerId, productId, quantity}
   │
   ▼  Validation: quantity > 0 ?  (sai -> 400)
   ▼  ProductClient gọi Product Service (qua API) lấy price theo productId
   ▼  totalAmount = price * quantity
   ▼  Lưu Order vào order_db  (lỗi lưu -> 500)
   ▼  Trả OrderResponseDTO
```

`totalAmount` được tính bằng giá lấy từ Product Service nhân với số lượng. Order Service không lưu giá sản phẩm trong DB của mình — giá là dữ liệu thuộc Product Service.

> Ghi chú: `ProductClient` có cơ chế fallback giả lập giá nếu Product Service chưa chạy, để bài tập vẫn demo được luồng tính `totalAmount`.

---

## 6. API

| Method | Endpoint | Mô tả |
|--------|----------|-------|
| POST | `/api/v1/orders` | Nhận customerId, productId, quantity; tính totalAmount; lưu vào PostgreSQL |
| GET | `/api/v1/orders/{id}` | Trả thông tin đơn hàng |

---

## 7. Xử lý lỗi

| Tình huống | HTTP | message |
|------------|------|---------|
| `quantity <= 0` | 400 Bad Request | "quantity phải lớn hơn 0" |
| Lưu DB thất bại | 500 Internal Server Error | "Lưu đơn hàng vào cơ sở dữ liệu thất bại" |
| Không tìm thấy đơn hàng | 404 Not Found | "Đơn hàng với ID ... không tồn tại!" |

Tất cả đều trả về định dạng `ApiResponseError` (timestamp, status, error, message).

---

## 8. Cách chạy

```bash
# Bước 1: PostgreSQL (tự tạo order_db)
docker compose up -d

# Bước 2 (tùy chọn): chạy Product Service ở port 8082 để lấy giá thật
# Nếu không, Order Service dùng giá giả lập.

# Bước 3: chạy Order Service
mvn spring-boot:run
```

Service chạy ở `http://localhost:8083`.

---

## 9. Test API

### 9.1. Tạo đơn hàng hợp lệ — POST /orders

```bash
curl -X POST http://localhost:8083/api/v1/orders \
  -H "Content-Type: application/json" \
  -d '{"customerId":1,"productId":1,"quantity":3}'
```

Kết quả (201 Created) — đơn hàng được lưu thành công vào order_db:

```json
{
  "id": 1,
  "customerId": 1,
  "productId": 1,
  "orderDate": "2026-06-16T10:30:00",
  "totalAmount": 300000
}
```

(totalAmount = giá 100000 × quantity 3 = 300000)

### 9.2. quantity <= 0 — kết quả mong muốn về lỗi 400

```bash
curl -i -X POST http://localhost:8083/api/v1/orders \
  -H "Content-Type: application/json" \
  -d '{"customerId":1,"productId":1,"quantity":0}'
```

HTTP 400 Bad Request:

```json
{
  "timestamp": "2026-06-16T10:31:00",
  "status": 400,
  "error": "Bad Request",
  "message": "quantity phải lớn hơn 0"
}
```

### 9.3. Lấy đơn hàng — GET /{id}

```bash
curl http://localhost:8083/api/v1/orders/1
```

### 9.4. Lấy đơn hàng không tồn tại

```bash
curl -i http://localhost:8083/api/v1/orders/999
```

```json
{
  "timestamp": "2026-06-16T10:32:00",
  "status": 404,
  "error": "Not Found",
  "message": "Đơn hàng với ID 999 không tồn tại!"
}
```

---

## 10. Tổng kết — kết quả mong muốn

- **Giải thích vì sao không dùng @JoinColumn**: vì Customer/Product nằm khác Database/Service, không thể tạo khóa ngoại hay JOIN xuyên database. Chỉ lưu ID dạng Long và liên kết qua REST API.
- **Dữ liệu đơn hàng được lưu thành công vào database thứ 3** (`order_db`), độc lập với customer_db và product_db.
