package com.product.serverproduct.services;

import com.product.serverproduct.dtos.ProductsDTO;
import com.product.serverproduct.entities.Products;
import com.product.serverproduct.exceptions.ProductsNotFoundException;
import com.product.serverproduct.mappers.ProductMapperImp;
import com.product.serverproduct.repositories.ProductsRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class ProductServiceImpl implements ProductService{
    private ProductsRepository productsRepository;
    private ProductMapperImp dtoImpl;
    @Override
    public ProductsDTO saveProduct(ProductsDTO productsDTO) {
        log.info("Saving a Product");
        Products products= dtoImpl.fromProductDTO(productsDTO);
        Products productSave=productsRepository.save(products);
        return dtoImpl.fromProduct(productSave);
    }

    @Override
    public ProductsDTO getProduct(Long productId) throws ProductsNotFoundException {
        Products products=productsRepository.findById(productId).orElseThrow(()->new ProductsNotFoundException("Product Not Found"));
        return dtoImpl.fromProduct(products);
    }

    @Override
    public ProductsDTO updateProduct(ProductsDTO productsDTO) throws ProductsNotFoundException {
        log.info("Product Updating");
        Products products = productsRepository.findById(productsDTO.getId()).orElseThrow(()->new ProductsNotFoundException("Product Not Found"));
        if(products!=null){
            Products productsUpdate=dtoImpl.fromProductDTO(productsDTO);
            Products updateProducts=productsRepository.save(productsUpdate);
            return dtoImpl.fromProduct(updateProducts);
        }
        return null;
    }

    @Override
    public void deleteProduct(Long productId) throws ProductsNotFoundException{
        Products products= productsRepository.findById(productId).orElseThrow(()->new ProductsNotFoundException("Product Not Found"));
        productsRepository.delete(products);
    }

    @Override
    public List<ProductsDTO> searchProduct(String keyword) {
        List<Products> productsList=productsRepository.findByNameContains("%"+keyword+"%");
        return productsList.stream().map(products -> dtoImpl.fromProduct(products)).collect(Collectors.toList());
    }

    @Override
    public Page<ProductsDTO> listProducts(String name,int page, int size) {
        Page<Products> productsList=productsRepository.findAllByNameContainingIgnoreCase(name,PageRequest.of(page, size));
        return productsList.map(products -> dtoImpl.fromProduct(products));
    }
}
