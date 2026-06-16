package com.ecommerce.productservice.service.impl;

import com.ecommerce.productservice.dto.ProductResponse;
import com.ecommerce.productservice.entity.Product;
import com.ecommerce.productservice.repository.ProductRepository;
import com.ecommerce.productservice.service.ProductService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public ProductResponse getProductById(Long id) {
        Product p = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product " + id + " không tồn tại"));
        return ProductResponse.fromEntity(p);
    }

    @PostConstruct
    public void seed() {
        if (productRepository.count() == 0) {
            productRepository.save(Product.builder()
                    .name("Áo thun cotton").price(new BigDecimal("199000"))
                    .stockQuantity(320).description("Áo thun nam cotton 100%").build());
        }
    }
}
