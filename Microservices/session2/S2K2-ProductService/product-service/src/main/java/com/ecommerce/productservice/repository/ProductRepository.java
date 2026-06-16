package com.ecommerce.productservice.repository;

import com.ecommerce.productservice.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Tầng Repository: truy xuất dữ liệu sản phẩm.
 * <p>
 * Theo quy tắc đặt tên: tên Entity nghiệp vụ ("Product") + hậu tố "Repository".
 */
@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
}
