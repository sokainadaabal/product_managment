package com.product.serverproduct.dtos;

import lombok.Data;

@Data
public class ProductsDTO {
    private Long id;
    private String name;
    private String description;
    private Double prix;
}
