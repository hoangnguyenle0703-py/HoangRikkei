# Database per Service — Physical Isolation

Demo nguyên tắc **"Database per Service"** với PostgreSQL: hai microservice (`user-service` và `inventory-service`) lưu trữ dữ liệu trên **hai database vật lý riêng biệt** trên cùng một PostgreSQL instance.

```
                  ┌──────────────────────────────────────┐
                  │        PostgreSQL (localhost:5432)     │
                  │                                        │
   user-service ──┼──►  user_db        inventory_db  ◄─────┼── inventory-service
   (port 8083)    │     └─ table:users  └─ table:products  │   (port 8084)
                  │     pool:           pool:               │
                  │     user-service-   inventory-service-  │
                  │     pool            pool                │
                  └──────────────────────────────────────┘
        kết nối riêng                          kết nối riêng
        (user_service_app)                     (inventory_service_app)
```

Hai service **không hề dùng chung kết nối**: khác database, khác credentials, khác connection pool.

## Thành phần

| Service | Port | Database | Bảng | DB user |
|---------|------|----------|------|---------|
| user-service | 8083 | `user_db` | `users` | `user_service_app` |
| inventory-service | 8084 | `inventory_db` | `products` | `inventory_service_app` |

## Cách chạy

### Bước 1 — Khởi động PostgreSQL (tạo sẵn 2 database)

```bash
docker compose up -d
```

Script `init-db/init.sql` sẽ tự tạo `user_db`, `inventory_db` và 2 user riêng.

### Bước 2 — Chạy user-service

```bash
cd user-service
mvn spring-boot:run
```

### Bước 3 — Chạy inventory-service (terminal khác)

```bash
cd inventory-service
mvn spring-boot:run
```

## Chứng minh hai service tách biệt hoàn toàn

### 1. Lấy dữ liệu từ mỗi service

```bash
# Dữ liệu users — chỉ có trong user_db
curl http://localhost:8083/api/v1/users

# Dữ liệu products — chỉ có trong inventory_db
curl http://localhost:8084/api/v1/products
```

### 2. So sánh thông tin kết nối DB

```bash
curl http://localhost:8083/api/v1/users/db-info
curl http://localhost:8084/api/v1/products/db-info
```

Kết quả `user-service`:

```json
{
  "serviceName": "user-service",
  "databaseName": "user_db",
  "jdbcUrl": "jdbc:postgresql://localhost:5432/user_db",
  "connectionPool": "user-service-pool",
  "dbUser": "user_service_app"
}
```

Kết quả `inventory-service`:

```json
{
  "serviceName": "inventory-service",
  "databaseName": "inventory_db",
  "jdbcUrl": "jdbc:postgresql://localhost:5432/inventory_db",
  "connectionPool": "inventory-service-pool",
  "dbUser": "inventory_service_app"
}
```

→ `databaseName`, `jdbcUrl`, `connectionPool`, `dbUser` **đều khác nhau** ⇒ chứng minh hai service dùng kết nối hoàn toàn độc lập.

### 3. Chứng minh tính độc lập khi bảo trì (fault isolation)

Kiểm tra trực tiếp trong PostgreSQL: bảng `users` chỉ tồn tại trong `user_db`, bảng `products` chỉ tồn tại trong `inventory_db`.

```bash
# Trong user_db KHÔNG có bảng products
docker exec -it ecommerce-postgres psql -U postgres -d user_db -c "\dt"

# Trong inventory_db KHÔNG có bảng users
docker exec -it ecommerce-postgres psql -U postgres -d inventory_db -c "\dt"
```

Mô phỏng "một bên bảo trì DB": ngắt kết nối tới `inventory_db` (ví dụ dừng inventory-service hoặc revoke quyền). API `user-service` vẫn trả dữ liệu bình thường vì nó không phụ thuộc vào `inventory_db`.

```bash
# Dù inventory-service đã dừng, user-service vẫn hoạt động:
curl http://localhost:8083/api/v1/users   # ✅ vẫn OK
```

## Kết quả đạt được

- Hai service hoạt động độc lập, dữ liệu lưu trữ tách biệt hoàn toàn trên PostgreSQL.
- Không chia sẻ database, schema hay connection pool.
- Một bên bảo trì/sự cố DB không làm bên kia ngừng hoạt động.
