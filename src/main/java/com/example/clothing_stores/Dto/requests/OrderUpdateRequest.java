package com.example.clothing_stores.Dto.requests;

public record OrderUpdateRequest(
        AddressRequest shippingAddress,
        AddressRequest billingAddress,
        String note
) { }
