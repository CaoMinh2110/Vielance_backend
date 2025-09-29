package com.example.clothing_stores.Services;

import com.example.clothing_stores.Dto.requests.CartItemRequest;
import com.example.clothing_stores.Dto.responses.CartResponse;
import com.example.clothing_stores.Exception.AppException;
import com.example.clothing_stores.Enums.ErrorCode;
import com.example.clothing_stores.Mapper.CartMapper;
import com.example.clothing_stores.Models.Carts.Cart;
import com.example.clothing_stores.Models.Carts.CartItem;
import com.example.clothing_stores.Models.Product.ProductVariant;
import com.example.clothing_stores.Models.User.AppUser;
import com.example.clothing_stores.Repo.AppUserRepository;
import com.example.clothing_stores.Repo.CartItemRepository;
import com.example.clothing_stores.Repo.CartRepository;
import com.example.clothing_stores.Repo.ProductVariantRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CartService {
    CartRepository cartRepository;
    ProductVariantRepository variantRepository;
    CartItemRepository itemRepository;
    AppUserRepository userRepository;

    CartMapper mapper;

    public CartResponse getCart (UserDetails userDetails, String cartId){
        Cart cart = getOrCreateCart(userDetails, cartId);

        CartResponse response = mapper.toResponse(cart);
        response.setItems(cart.getItems().stream().map(mapper::toItemResponse).toList());

        return response;
    }

    public CartResponse addItem(UserDetails userDetails, String cartId, CartItemRequest request){
        Cart cart = getOrCreateCart(userDetails, cartId);

        ProductVariant variant = variantRepository.findById(request.productVariantId()).orElseThrow(
                () -> new AppException(ErrorCode.PRODUCT_NOT_EXISTED));

        mergeItem(cart, variant, request.quantity());

        CartResponse response = mapper.toResponse(cart);
        response.setItems(cart.getItems().stream().map(mapper::toItemResponse).toList());

        return response;
    }

    public CartResponse updateItemQuantity(String cartItemId, int quantity){
        CartItem item = itemRepository.findById(cartItemId).orElseThrow(
                () -> new AppException(ErrorCode.PRODUCT_NOT_EXISTED));

        item.setQuantity(quantity);
        itemRepository.save(item);

        return mapper.toResponse(item.getCart());
    }

    public void removeItem (String cartItemId){
        itemRepository.deleteById(cartItemId);
    }

    public void clearCart(String cartId) {
        Cart cart = getOrCreateCart(null, cartId);
        cart.getItems().clear();
        cartRepository.save(cart);
    }

    public CartResponse mergeCart(UserDetails userDetails, String guestCartId) {
        Cart userCart = getOrCreateCart(userDetails, null);
        Cart guestCart = getOrCreateCart(null, guestCartId);

        for (CartItem guestItem : guestCart.getItems()) {
            mergeItem(userCart, guestItem.getProductVariant(), guestItem.getQuantity());
        }

        cartRepository.delete(guestCart);

        return mapper.toResponse(cartRepository.save(userCart));
    }

    private Cart getOrCreateCart(UserDetails userDetails, String cartId) {
        if (userDetails != null) {
            String userId = userDetails.getUsername();
            return cartRepository.findByUser_Id(userId).orElseGet(() -> {
                AppUser user = userRepository.findById(userId).orElseThrow(
                        () -> new AppException(ErrorCode.USER_NOT_EXISTED));

                return cartRepository.save(Cart.builder()
                        .user(user)
                        .build());
            });
        }
        else if (cartId != null)
            return cartRepository.findById(cartId)
                    .orElseThrow(() -> new AppException(ErrorCode.CART_NOT_EXISTED));

        else 
            return cartRepository.save(Cart.builder()
                    .user(null)
                    .build());
    }

    private void mergeItem (Cart cart, ProductVariant variant, int quantity){
        CartItem item = cart.getItems().stream()
                .filter(i -> i.getProductVariant().getId().equals(variant.getId()))
                .findFirst()
                .orElse(null);

        if (item != null)
            item.setQuantity(item.getQuantity() + quantity);
        else {
            item = CartItem.builder()
                    .cart(cart)
                    .productVariant(variant)
                    .quantity(quantity)
                    .priceAtTime(variant.getPrice())
                    .build();
            cart.getItems().add(item);
        }
    }
}
