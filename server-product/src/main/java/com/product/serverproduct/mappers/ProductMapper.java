package com.product.serverproduct.mappers;

import com.product.serverproduct.dtos.ProductsDTO;
import com.product.serverproduct.entities.Products;

public interface ProductMapper {

    ProductsDTO fromProduct(Products products);
    Products fromProductDTO(ProductsDTO productsDTO);
}
