package com.example.clothing_stores.Dto.requests;

public record OrderItemRequest (
        Long productId,
        Long variantId,
        int quantity
){
}
