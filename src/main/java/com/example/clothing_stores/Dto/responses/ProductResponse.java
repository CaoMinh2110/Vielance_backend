package com.example.clothing_stores.Dto.responses;

import com.example.clothing_stores.Enums.DiscountType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductResponse {
    Long id;
    String name;
    String shortDescription;
    String longDescription;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    List<Long> categories;
    DiscountType discountType;
    List<ProductVariantResponse> variants = new ArrayList<>();
    List<ProductImageResponse> images = new ArrayList<>();
}
