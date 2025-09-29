package com.example.clothing_stores.Dto.requests;

import com.example.clothing_stores.Enums.DiscountType;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public record ProductRequest(
        String name,
        String sku,
        String shortDescription,
        String longDescription,
        List<Long> categoryId,
        List<ProductVariantRequest> variants,
        Map<String, List<ProductImageRequest>> images,
        DiscountType discountType,
        BigDecimal discountValue)
{}
