package com.example.clothing_stores.Services;

import com.example.clothing_stores.Dto.requests.*;
import com.example.clothing_stores.Dto.responses.OrderResponse;
import com.example.clothing_stores.Enums.ErrorCode;
import com.example.clothing_stores.Enums.OrderStatus;
import com.example.clothing_stores.Exception.AppException;
import com.example.clothing_stores.Mapper.OrderMapper;
import com.example.clothing_stores.Models.Orders.Order;
import com.example.clothing_stores.Models.Orders.OrderItem;
import com.example.clothing_stores.Models.Product.Product;
import com.example.clothing_stores.Models.Product.ProductVariant;
import com.example.clothing_stores.Models.User.AppUser;
import com.example.clothing_stores.Repo.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderService {
    OrderRepository orderRepository;
    ProductVariantRepository variantRepository;
    AppUserRepository userRepository;

    OrderMapper mapper;
    private final ProductRepository productRepository;

    public OrderResponse create (OrderCreationRequest request, String userId){
        Order order = mapper.toEntity(request);

        if (userId != null) {
            AppUser user = userRepository.findById(userId)
                    .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
            order.setUser(user);
        }
        else order.setUser(null);

        List<OrderItem> items = request.itemId().stream().map(this::createItem).toList();
        order.setItems(items);

        BigDecimal subtotalAmount = items.stream()
                .map(OrderItem::getLineTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setSubtotalAmount(subtotalAmount);

        BigDecimal discountAmount = BigDecimal.ZERO;

        order.setDiscountAmount(discountAmount);
        order.setTotalAmount(subtotalAmount.subtract(discountAmount));

        return mapper.toResponse(orderRepository.save(order));
    }

    public OrderResponse update (Long id, OrderUpdateRequest request){
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.ORDER_NOT_EXISTED));

        if(order.getStatus().equals(OrderStatus.COMPLETED))
            throw new AppException(ErrorCode.ORDER_COMPLETED);

        mapper.toEntity(order, request);

        return mapper.toResponse(orderRepository.save(order));
    }

    public OrderResponse changeStatus (Long id, OrderStatus status){
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.ORDER_NOT_EXISTED));

        if(order.getStatus().equals(OrderStatus.COMPLETED))
            throw new AppException(ErrorCode.ORDER_COMPLETED);

        order.setStatus(status);

        return mapper.toResponse(orderRepository.save(order));
    }

    public void deleteById(Long id) {
        orderRepository.deleteById(id);
    }

    public Page<OrderResponse> findAll (Pageable pageable) {
        return orderRepository.findAll(pageable).map(mapper::toResponse);
    }

    public OrderResponse findByCode (String code) {
        return mapper.toResponse(orderRepository.findByCode(code).orElseThrow(
                () -> new AppException(ErrorCode.ORDER_NOT_EXISTED)));
    }
    
    private OrderItem createItem (OrderItemRequest request) {
        Product product = productRepository.findById(request.productId()).orElseThrow(
                () -> new AppException(ErrorCode.PRODUCT_NOT_EXISTED));

        ProductVariant variant = variantRepository.findById(request.variantId()).orElseThrow(
                () -> new AppException(ErrorCode.PRODUCT_NOT_EXISTED));

        BigDecimal lineTotal = variant.getPrice().multiply(BigDecimal.valueOf(request.quantity()));

        Map<String, String> attrs = new HashMap<>();
        attrs.put("size", variant.getSize());
        attrs.put("color", variant.getColor());

        return OrderItem.builder()
                .product(product)
                .variant(variant)
                .sku(variant.getSku())
                .name(product.getName())
                .attrs(attrs.toString())
                .unitPrice(variant.getPrice())
                .quantity(request.quantity())
                .lineTotal(lineTotal)
                .build();
    }
}
