package com.personalproject.productservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.personalproject.productservice.dto.ProductRequest;
import com.personalproject.productservice.dto.ProductResponse;
import com.personalproject.productservice.model.Product;
import com.personalproject.productservice.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    
    public void createProduct(ProductRequest productRequest){
        Product product = Product.builder()
        .productName(productRequest.getProductName())
        .productDescription(productRequest.getProductDescription())
        .price(productRequest.getPrice())
        .build();

        productRepository.save(product);

        log.info("Product {} saved ", product.getProductId());
    }

    public List<ProductResponse> getAllProducts() {
       return productRepository.findAll()
        .stream()
        .map(product -> mapToProductResponse(product))
        .toList(); 
    }

    private ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
                      .productId(product.getProductId())
                      .productName(product.getProductName())
                      .productDescription(product.getProductDescription())
                      .price(product.getPrice())
                      .build();
    }
}
