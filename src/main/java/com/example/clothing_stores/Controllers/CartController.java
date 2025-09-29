package com.example.clothing_stores.Controllers;

import com.example.clothing_stores.Dto.requests.CartItemRequest;
import com.example.clothing_stores.Dto.responses.ApiResponse;
import com.example.clothing_stores.Dto.responses.CartResponse;
import com.example.clothing_stores.Services.CartService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CartController {
    CartService service;

    @GetMapping
    ApiResponse<CartResponse> getCart (@AuthenticationPrincipal UserDetails userDetails,
                                       @RequestParam(required = false) String cartId){
        return ApiResponse.<CartResponse>builder()
                .result(service.getCart(userDetails, cartId))
                .build();
    }

    @PostMapping("/items")
    ApiResponse<CartResponse> addItem (@AuthenticationPrincipal UserDetails userDetails,
                                       @RequestParam(required = false) String cartId,
                                       @RequestBody CartItemRequest request) {
        return ApiResponse.<CartResponse>builder()
                .result(service.addItem(userDetails, cartId, request))
                .build();
    }

    @PutMapping("/items/{itemId}")
    ApiResponse<CartResponse> updateItemQuantity (@PathVariable String itemId,
                                                  @RequestParam int quantity){
        return ApiResponse.<CartResponse>builder()
                .result(service.updateItemQuantity(itemId, quantity))
                .build();
    }

    @DeleteMapping("/items/{itemId}")
    ApiResponse<Void> removeItem (@PathVariable String itemId){
        service.removeItem(itemId);
        return ApiResponse.<Void>builder()
                .message("Item has been removed!")
                .build();
    }

    @DeleteMapping
    ApiResponse<Void> clearCart (@RequestParam String id){
        service.clearCart(id);
        return ApiResponse.<Void>builder()
                .message("Cart has been clear!")
                .build();
    }

    @PostMapping("/merge")
    ApiResponse<CartResponse> mergeCart (@AuthenticationPrincipal UserDetails userDetails,
                                         @RequestParam(required = false) String guestCartId){
        return ApiResponse.<CartResponse>builder()
                .result(service.mergeCart(userDetails, guestCartId))
                .build();
    }
}
