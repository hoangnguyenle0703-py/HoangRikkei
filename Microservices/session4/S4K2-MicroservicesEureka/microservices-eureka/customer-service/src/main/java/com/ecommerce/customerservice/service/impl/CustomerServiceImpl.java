package com.ecommerce.customerservice.service.impl;

import com.ecommerce.customerservice.dto.CustomerRequestDTO;
import com.ecommerce.customerservice.dto.CustomerResponseDTO;
import com.ecommerce.customerservice.dto.LoginRequestDTO;
import com.ecommerce.customerservice.entity.Customer;
import com.ecommerce.customerservice.exception.DuplicateResourceException;
import com.ecommerce.customerservice.exception.InvalidCredentialsException;
import com.ecommerce.customerservice.exception.ResourceNotFoundException;
import com.ecommerce.customerservice.repository.CustomerRepository;
import com.ecommerce.customerservice.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public CustomerResponseDTO register(CustomerRequestDTO request) {
        if (customerRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException(
                    "Email " + request.getEmail() + " đã được đăng ký!");
        }
        Customer customer = Customer.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                // MÃ HÓA mật khẩu bằng BCrypt trước khi lưu
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        Customer saved = customerRepository.save(customer);
        return CustomerResponseDTO.fromEntity(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerResponseDTO getById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.customer(id));
        return CustomerResponseDTO.fromEntity(customer);
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerResponseDTO login(LoginRequestDTO request) {
        Customer customer = customerRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException("email or password incorrect"));

        // So khớp mật khẩu gốc với hash trong DB
        if (!passwordEncoder.matches(request.getPassword(), customer.getPassword())) {
            throw new InvalidCredentialsException("email or password incorrect");
        }
        return CustomerResponseDTO.fromEntity(customer);
    }
}
