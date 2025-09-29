package com.example.clothing_stores.Repo;

import com.example.clothing_stores.Models.Product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByName(String name);

    @Query(""" 
            SELECT DISTINCT p 
            FROM Product p 
            LEFT JOIN p.categories c 
            WHERE (:keyword IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%'))) 
            AND (:categories IS NULL OR c.id IN :categories) 
            """)
    Page<Product> searchProducts(
            @Param("keyword") String keyword,
            @Param("categories") List<Long> categories, // id kiểu String vì Product.id là UUID
            Pageable pageable
    );


}
