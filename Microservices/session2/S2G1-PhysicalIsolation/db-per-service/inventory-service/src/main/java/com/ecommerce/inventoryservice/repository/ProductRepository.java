package com.ecommerce.inventoryservice.repository;

import com.ecommerce.inventoryservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository truy xuất bảng products trong inventory_db.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
