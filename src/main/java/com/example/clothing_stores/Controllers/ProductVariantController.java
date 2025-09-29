package com.example.clothing_stores.Controllers;

import com.example.clothing_stores.Dto.requests.ProductVariantRequest;
import com.example.clothing_stores.Dto.responses.ProductVariantResponse;
import com.example.clothing_stores.Dto.responses.ApiResponse;
import com.example.clothing_stores.Enums.ProductStatus;
import com.example.clothing_stores.Services.ProductVariantService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products/{productId}/variants")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductVariantController {
    ProductVariantService service;

    @PostMapping
    ApiResponse<ProductVariantResponse> createVariant (
            @PathVariable Long productId, @RequestBody ProductVariantRequest request){
        return ApiResponse.<ProductVariantResponse>builder()
                .result(service.create(productId, request))
                .build();
    }

    @PutMapping("/{id}")
    ApiResponse<ProductVariantResponse> updateVariant (
            @PathVariable Long id, @RequestBody ProductVariantRequest request){
        return ApiResponse.<ProductVariantResponse>builder()
                .result(service.update(id, request))
                .build();
    }

    @PutMapping("/{id}/status")
    ApiResponse<ProductVariantResponse> changeStateus (
            @PathVariable Long id, @RequestParam ProductStatus status){
        return ApiResponse.<ProductVariantResponse>builder()
                .result(service.changeStatus(id, status))
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<Void> deleteVariant(@PathVariable Long id){
        service.delete(id);

        return ApiResponse.<Void>builder()
                .message("Product variant has been deleted!")
                .build();
    }

    @GetMapping("/{id}")
    ApiResponse<ProductVariantResponse> getVariantById (@PathVariable Long id){
        return ApiResponse.<ProductVariantResponse>builder()
                .result(service.findVariantById(id))
                .build();
    }

    @GetMapping
    ApiResponse<List<ProductVariantResponse>> getAllVariantByProductId (@RequestParam Long productId){
        return ApiResponse.<List<ProductVariantResponse>>builder()
                .result(service.findVariantByProductId(productId))
                .build();
    }
}
