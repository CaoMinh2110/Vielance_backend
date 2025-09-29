package com.example.clothing_stores.Mapper;

import com.example.clothing_stores.Dto.requests.OrderItemRequest;
import com.example.clothing_stores.Dto.responses.OrderItemResponse;
import com.example.clothing_stores.Models.Orders.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {
    OrderItem toEntity (OrderItemRequest request);

    void toEntity (@MappingTarget OrderItem item, OrderItemRequest request);

    @Mapping(target = "orderId", source = "order.id")
    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "variantId", source = "variant.id")
    OrderItemResponse toResponse (OrderItem item);
}
