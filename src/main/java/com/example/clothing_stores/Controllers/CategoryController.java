package com.example.clothing_stores.Controllers;

import com.example.clothing_stores.Dto.requests.CategoryRequest;
import com.example.clothing_stores.Dto.responses.CategoryResponse;
import com.example.clothing_stores.Dto.responses.ApiResponse;
import com.example.clothing_stores.Services.CategoryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryController {
    CategoryService service;

    @PostMapping
    ApiResponse<CategoryResponse> createCategory(@RequestBody CategoryRequest request){
        return ApiResponse.<CategoryResponse>builder()
                .result(service.create(request))
                .build();
    }

    @PutMapping("/{id}")
    ApiResponse<CategoryResponse> updateAdress(@PathVariable Long id, @RequestBody CategoryRequest request){
        return ApiResponse.<CategoryResponse>builder()
                .result(service.update(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<Void> deleteById(@PathVariable Long id){
        service.deleteById(id);
        return ApiResponse.<Void>builder()
                .message("Category has been deleted!")
                .build();
    }

    @GetMapping()
    ApiResponse<List<CategoryResponse>> getAllCategor(){
        return ApiResponse.<List<CategoryResponse>>builder()
                .result(service.findAll())
                .build();
    }

    @GetMapping("/{id}")
    ApiResponse<CategoryResponse> getCategoryById(@PathVariable Long id){
        return ApiResponse.<CategoryResponse>builder()
                .result(service.findById(id))
                .build();
    }
}
