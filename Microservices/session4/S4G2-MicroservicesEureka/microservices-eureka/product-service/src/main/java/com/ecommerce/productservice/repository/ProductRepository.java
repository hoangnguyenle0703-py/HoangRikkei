package com.ecommerce.productservice.repository;

import com.ecommerce.productservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Tầng truy xuất dữ liệu, kế thừa JpaRepository<Product, Long>.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
