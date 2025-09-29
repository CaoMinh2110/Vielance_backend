package com.example.clothing_stores.Controllers;

import com.example.clothing_stores.Dto.requests.UserCreationRequest;
import com.example.clothing_stores.Dto.requests.UserUpdateRequest;
import com.example.clothing_stores.Dto.responses.ApiResponse;
import com.example.clothing_stores.Dto.responses.UserResponse;
import com.example.clothing_stores.Services.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService service;

    @PostMapping
    ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request){
        return ApiResponse.<UserResponse>builder()
                .result(service.create(request))
                .build();
    }

    @PutMapping("/{id}")
    ApiResponse<UserResponse> updateUser(@PathVariable String id, @RequestBody @Valid UserUpdateRequest request){
        return ApiResponse.<UserResponse>builder()
                .result(service.update(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<Void> deleteById(@PathVariable String id){
        service.deleteById(id);
        return ApiResponse.<Void>builder()
                .message("User has been deleted!")
                .build();
    }

    @GetMapping("/{id}")
    ApiResponse<UserResponse> findById(@PathVariable String id){
        return ApiResponse.<UserResponse>builder()
                .result(service.findById(id))
                .build();
    }

    @GetMapping()
    ApiResponse<List<UserResponse>> findAll(){
        return ApiResponse.<List<UserResponse>>builder()
                .result(service.findAll())
                .build();
    }
}
