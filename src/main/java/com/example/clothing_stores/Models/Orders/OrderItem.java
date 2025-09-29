package com.example.clothing_stores.Models.Orders;

import com.example.clothing_stores.Models.Product.Product;
import com.example.clothing_stores.Models.Product.ProductVariant;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "order_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_orderitem_order"))
    Order order;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_orderitem_product"))
    Product product;

    @ManyToOne(optional = false)
    @JoinColumn(name = "variant_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_orderitem_variant"))
    ProductVariant variant;

    @Column(nullable = false)
    String sku;

    @Column(nullable = false)
    String name;

    @Column(columnDefinition = "json", nullable = false)
    String attrs; // {"size":"M","color":"Black"}

    @Column(name = "unit_price", precision = 12, scale = 2, nullable = false)
    BigDecimal unitPrice;

    @Column(nullable = false)
    Integer quantity;

    @Column(name = "line_total", precision = 12, scale = 2, nullable = false)
    BigDecimal lineTotal;
}
