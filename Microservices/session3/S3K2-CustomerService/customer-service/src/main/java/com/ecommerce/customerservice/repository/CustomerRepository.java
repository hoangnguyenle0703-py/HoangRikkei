package com.ecommerce.customerservice.repository;

import com.ecommerce.customerservice.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Tầng truy xuất dữ liệu, kế thừa JpaRepository<Customer, Long>.
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    /** Tìm khách hàng theo email — phục vụ chức năng đăng nhập */
    Optional<Customer> findByEmail(String email);

    boolean existsByEmail(String email);
}
