package com.ecommerce.inventoryservice.service;

import com.ecommerce.inventoryservice.dto.DbInfoDTO;
import com.ecommerce.inventoryservice.dto.ProductResponseDTO;

import java.util.List;

/**
 * Tầng nghiệp vụ cho Inventory-Service.
 */
public interface ProductService {
    List<ProductResponseDTO> getAllProducts();
    DbInfoDTO getDatabaseInfo();
}
