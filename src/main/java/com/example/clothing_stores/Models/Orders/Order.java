package com.example.clothing_stores.Models.Orders;

import com.example.clothing_stores.Enums.OrderStatus;
import com.example.clothing_stores.Models.User.AppUser;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "\"order\"")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_order_user"))
    AppUser user;

    @Column(nullable = false, unique = true)
    String code;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    OrderStatus status = OrderStatus.PENDING;

    @Column(name = "subtotal_amount", precision = 12, scale = 2, nullable = false)
    BigDecimal subtotalAmount;

    @Builder.Default
    @Column(name = "discount_amount", precision = 12, scale = 2, nullable = false)
    BigDecimal discountAmount = BigDecimal.ZERO;

    @Builder.Default
    @Column(name = "shipping_fee", precision = 12, scale = 2, nullable = false)
    BigDecimal shippingFee = BigDecimal.ZERO;

    @Column(name = "total_amount", precision = 12, scale = 2, nullable = false)
    BigDecimal totalAmount;

    @Builder.Default
    @Column(length = 3, nullable = false)
    String currency = "VND";

    @Column(name = "shipping_address", columnDefinition = "json", nullable = false)
    String shippingAddress; // lưu JSON -> String

    @Column(name = "billing_address", columnDefinition = "json")
    String billingAddress;

    @Column(columnDefinition = "text")
    String note;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    Instant createdAt;

    @UpdateTimestamp
    Instant updatedAt;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    List<OrderItem> items;
}
