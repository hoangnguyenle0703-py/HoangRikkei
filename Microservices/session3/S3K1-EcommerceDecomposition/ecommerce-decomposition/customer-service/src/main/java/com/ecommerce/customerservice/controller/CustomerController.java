package com.ecommerce.customerservice.controller;

import com.ecommerce.customerservice.dto.CustomerResponse;
import com.ecommerce.customerservice.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * API ranh giới (boundary) của Customer Service.
 * Service khác CHỈ được lấy dữ liệu khách hàng qua API này, không truy cập DB trực tiếp.
 */
@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/{id}")
    public CustomerResponse getCustomerById(@PathVariable Long id) {
        return customerService.getCustomerById(id);
    }
}
