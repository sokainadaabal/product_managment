package com.product.serverproduct.web;

import com.product.serverproduct.dtos.ProductsDTO;
import com.product.serverproduct.exceptions.ProductsNotFoundException;
import com.product.serverproduct.services.ProductServiceImpl;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/products")
@CrossOrigin(value = "*",maxAge = 3600)
@SecurityRequirement(name = "productApi")
@AllArgsConstructor
public class ProductRestController {
    private ProductServiceImpl productService;
    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/findAll")
    public Page<ProductsDTO> getAllProducts(
            @RequestParam(name="page",defaultValue = "0") int page
            ,@RequestParam(name="size",defaultValue = "5") int size,
            @RequestParam(name = "searchName",defaultValue = "") String searchName){
        return productService.listProducts(searchName,page,size);
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/search")
    public List<ProductsDTO> searchProduct(@RequestParam(name="keyword",defaultValue = "") String keyWord){
        return productService.searchProduct(keyWord);
    }
   @PreAuthorize("hasAuthority('USER')")
   @GetMapping("/product/{id}")
   public ProductsDTO getProduct(@PathVariable("id") Long productId) throws ProductsNotFoundException{
        return productService.getProduct(productId);
   }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/addProduct")
    public ProductsDTO saveProduct(@RequestBody ProductsDTO productsDTO){
        return productService.saveProduct(productsDTO);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/updateProduct")
    public ProductsDTO updateProduct(@RequestBody ProductsDTO productsDTO) throws ProductsNotFoundException {
        return productService.updateProduct(productsDTO);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public void deleteProduct(@PathVariable("id") Long productId) throws ProductsNotFoundException{
        productService.deleteProduct(productId);
    }


}
