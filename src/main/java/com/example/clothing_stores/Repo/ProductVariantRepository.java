package com.example.clothing_stores.Repo;

import com.example.clothing_stores.Models.Product.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductVariantRepository extends JpaRepository<ProductVariant, Long> {
    List<ProductVariant> findAllByProduct_Id(Long productId);
}
