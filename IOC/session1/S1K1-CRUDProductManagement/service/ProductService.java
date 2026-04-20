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

    public Product addProduct(Product product) {
        products.add(product);
        return product;
    }

    public void updateProduct(int id,Product product) {
        for(int i = 0; i < products.size(); i++) {
            if(products.get(i).getId() == id) {
                products.set(i, product);
                return;
            }
        }
    }

    public void deleteProduct(int id) {
        products.removeIf(product -> product.getId() == id);
    }
}