package com.ecommerce.inventoryservice.service.impl;

import com.ecommerce.inventoryservice.dto.DbInfoDTO;
import com.ecommerce.inventoryservice.dto.ProductResponseDTO;
import com.ecommerce.inventoryservice.entity.Product;
import com.ecommerce.inventoryservice.repository.ProductRepository;
import com.ecommerce.inventoryservice.service.ProductService;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.List;

/**
 * Cài đặt ProductService.
 * <p>
 * {@link #getDatabaseInfo()} truy vấn current_database() để chứng minh
 * service đang kết nối tới inventory_db.
 */
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final DataSource dataSource;

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(ProductResponseDTO::fromEntity)
                .toList();
    }

    @Override
    public DbInfoDTO getDatabaseInfo() {
        try (Connection conn = dataSource.getConnection()) {
            String dbName;
            try (var stmt = conn.createStatement();
                 var rs = stmt.executeQuery("SELECT current_database()")) {
                rs.next();
                dbName = rs.getString(1);
            }
            String poolName = (dataSource instanceof HikariDataSource hikari)
                    ? hikari.getPoolName() : "unknown";
            var meta = conn.getMetaData();
            return DbInfoDTO.builder()
                    .serviceName("inventory-service")
                    .databaseName(dbName)
                    .jdbcUrl(meta.getURL())
                    .connectionPool(poolName)
                    .dbUser(meta.getUserName())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Không lấy được thông tin DB: " + e.getMessage(), e);
        }
    }

    /** Nạp dữ liệu mẫu vào inventory_db khi khởi động */
    @PostConstruct
    public void seed() {
        if (productRepository.count() == 0) {
            productRepository.save(Product.builder()
                    .name("Áo thun cotton nam").sku("SKU-AT-001")
                    .price(new BigDecimal("199000")).stockQuantity(320).build());
            productRepository.save(Product.builder()
                    .name("Tai nghe Bluetooth X9").sku("SKU-TN-009")
                    .price(new BigDecimal("499000")).stockQuantity(85).build());
        }
    }
}
