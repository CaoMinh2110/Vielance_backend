package com.example.clothing_stores.Dto.responses;

import com.example.clothing_stores.Enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderResponse {
    Long id;
    String userId;
    String code;
    OrderStatus status;
    BigDecimal subtotalAmount;
    BigDecimal discountAmount;
    BigDecimal shippingFee;
    BigDecimal totalAmount;
    String currency;
    String shippingAddress;
    String billingAddress;
    String note;
    Instant createdAt;
    Instant updatedAt;
    List<OrderItemResponse> items;
}
