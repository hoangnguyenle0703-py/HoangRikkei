package com.ecommerce.customerservice.service.impl;

import com.ecommerce.customerservice.dto.CustomerResponse;
import com.ecommerce.customerservice.entity.Customer;
import com.ecommerce.customerservice.repository.CustomerRepository;
import com.ecommerce.customerservice.service.CustomerService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public CustomerResponse getCustomerById(Long id) {
        Customer c = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer " + id + " không tồn tại"));
        return CustomerResponse.fromEntity(c);
    }

    @PostConstruct
    public void seed() {
        if (customerRepository.count() == 0) {
            customerRepository.save(Customer.builder()
                    .fullName("Nguyễn Văn Huy").email("huy@example.com")
                    .password("$2a$hashed$pwd").address("Hà Nội").build());
        }
    }
}
