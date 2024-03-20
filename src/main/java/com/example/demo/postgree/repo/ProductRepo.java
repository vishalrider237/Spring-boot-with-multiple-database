package com.example.demo.postgree.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.postgree.entity.Product;

public interface ProductRepo extends JpaRepository<Product,Integer> {
    Product findByName(String name);
}
