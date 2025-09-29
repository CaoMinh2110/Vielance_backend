package com.example.clothing_stores.Repo;

import com.example.clothing_stores.Models.Carts.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, String> {
}
