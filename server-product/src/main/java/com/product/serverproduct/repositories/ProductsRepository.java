package com.product.serverproduct.repositories;

import com.product.serverproduct.dtos.ProductsDTO;
import com.product.serverproduct.entities.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductsRepository extends JpaRepository<Products,Long> {
    @Query("select P from Products P where P.name like :kw")
    List<Products> findByNameContains(@Param("kw")String keyword);
    Page<Products> findAllByNameContainingIgnoreCase(String name,Pageable pageable);
}
