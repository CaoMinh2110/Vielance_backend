package com.example.clothing_stores.Mapper;

import com.example.clothing_stores.Dto.responses.CartItemResponse;
import com.example.clothing_stores.Dto.responses.CartResponse;
import com.example.clothing_stores.Models.Carts.Cart;
import com.example.clothing_stores.Models.Carts.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {
    @Mapping(source = "user.id", target = "userId")
    CartResponse toResponse (Cart address);

    @Mapping(target = "variantId", source = "productVariant.id")
    CartItemResponse toItemResponse (CartItem address);
}
