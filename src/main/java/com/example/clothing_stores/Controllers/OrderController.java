package com.example.clothing_stores.Controllers;

import com.example.clothing_stores.Dto.requests.OrderCreationRequest;
import com.example.clothing_stores.Dto.requests.OrderUpdateRequest;
import com.example.clothing_stores.Dto.responses.OrderResponse;
import com.example.clothing_stores.Enums.OrderStatus;
import com.example.clothing_stores.Services.OrderService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderController {
    OrderService service;

    @PostMapping
    ResponseEntity<OrderResponse> createOrder (@RequestBody OrderCreationRequest request,
                                               Authentication authentication) {
        String userId = null;
        if (authentication != null && authentication.isAuthenticated()) {
            userId = authentication.getName();
        }

        return ResponseEntity.ok(service.create(request, userId));
    }

    @PutMapping("/{orderId}")
    ResponseEntity<OrderResponse> updateOrder (@PathVariable Long orderId, @RequestBody OrderUpdateRequest request) {
        return ResponseEntity.ok(service.update(orderId, request));
    }

    @PutMapping("/{orderId}/status")
    ResponseEntity<OrderResponse> changeStatus (@PathVariable Long orderId, @RequestParam OrderStatus status) {
        return ResponseEntity.ok(service.changeStatus(orderId, status));
    }

    @DeleteMapping("/{orderId}")
    void deletedOrderById (@RequestParam Long orderId) {
        service.deleteById(orderId);
    }

    @GetMapping
    ResponseEntity<Page<OrderResponse>> getAllOrder (@RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return ResponseEntity.ok(service.findAll(pageable));
    }

    @PutMapping("/{code}")
    ResponseEntity<OrderResponse> getOrderByCode (@PathVariable String code) {
        return ResponseEntity.ok(service.findByCode(code));
    }
}
