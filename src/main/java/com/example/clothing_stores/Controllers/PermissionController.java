package com.example.clothing_stores.Controllers;

import com.example.clothing_stores.Dto.requests.PermissionRequest;
import com.example.clothing_stores.Dto.responses.ApiResponse;
import com.example.clothing_stores.Dto.responses.PermissionResponse;
import com.example.clothing_stores.Services.PermissionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/permissions")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionController {
    PermissionService service;

    @PostMapping
    ApiResponse<PermissionResponse> createRole(@RequestBody PermissionRequest request){
        return ApiResponse.<PermissionResponse>builder()
                .result(service.create(request))
                .build();
    }


    @DeleteMapping("/{id}")
    ApiResponse<Void> deleteById(@PathVariable String id){
        service.deleteById(id);
        return ApiResponse.<Void>builder()
                .message("Permission has been deleted!")
                .build();
    }

    @GetMapping
    ApiResponse<List<PermissionResponse>> findAll(){
        return ApiResponse.<List<PermissionResponse>>builder()
                .result(service.findAll())
                .build();
    }
}
