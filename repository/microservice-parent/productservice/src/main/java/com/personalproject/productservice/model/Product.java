package com.personalproject.productservice.model;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Document(value = "product")
@Builder
@Data
public class Product {
    @Id
    private String productId;
    private String productName;
    private String productDescription;
    private BigDecimal price;

}
