package com.ecommerce.productservice.service.impl;

import com.ecommerce.productservice.dto.ProductResponseDTO;
import com.ecommerce.productservice.entity.ProductEntity;
import com.ecommerce.productservice.exception.ResourceNotFoundException;
import com.ecommerce.productservice.repository.ProductRepository;
import com.ecommerce.productservice.service.ProductService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * Cài đặt {@link ProductService}.
 * <p>
 * Mỗi phương thức lấy Entity từ Repository rồi gọi
 * {@code ProductResponseDTO.fromEntity(...)} để LỌC dữ liệu trước khi trả về.
 * Đây là nơi diễn ra việc "map dữ liệu từ Entity sang DTO".
 */
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    @Transactional(readOnly = true)
    public ProductResponseDTO getProductById(Long id) {
        ProductEntity entity = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
        // Lọc dữ liệu nhạy cảm: chỉ giữ id, name, sellPrice
        return ProductResponseDTO.fromEntity(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(ProductResponseDTO::fromEntity)
                .toList();
    }

    /**
     * Nạp sẵn vài sản phẩm mẫu khi khởi động (có đầy đủ trường nhạy cảm trong DB)
     * để minh họa việc DTO che giấu chúng khi trả ra API.
     */
    @PostConstruct
    public void seedData() {
        if (productRepository.count() == 0) {
            productRepository.save(ProductEntity.builder()
                    .name("Áo thun cotton nam")
                    .sku("SKU-AT-001")
                    .importPrice(new BigDecimal("85000"))
                    .sellPrice(new BigDecimal("199000"))
                    .stockQuantity(320)
                    .build());
            productRepository.save(ProductEntity.builder()
                    .name("Tai nghe Bluetooth X9")
                    .sku("SKU-TN-009")
                    .importPrice(new BigDecimal("210000"))
                    .sellPrice(new BigDecimal("499000"))
                    .stockQuantity(85)
                    .build());
        }
    }
}
