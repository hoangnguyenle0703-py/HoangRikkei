# Order Service

Microservice quản lý đơn hàng trong hệ thống E-commerce. Dự án minh họa cấu trúc thư mục chuẩn và quy tắc đặt tên cho một Spring Boot microservice.

## Công nghệ

- Java 17, Spring Boot 3.3.2
- Spring Web, Spring Data JPA, Bean Validation
- H2 Database (in-memory, dùng để chạy thử)
- Lombok, Maven

## Cấu trúc thư mục (phân lớp theo trách nhiệm)

```
com.ecommerce.orderservice
├── OrderServiceApplication.java      # Entry point
├── controller/                       # Tầng tiếp nhận request HTTP
│   └── OrderController.java
├── service/                          # Tầng logic nghiệp vụ
│   ├── OrderService.java             # Interface
│   └── impl/
│       └── OrderServiceImpl.java     # Cài đặt
├── repository/                       # Tầng truy xuất dữ liệu
│   └── OrderRepository.java
├── entity/                           # Đối tượng ánh xạ CSDL
│   └── Order.java
├── dto/                              # Đối tượng truyền dữ liệu
│   ├── OrderRequest.java
│   ├── OrderResponse.java
│   └── ApiResponse.java
└── exception/                        # Xử lý lỗi tập trung
    ├── ResourceNotFoundException.java
    └── GlobalExceptionHandler.java
```

## Quy tắc đặt tên

| Thành phần | Quy tắc | Ví dụ |
|------------|---------|-------|
| Entity     | Danh từ số ít, PascalCase       | `Order` |
| Repository | `<Entity>` + `Repository`       | `OrderRepository` |
| Service    | `<Entity>` + `Service`          | `OrderService` |
| Controller | `<Entity>` + `Controller`       | `OrderController` |
| DTO đầu vào| `<Entity>` + `Request`          | `OrderRequest` |
| DTO đầu ra | `<Entity>` + `Response`         | `OrderResponse` |

## Luồng phụ thuộc

```
Controller  →  Service  →  Repository  →  Database
   (HTTP)     (nghiệp vụ)   (truy vấn)
```

Logic nghiệp vụ chỉ nằm ở tầng Service. Controller không truy cập trực tiếp Repository.

## Chạy dự án

```bash
mvn spring-boot:run
```

## API

| Method | Endpoint                         | Mô tả |
|--------|----------------------------------|-------|
| GET    | `/api/v1/orders/health-check`    | Kiểm tra service — trả về `Order Service is Up` |
| POST   | `/api/v1/orders`                 | Tạo đơn hàng mới |
| GET    | `/api/v1/orders/{id}`            | Lấy đơn hàng theo id |
| GET    | `/api/v1/orders`                 | Lấy danh sách đơn hàng |

### Thử nhanh health-check

```bash
curl http://localhost:8081/api/v1/orders/health-check
# → Order Service is Up
```
