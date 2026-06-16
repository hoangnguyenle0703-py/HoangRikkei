package com.ecommerce.customerservice.controller;

import com.ecommerce.customerservice.dto.CustomerRequestDTO;
import com.ecommerce.customerservice.dto.CustomerResponseDTO;
import com.ecommerce.customerservice.dto.LoginRequestDTO;
import com.ecommerce.customerservice.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    /** POST /api/v1/customers/register — đăng ký, trả CustomerResponseDTO (không có password) */
    @PostMapping("/register")
    public ResponseEntity<CustomerResponseDTO> register(@Valid @RequestBody CustomerRequestDTO request) {
        return new ResponseEntity<>(customerService.register(request), HttpStatus.CREATED);
    }

    /** GET /api/v1/customers/{id} — lấy khách hàng; nếu không thấy -> 404 ApiResponseError */
    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.getById(id));
    }

    /** PUT /api/v1/customers/login — đăng nhập; đúng -> CustomerResponseDTO, sai -> message lỗi */
    @PutMapping("/login")
    public ResponseEntity<CustomerResponseDTO> login(@Valid @RequestBody LoginRequestDTO request) {
        return ResponseEntity.ok(customerService.login(request));
    }
}
