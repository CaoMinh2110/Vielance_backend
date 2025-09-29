package com.example.clothing_stores.Mapper;

import com.example.clothing_stores.Dto.requests.AddressRequest;
import com.example.clothing_stores.Dto.requests.OrderCreationRequest;
import com.example.clothing_stores.Dto.requests.OrderUpdateRequest;
import com.example.clothing_stores.Dto.responses.OrderResponse;
import com.example.clothing_stores.Models.Orders.Order;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(target = "shippingAddress", source = "shippingAddress", qualifiedByName = "mapAddressToString")
    @Mapping(target = "billingAddress", source = "billingAddress", qualifiedByName = "mapAddressToString")
    Order toEntity (OrderCreationRequest request);

    @Mapping(target = "shippingAddress", source = "shippingAddress", qualifiedByName = "mapAddressToString")
    @Mapping(target = "billingAddress", source = "billingAddress", qualifiedByName = "mapAddressToString")
    void toEntity (@MappingTarget Order Order, OrderUpdateRequest request);

    @Mapping(target = "userId", source = "user.id")
    OrderResponse toResponse (Order Order);

    @Named("mapAddressToString")
    default String mapAddressToString(AddressRequest addressRequest) {
        try {
            return new ObjectMapper().writeValueAsString(addressRequest);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
