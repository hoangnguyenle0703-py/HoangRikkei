package com.ecommerce.orderservice.repository;

import com.ecommerce.orderservice.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Tầng Repository: chịu trách nhiệm truy xuất dữ liệu (data access layer).
 * <p>
 * Theo quy tắc đặt tên: tên Repository = tên Entity + hậu tố "Repository" → {@code OrderRepository}.
 * Kế thừa {@link JpaRepository} để có sẵn các thao tác CRUD cơ bản
 * (save, findById, findAll, deleteById...) mà không cần viết tay.
 * <p>
 * Lưu ý kiến trúc: tầng này KHÔNG chứa logic nghiệp vụ, chỉ thuần truy vấn dữ liệu.
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    /**
     * Tìm đơn hàng theo mã đơn (order code).
     * Spring Data JPA tự sinh câu truy vấn dựa trên tên phương thức.
     */
    Optional<Order> findByOrderCode(String orderCode);

    /**
     * Kiểm tra một mã đơn hàng đã tồn tại hay chưa.
     */
    boolean existsByOrderCode(String orderCode);
}
