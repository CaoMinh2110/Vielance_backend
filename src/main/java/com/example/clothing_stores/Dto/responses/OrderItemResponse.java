package com.example.clothing_stores.Dto.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderItemResponse {
    Long id;
    String orderId;
    String productId;
    String variantId;
    String sku;
    String name;
    String attrs;
    BigDecimal unitPrice;
    Integer quantity;
    BigDecimal lineTotal;
}
