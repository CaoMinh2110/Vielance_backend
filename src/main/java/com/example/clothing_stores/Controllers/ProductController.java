package com.example.clothing_stores.Controllers;

import com.example.clothing_stores.Dto.requests.ProductRequest;
import com.example.clothing_stores.Dto.responses.ApiResponse;
import com.example.clothing_stores.Dto.responses.ProductResponse;
import com.example.clothing_stores.Services.ProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductController {
    ProductService service;

    @PostMapping
    ApiResponse<ProductResponse> createProduct(@RequestBody ProductRequest request){
        return ApiResponse.<ProductResponse>builder()
                .result(service.create(request))
                .build();
    }

    @PutMapping("/{id}")
    ApiResponse<ProductResponse> updateProduct(@PathVariable Long id, @RequestBody ProductRequest request){
        return ApiResponse.<ProductResponse>builder()
                .result(service.update(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<Void> deleteProduct(@PathVariable Long id){
        service.delete(id);

        return ApiResponse.<Void>builder()
                .message("Product has been deleted!")
                .build();
    }

    @GetMapping
    ApiResponse<Page<ProductResponse>> getAllProduct(@RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "10") int size,
                                                     @RequestParam String sortBy,
                                                     @RequestParam(required = false) String categories,
                                                     @RequestParam(required = false) String keyword
    ){
        return ApiResponse.<Page<ProductResponse>>builder()
                .result(service.findAll(page, size, sortBy, categories, keyword))
                .build();
    }

    @GetMapping("/{id}")
    ApiResponse<ProductResponse> getProductById(@PathVariable Long id) {
        return ApiResponse.<ProductResponse>builder()
                .result(service.findById(id))
                .build();
    }
}
