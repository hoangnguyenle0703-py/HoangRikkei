package com.ecommerce.productservice.service;

import com.ecommerce.productservice.dto.ProductResponse;

public interface ProductService {
    ProductResponse getProductById(Long id);
}
