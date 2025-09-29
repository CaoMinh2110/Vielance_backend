package com.example.clothing_stores.Controllers;

import com.example.clothing_stores.Dto.requests.RoleRequest;
import com.example.clothing_stores.Dto.responses.ApiResponse;
import com.example.clothing_stores.Dto.responses.RoleResponse;
import com.example.clothing_stores.Services.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/roles")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleController {
    RoleService service;

    @PostMapping
    ApiResponse<RoleResponse> createRole(@RequestBody RoleRequest request){
        return ApiResponse.<RoleResponse>builder()
                .result(service.create(request))
                .build();
    }


    @DeleteMapping("/{id}")
    ApiResponse<Void> deleteById(@PathVariable String id){
        service.deleteById(id);
        return ApiResponse.<Void>builder()
                .message("Roles has been deleted!")
                .build();
    }


    @GetMapping
    ApiResponse<List<RoleResponse>> findAll(){
        return ApiResponse.<List<RoleResponse>>builder()
                .result(service.findAll())
                .build();
    }
}
