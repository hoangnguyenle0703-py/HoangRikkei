package com.ecommerce.userservice.repository;

import com.ecommerce.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository truy xuất bảng users trong user_db.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
