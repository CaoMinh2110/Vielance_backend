package com.example.clothing_stores.Dto.requests;

import com.example.clothing_stores.Enums.ProductStatus;

import java.math.BigDecimal;
import java.util.List;

public record ProductVariantRequest(
        String color,
        String size,
        BigDecimal price,
        ProductStatus status,
        Integer stock){}
