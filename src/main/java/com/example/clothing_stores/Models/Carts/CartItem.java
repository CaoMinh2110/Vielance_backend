package com.example.clothing_stores.Models.Carts;

import com.example.clothing_stores.Models.Product.ProductVariant;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "cart_item",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_cart_variant", columnNames = {"cart_id","product_variant_id"})
        })
public class CartItem {
    @Id
    @UuidGenerator
    String id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cart_id")
    Cart cart;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_variant_id")
    ProductVariant productVariant;

    @Column(nullable = false)
    int quantity;

    // Chụp giá tại thời điểm thêm vào giỏ (tránh đổi giá làm sai đơn)
    @Column(nullable = false, precision = 12, scale = 2)
    BigDecimal priceAtTime;

    @CreationTimestamp
    LocalDateTime createdAt;
}
