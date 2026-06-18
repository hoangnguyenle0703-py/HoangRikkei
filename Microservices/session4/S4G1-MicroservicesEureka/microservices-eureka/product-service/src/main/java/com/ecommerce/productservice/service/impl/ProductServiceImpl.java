package com.ecommerce.productservice.service.impl;

import com.ecommerce.productservice.dto.ProductRequestDTO;
import com.ecommerce.productservice.dto.ProductResponseDTO;
import com.ecommerce.productservice.entity.Product;
import com.ecommerce.productservice.exception.ResourceNotFoundException;
import com.ecommerce.productservice.repository.ProductRepository;
import com.ecommerce.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    @Transactional
    public ProductResponseDTO create(ProductRequestDTO request) {
        // Dữ liệu tới đây ĐÃ được Validation kiểm tra hợp lệ (price > 0, stock >= 0...)
        Product product = Product.builder()
                .name(request.getName())
                .price(request.getPrice())
                .stockQuantity(request.getStockQuantity())
                .build();
        return ProductResponseDTO.fromEntity(productRepository.save(product));
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponseDTO getById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.product(id));
        return ProductResponseDTO.fromEntity(product);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDTO> getAll() {
        return productRepository.findAll().stream()
                .map(ProductResponseDTO::fromEntity)
                .toList();
    }
}
