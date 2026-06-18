# Customer Service — Triển khai & Chuẩn hóa API Error

Microservice quản lý khách hàng, kết nối **PostgreSQL thực tế**, có cấu trúc dự án chuẩn và cơ chế phản hồi lỗi chuyên nghiệp cho phía Client.

---

## 1. Mục tiêu

- Làm quen với việc kết nối PostgreSQL thực tế.
- Xây dựng cấu trúc dự án chuẩn để quản lý dữ liệu khách hàng.
- Học cách phản hồi lỗi chuyên nghiệp cho phía Client (chuẩn hóa qua `ApiResponseError` + `@RestControllerAdvice`).

---

## 2. Thông tin dự án

| Mục | Giá trị |
|-----|---------|
| Tên project | `customer-service` |
| Port | **8081** |
| Database | `customer_db` (PostgreSQL) |
| Java | 17 |
| Spring Boot | 3.3.2 |
| Mã hóa mật khẩu | BCrypt (spring-security-crypto) |

---

## 3. Cấu trúc mã nguồn (Packages)

```
com.ecommerce.customerservice
├── CustomerServiceApplication.java     # Entry point
│
├── config/
│   └── PasswordConfig.java             # Bean BCryptPasswordEncoder
│
├── entity/
│   └── Customer.java                   # id, fullName, email (unique), password
│
├── dto/
│   ├── CustomerRequestDTO.java         # nhận dữ liệu đăng ký (có password)
│   ├── CustomerResponseDTO.java        # trả ra (KHÔNG có password)
│   ├── LoginRequestDTO.java            # email + password để đăng nhập
│   └── ApiResponseError.java           # 4 thuộc tính: timestamp, status, error, message
│
├── repository/
│   └── CustomerRepository.java         # extends JpaRepository<Customer, Long>
│
├── service/
│   ├── CustomerService.java            # interface
│   └── impl/CustomerServiceImpl.java   # logic CRUD + mã hóa + đăng nhập
│
├── controller/
│   └── CustomerController.java         # REST endpoints
│
└── exception/
    ├── ResourceNotFoundException.java      # custom exception 404
    ├── InvalidCredentialsException.java    # custom exception 401 (sai login)
    ├── DuplicateResourceException.java      # email trùng (409)
    └── GlobalExceptionHandler.java          # @RestControllerAdvice
```

### Giải thích các thành phần chính

**`entity/Customer`** — Lớp thực thể với các cột: `id` (Long, khóa chính), `fullName` (String), `email` (String, unique), `password` (String, lưu dạng đã băm).

**`dto/CustomerRequestDTO`** — Nhận dữ liệu tạo mới (fullName, email, password). Có validation: email hợp lệ, password tối thiểu 6 ký tự.

**`dto/CustomerResponseDTO`** — Trả dữ liệu ra ngoài, **chỉ gồm id, fullName, email — không bao giờ chứa password**.

**`dto/ApiResponseError`** — Định dạng lỗi chuẩn với đúng 4 thuộc tính: `timestamp`, `status`, `error`, `message`.

**`exception/GlobalExceptionHandler`** — Dùng `@RestControllerAdvice` bắt mọi exception và chuyển thành `ApiResponseError`. Controller không cần try-catch.

**`repository/CustomerRepository`** — Kế thừa `JpaRepository<Customer, Long>`, thêm `findByEmail` phục vụ đăng nhập.

---

## 4. Yêu cầu API

| Method | Endpoint | Mô tả |
|--------|----------|-------|
| POST | `/api/v1/customers/register` | Nhận `CustomerRequestDTO`, **mã hóa mật khẩu** rồi lưu, trả `CustomerResponseDTO` |
| GET | `/api/v1/customers/{id}` | Trả `CustomerResponseDTO`. Không thấy id → ném lỗi → 404 `ApiResponseError` |
| PUT | `/api/v1/customers/login` | Nhận email + password. Đúng → `CustomerResponseDTO`. Sai → message `"email or password incorrect"` |

---

## 5. Cách chạy

```bash
# Bước 1: PostgreSQL (tự tạo customer_db)
docker compose up -d

# Bước 2: chạy service
mvn spring-boot:run
```

Service chạy ở `http://localhost:8081`.

> Nếu tự cài PostgreSQL thay vì Docker: tạo database bằng `CREATE DATABASE customer_db;` rồi đảm bảo user/password trong `application.yml` khớp.

---

## 6. Test API (dùng curl hoặc Postman)

### 6.1. Đăng ký — POST /register

```bash
curl -X POST http://localhost:8081/api/v1/customers/register \
  -H "Content-Type: application/json" \
  -d '{"fullName":"Nguyễn Văn Huy","email":"huy@example.com","password":"matkhau123"}'
```

Kết quả (201 Created) — lưu ý **không có password**:

```json
{
  "id": 1,
  "fullName": "Nguyễn Văn Huy",
  "email": "huy@example.com"
}
```

Trong DB, cột password lưu dạng băm, ví dụ: `$2a$10$N9qo8uLOickgx2ZMRZoMy...` (không phải `matkhau123`).

### 6.2. Lấy khách hàng tồn tại — GET /{id}

```bash
curl http://localhost:8081/api/v1/customers/1
```

```json
{
  "id": 1,
  "fullName": "Nguyễn Văn Huy",
  "email": "huy@example.com"
}
```

### 6.3. Lấy khách hàng KHÔNG tồn tại — kết quả mong muốn của bài

```bash
curl -i http://localhost:8081/api/v1/customers/99
```

HTTP 404, body đúng định dạng yêu cầu:

```json
{
  "timestamp": "2026-06-16T10:30:00",
  "status": 404,
  "error": "Not Found",
  "message": "Khách hàng với ID 99 không tồn tại!"
}
```

### 6.4. Đăng nhập đúng — PUT /login

```bash
curl -X PUT http://localhost:8081/api/v1/customers/login \
  -H "Content-Type: application/json" \
  -d '{"email":"huy@example.com","password":"matkhau123"}'
```

```json
{
  "id": 1,
  "fullName": "Nguyễn Văn Huy",
  "email": "huy@example.com"
}
```

### 6.5. Đăng nhập sai — PUT /login

```bash
curl -i -X PUT http://localhost:8081/api/v1/customers/login \
  -H "Content-Type: application/json" \
  -d '{"email":"huy@example.com","password":"sai_mat_khau"}'
```

HTTP 401, body:

```json
{
  "timestamp": "2026-06-16T10:31:00",
  "status": 401,
  "error": "Unauthorized",
  "message": "email or password incorrect"
}
```

---

## 7. Điểm nhấn kỹ thuật

1. **Mã hóa mật khẩu (BCrypt)**: mật khẩu gốc được băm trước khi lưu (`passwordEncoder.encode`). Khi đăng nhập so khớp bằng `passwordEncoder.matches`, không bao giờ giải mã ngược.

2. **Không lộ mật khẩu**: `CustomerResponseDTO` không có trường password nên password không bao giờ xuất hiện trong response.

3. **Chuẩn hóa lỗi tập trung**: mọi lỗi (404, 401, 400, 409, 500) đều trả về cùng cấu trúc `ApiResponseError`, giúp Frontend xử lý nhất quán.

4. **Tách lớp rõ ràng**: Controller → Service → Repository, logic nghiệp vụ nằm ở tầng Service.

---

## 8. Luồng xử lý lỗi GET /{id} với id không tồn tại

```
GET /customers/99
   │
   ▼  Controller gọi Service
Service: findById(99) rỗng
   │
   ▼  ném ResourceNotFoundException.customer(99)
       → "Khách hàng với ID 99 không tồn tại!"
GlobalExceptionHandler (@RestControllerAdvice) bắt
   │
   ▼  build ApiResponseError (status 404, error "Not Found")
Response JSON chuẩn hóa trả về Client
```
