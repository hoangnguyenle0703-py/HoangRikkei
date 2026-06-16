package com.ecommerce.productservice;

import com.ecommerce.productservice.dto.ProductResponseDTO;
import com.ecommerce.productservice.entity.ProductEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Kiểm thử đảm bảo DTO KHÔNG làm lộ các trường nhạy cảm của Entity.
 */
class ProductResponseDTOTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void dtoJson_shouldNotContainSensitiveFields() throws Exception {
        ProductEntity entity = ProductEntity.builder()
                .id(1L)
                .name("Áo thun cotton nam")
                .sku("SKU-AT-001")
                .importPrice(new BigDecimal("85000"))
                .sellPrice(new BigDecimal("199000"))
                .stockQuantity(320)
                .build();

        ProductResponseDTO dto = ProductResponseDTO.fromEntity(entity);
        String json = objectMapper.writeValueAsString(dto);

        // Các trường an toàn PHẢI có
        assertTrue(json.contains("name"));
        assertTrue(json.contains("sellPrice"));

        // Các trường nhạy cảm TUYỆT ĐỐI không được lộ
        assertFalse(json.contains("importPrice"));
        assertFalse(json.contains("sku"));
        assertFalse(json.contains("stockQuantity"));
    }
}
