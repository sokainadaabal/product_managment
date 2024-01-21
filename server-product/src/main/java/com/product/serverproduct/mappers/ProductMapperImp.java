package com.product.serverproduct.mappers;

import com.product.serverproduct.dtos.ProductsDTO;
import com.product.serverproduct.entities.Products;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class ProductMapperImp implements ProductMapper {
    @Override
    public ProductsDTO fromProduct(Products products) {
        ProductsDTO productsDTO = new ProductsDTO();
        BeanUtils.copyProperties(products,productsDTO);
        return productsDTO;
    }
    @Override
    public Products fromProductDTO(ProductsDTO productsDTO) {
        Products products = new Products();
        BeanUtils.copyProperties(productsDTO,products);
        return products;
    }
}
