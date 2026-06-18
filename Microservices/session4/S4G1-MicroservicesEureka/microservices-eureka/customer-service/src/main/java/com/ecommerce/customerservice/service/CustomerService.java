package com.ecommerce.customerservice.service;

import com.ecommerce.customerservice.dto.CustomerRequestDTO;
import com.ecommerce.customerservice.dto.CustomerResponseDTO;
import com.ecommerce.customerservice.dto.LoginRequestDTO;

public interface CustomerService {

    /** Đăng ký khách hàng mới (mã hóa mật khẩu trước khi lưu) */
    CustomerResponseDTO register(CustomerRequestDTO request);

    /** Lấy khách hàng theo id; ném ResourceNotFoundException nếu không tồn tại */
    CustomerResponseDTO getById(Long id);

    /** Đăng nhập; ném InvalidCredentialsException nếu sai email/password */
    CustomerResponseDTO login(LoginRequestDTO request);
}
