package com.springsecurity.springsecurity.service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;

import com.springsecurity.springsecurity.entity.Product;

import jakarta.annotation.PostConstruct;

@Service
public class ProductService {

    List<Product> products = null;
    @PostConstruct
    public void loadProductsFromDb(){
        products = IntStream.rangeClosed(1, 100)
        .mapToObj( i-> Product.builder()
        .productId(i)
        .productName("product " +i)
        .quantity(new Random().nextInt(10))
        .price(new Random().nextInt(5000))
        .build())
        .collect(Collectors.toList());       
    }

    public List<Product> getAllProducts(){
        return products;
    }

    public Product getProductById(int id){
       return  products.stream()
        .filter(p -> (p.getProductId() == id))
        .findFirst()
        .orElseThrow(() -> new RuntimeException("Product not found"));
    }


}
