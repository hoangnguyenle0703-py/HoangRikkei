# Product Service

Microservice quản lý sản phẩm, minh họa việc **thiết kế DTO và Response chuẩn hóa** để không trả Entity trực tiếp ra ngoài.

## Vấn đề: tại sao không trả Entity trực tiếp?

`ProductEntity` chứa các trường **nhạy cảm nội bộ**:

| Trường | Loại | Có nên lộ cho khách hàng? |
|--------|------|---------------------------|
| `id` | công khai | ✅ |
| `name` | công khai | ✅ |
| `sellPrice` | công khai | ✅ |
| `sku` | mã kho nội bộ | ❌ |
| `importPrice` | giá nhập (lộ biên lợi nhuận) | ❌ |
| `stockQuantity` | tồn kho (đối thủ lợi dụng) | ❌ |

Nếu trả thẳng Entity ra JSON, toàn bộ thông tin nội bộ bị phơi bày.

## Giải pháp: ProductResponseDTO

`ProductResponseDTO` chỉ chứa 3 trường an toàn: `id`, `name`, `sellPrice`.
Tầng Service map `Entity → DTO` qua `ProductResponseDTO.fromEntity(...)`, lọc bỏ trường nhạy cảm trước khi trả về Controller.

## Cấu trúc

```
com.ecommerce.productservice
├── controller/   ProductController.java
├── service/      ProductService.java + impl/ProductServiceImpl.java
├── repository/   ProductRepository.java
├── entity/       ProductEntity.java       (6 trường, có nhạy cảm)
├── dto/          ProductResponseDTO.java  (chỉ 3 trường an toàn)
│                 ApiResponse.java
└── exception/    ResourceNotFoundException.java + GlobalExceptionHandler.java
```

## Chạy

```bash
mvn spring-boot:run
```

## API

| Method | Endpoint                  | Mô tả |
|--------|---------------------------|-------|
| GET    | `/api/v1/products`        | Danh sách sản phẩm (đã lọc) |
| GET    | `/api/v1/products/{id}`   | Chi tiết một sản phẩm (đã lọc) |

### Ví dụ kết quả

```bash
curl http://localhost:8082/api/v1/products/1
```

```json
{
  "success": true,
  "message": "Lấy sản phẩm thành công",
  "data": {
    "id": 1,
    "name": "Áo thun cotton nam",
    "sellPrice": 199000
  },
  "timestamp": "2026-06-15T10:00:00"
}
```

Không hề có `importPrice`, `sku`, `stockQuantity` trong response → thông tin nội bộ được bảo mật.
