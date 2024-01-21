package com.product.serverproduct.services;

import com.product.serverproduct.dtos.ProductsDTO;
import com.product.serverproduct.exceptions.ProductsNotFoundException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    ProductsDTO saveProduct(ProductsDTO productsDTO);

    ProductsDTO getProduct(Long productId) throws ProductsNotFoundException;
    ProductsDTO updateProduct(ProductsDTO  customerDTO) throws  ProductsNotFoundException;
    void deleteProduct(Long productId) throws ProductsNotFoundException;
    List<ProductsDTO> searchProduct(String keyword);

    Page<ProductsDTO> listProducts(String name, int page, int size);
}
