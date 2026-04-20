package com.example.productmanagement.service;

import com.example.productmanagement.model.Product;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    private List<Product> products = new ArrayList<>();

    public ProductService() {
        products.add(new Product(1, "Laptop Gaming", 25000000));
        products.add(new Product(2, "Bàn phím cơ AULA F75", 800000));
        products.add(new Product(3, "Chuột không dây", 500000));
    }

    public List<Product> getAllProducts() {
        return products;
    }
}