package com.example.clothing_stores.Dto.responses;

import com.example.clothing_stores.Enums.ProductStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductVariantResponse {
    Long id;
    String sku;
    String size;
    String color;
    ProductStatus status;
    BigDecimal price;
    int stock;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    String productId;
}
