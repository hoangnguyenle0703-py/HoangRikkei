# Order Service — Chuẩn hóa API Error Response

Demo cách chuẩn hóa thông báo lỗi cho toàn hệ thống bằng `ApiResponseError` + custom exception + `@RestControllerAdvice`.

## Thành phần chính

| File | Vai trò |
|------|---------|
| `dto/ApiResponseError.java` | Định dạng lỗi chuẩn: `timestamp`, `status`, `error`, `message` |
| `exception/ResourceNotFoundException.java` | Custom exception ném khi không tìm thấy tài nguyên |
| `exception/GlobalExceptionHandler.java` | Bắt exception tập trung bằng `@RestControllerAdvice` |
| `controller/OrderController.java` | API `GET /api/v1/orders/{id}` |

## Cách chạy

### Bước 1 — PostgreSQL (tạo sẵn order_db)

```bash
docker compose up -d
```

### Bước 2 — Chạy service

```bash
mvn spring-boot:run
```

Service chạy ở port **8085**. Khi khởi động tự nạp 2 đơn hàng id = 1, 2.

## Test API

### Trường hợp THÀNH CÔNG (id tồn tại)

```bash
curl http://localhost:8085/api/v1/orders/1
```

```json
{
  "id": 1,
  "orderCode": "ORD-0001",
  "customerName": "Nguyễn Văn Huy",
  "totalAmount": 199000
}
```

### Trường hợp LỖI (id không tồn tại) — kết quả mong muốn của bài

```bash
curl -i http://localhost:8085/api/v1/orders/999
```

HTTP status: `404 Not Found`, body:

```json
{
  "timestamp": "2026-06-15T10:00:00",
  "status": 404,
  "error": "Not Found",
  "message": "Order với ID 999 không tồn tại trên hệ thống!"
}
```

→ Đúng định dạng lỗi chuẩn hóa mà Frontend dễ xử lý.

## Luồng xử lý lỗi

```
GET /orders/999
   │
   ▼
OrderController.getOrderById(999)
   │
   ▼
OrderServiceImpl  → findById(999) rỗng
   │
   ▼
throw new ResourceNotFoundException("Order", 999)
   │
   ▼
GlobalExceptionHandler bắt  → build ApiResponseError (404)
   │
   ▼
Response JSON chuẩn hóa trả về client
```
