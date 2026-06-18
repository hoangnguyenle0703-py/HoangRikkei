# Product Service — Triển khai & Bean Validation

Microservice quản lý sản phẩm độc lập, sử dụng **Bean Validation** để đảm bảo tính đúng đắn của dữ liệu trước khi lưu vào DB.

---

## 1. Mục tiêu

- Triển khai dịch vụ quản lý sản phẩm độc lập.
- Sử dụng Validation để đảm bảo tính đúng đắn của dữ liệu trước khi lưu vào DB.

---

## 2. Thông tin dự án

| Mục | Giá trị |
|-----|---------|
| Tên project | `product-service` |
| Port | **8082** |
| Database | `product_db` (PostgreSQL) |
| Java | 17 |
| Spring Boot | 3.3.2 |
| Validation | spring-boot-starter-validation |

---

## 3. Cấu trúc mã nguồn

```
com.ecommerce.productservice
├── ProductServiceApplication.java      # Entry point
│
├── entity/
│   └── Product.java                    # id, name, price, stockQuantity
│
├── dto/
│   ├── ProductRequestDTO.java          # nhận dữ liệu + RÀNG BUỘC Validation
│   ├── ProductResponseDTO.java         # trả dữ liệu ra ngoài
│   └── ApiResponseError.java           # timestamp, status, error, message
│
├── repository/
│   └── ProductRepository.java          # extends JpaRepository<Product, Long>
│
├── service/
│   ├── ProductService.java             # interface
│   └── impl/ProductServiceImpl.java    # logic CRUD
│
├── controller/
│   └── ProductController.java          # REST endpoints (@Valid)
│
└── exception/
    ├── ResourceNotFoundException.java   # 404
    └── GlobalExceptionHandler.java      # @RestControllerAdvice + bắt validation
```

---

## 4. Bean Validation — các ràng buộc

Trong `ProductRequestDTO`:

| Trường | Ràng buộc | Annotation | Thông báo |
|--------|-----------|------------|-----------|
| `name` | Không được để trống | `@NotBlank` | "name không được để trống" |
| `price` | Phải lớn hơn 0 | `@Min(1)` | "price phải lớn hơn 0" |
| `stockQuantity` | Không được âm | `@Min(0)` | "stockQuantity không được âm" |

```java
public class ProductRequestDTO {

    @NotBlank(message = "name không được để trống")
    private String name;

    @NotNull(message = "price không được để trống")
    @Min(value = 1, message = "price phải lớn hơn 0")
    private BigDecimal price;

    @NotNull(message = "stockQuantity không được để trống")
    @Min(value = 0, message = "stockQuantity không được âm")
    private Integer stockQuantity;
}
```

### Cơ chế hoạt động

1. Controller dùng `@Valid @RequestBody ProductRequestDTO` để kích hoạt kiểm tra.
2. Nếu dữ liệu sai (vd `price = -500`), Spring **không gọi vào service**, mà ném ngay `MethodArgumentNotValidException`.
3. `GlobalExceptionHandler` bắt exception này, **map các thông báo lỗi vào trường `message`** của `ApiResponseError`, trả về HTTP 400.

```java
@ExceptionHandler(MethodArgumentNotValidException.class)
public ResponseEntity<ApiResponseError> handleValidation(MethodArgumentNotValidException ex) {
    String message = ex.getBindingResult().getFieldErrors().stream()
            .map(FieldError::getDefaultMessage)
            .collect(Collectors.joining("; "));
    return new ResponseEntity<>(build(HttpStatus.BAD_REQUEST, message), HttpStatus.BAD_REQUEST);
}
```

---

## 5. API

| Method | Endpoint | Mô tả |
|--------|----------|-------|
| POST | `/api/v1/products` | Tạo sản phẩm mới (có kiểm tra Validation) |
| GET | `/api/v1/products/{id}` | Lấy chi tiết sản phẩm |
| GET | `/api/v1/products` | Lấy danh sách toàn bộ sản phẩm |

---

## 6. Cách chạy

```bash
# Bước 1: PostgreSQL (tự tạo product_db)
docker compose up -d

# Bước 2: chạy service
mvn spring-boot:run
```

Service chạy ở `http://localhost:8082`.

---

## 7. Test API

### 7.1. Tạo sản phẩm hợp lệ — POST /products

```bash
curl -X POST http://localhost:8082/api/v1/products \
  -H "Content-Type: application/json" \
  -d '{"name":"Áo thun cotton","price":199000,"stockQuantity":320}'
```

Kết quả (201 Created):

```json
{
  "id": 1,
  "name": "Áo thun cotton",
  "price": 199000,
  "stockQuantity": 320
}
```

### 7.2. Tạo sản phẩm với price = -500 — kết quả mong muốn của bài

```bash
curl -i -X POST http://localhost:8082/api/v1/products \
  -H "Content-Type: application/json" \
  -d '{"name":"Sản phẩm lỗi","price":-500,"stockQuantity":10}'
```

Hệ thống **KHÔNG lưu vào DB**, trả về HTTP 400 Bad Request với định dạng `ApiResponseError`:

```json
{
  "timestamp": "2026-06-16T10:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "price phải lớn hơn 0"
}
```

### 7.3. Tạo sản phẩm thiếu name (rỗng)

```bash
curl -i -X POST http://localhost:8082/api/v1/products \
  -H "Content-Type: application/json" \
  -d '{"name":"","price":199000,"stockQuantity":10}'
```

```json
{
  "timestamp": "2026-06-16T10:31:00",
  "status": 400,
  "error": "Bad Request",
  "message": "name không được để trống"
}
```

### 7.4. Lấy chi tiết sản phẩm — GET /{id}

```bash
curl http://localhost:8082/api/v1/products/1
```

### 7.5. Lấy danh sách — GET /products

```bash
curl http://localhost:8082/api/v1/products
```

---

## 8. Luồng xử lý khi Validation thất bại

```
POST /products  {price: -500}
   │
   ▼  Controller: @Valid kiểm tra ProductRequestDTO
Validation THẤT BẠI (price không thỏa @Min(1))
   │
   ▼  Spring ném MethodArgumentNotValidException
       (KHÔNG gọi vào Service, KHÔNG chạm tới DB)
GlobalExceptionHandler (@RestControllerAdvice) bắt
   │
   ▼  map message lỗi vào ApiResponseError (status 400)
Response JSON 400 Bad Request trả về Client
```

Điểm quan trọng: dữ liệu sai bị chặn **ngay tại tầng Controller**, không bao giờ đi vào Service hay được lưu xuống database — đúng mục tiêu "đảm bảo tính đúng đắn của dữ liệu trước khi lưu vào DB".
