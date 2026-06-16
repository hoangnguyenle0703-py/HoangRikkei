package com.ecommerce.productservice.service;

import com.ecommerce.productservice.dto.ProductRequestDTO;
import com.ecommerce.productservice.dto.ProductResponseDTO;

import java.util.List;

public interface ProductService {
    ProductResponseDTO create(ProductRequestDTO request);
    ProductResponseDTO getById(Long id);
    List<ProductResponseDTO> getAll();
}
