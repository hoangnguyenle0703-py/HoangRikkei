# E-commerce — Thiết kế kiến trúc phân rã (Decomposition)

Phân tách hệ thống TMĐT thành **3 Spring Boot service độc lập**: Customer, Product, Order. Mỗi service sở hữu database riêng, giao tiếp qua REST API, không dùng chung DB và không gọi trực tiếp code của nhau.

## Kiến trúc

```
                    Client → API Gateway
                            │
         ┌──────────────────┼──────────────────┐
         ▼                  ▼                  ▼
  Customer Service   Product Service     Order Service
  (port 8081)        (port 8082)         (port 8083)
       │                  │                  │
       ▼                  ▼                  ▼
  customer_db        product_db          order_db

  Order Service ──REST API──► Customer Service  (lấy thông tin khách theo customerId)
```

## Các thực thể

| Service | Entity | Thuộc tính |
|---------|--------|------------|
| Customer | `Customer` | id, fullName, email, password, address |
| Product | `Product` | id, name, price, stockQuantity, description |
| Order | `Order` | id, **customerId**, orderDate, totalAmount, status |

## Điểm cốt lõi: Order chỉ lưu customerId

`Order` chỉ lưu `customerId` (kiểu Long) — KHÔNG nhúng cả đối tượng `Customer`. Khi cần thông tin khách, Order Service gọi sang Customer Service qua API (`CustomerClient`). Xem chi tiết lý do trong file báo cáo docx.

## Cách chạy

```bash
# 1. PostgreSQL + tạo 3 database
docker compose up -d

# 2. Mỗi service một terminal
cd customer-service && mvn spring-boot:run   # :8081
cd product-service  && mvn spring-boot:run   # :8082
cd order-service    && mvn spring-boot:run   # :8083
```

## Test

```bash
# Lấy khách hàng (Customer Service)
curl http://localhost:8081/api/v1/customers/1

# Lấy sản phẩm (Product Service)
curl http://localhost:8082/api/v1/products/1

# Lấy đơn hàng — Order Service tự gọi sang Customer Service để ghép thông tin khách
curl http://localhost:8083/api/v1/orders/1
```

Kết quả `GET /orders/1` cho thấy Order Service chỉ lưu `customerId=1`, nhưng response vẫn có đầy đủ thông tin khách (lấy động từ Customer Service):

```json
{
  "orderId": 1,
  "customerId": 1,
  "customer": {
    "id": 1,
    "fullName": "Nguyễn Văn Huy",
    "email": "huy@example.com",
    "address": "Hà Nội"
  },
  "orderDate": "2026-06-16T10:00:00",
  "totalAmount": 199000,
  "status": "PENDING"
}
```

## Ranh giới (Boundaries) được tôn trọng

- **Không dùng chung DB**: mỗi service một database riêng.
- **Không gọi trực tiếp code**: Order Service giao tiếp với Customer Service qua HTTP (`CustomerClient` + `WebClient`), không import class của service kia.
- **Sở hữu dữ liệu rõ ràng**: thông tin khách thuộc Customer Service, thông tin SP thuộc Product Service, đơn hàng thuộc Order Service.
