package com.example.clothing_stores.Dto.requests;

import java.util.List;

public record OrderCreationRequest (
        String couponId,
        AddressRequest shippingAddress,
        AddressRequest billingAddress,
        String note,
        List<OrderItemRequest> itemId
) { }
