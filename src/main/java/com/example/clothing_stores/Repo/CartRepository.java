package com.example.clothing_stores.Repo;

import com.example.clothing_stores.Models.Carts.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, String> {
    Optional<Cart> findByUser_Id(String userId);
}
