package com.ecommerce.customerservice.service;

import com.ecommerce.customerservice.dto.CustomerResponse;

public interface CustomerService {
    CustomerResponse getCustomerById(Long id);
}
