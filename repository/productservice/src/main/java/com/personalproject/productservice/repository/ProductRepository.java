package com.personalproject.productservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.personalproject.productservice.model.Product;

public interface ProductRepository extends MongoRepository<Product, String>{
    
}
