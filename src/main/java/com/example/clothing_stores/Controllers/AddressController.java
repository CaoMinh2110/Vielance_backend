package com.example.clothing_stores.Controllers;

import com.example.clothing_stores.Dto.requests.AddressRequest;
import com.example.clothing_stores.Dto.responses.AddressResponse;
import com.example.clothing_stores.Dto.responses.ApiResponse;
import com.example.clothing_stores.Services.AddressService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/{userId}/address")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AddressController {
    AddressService service;

    @PostMapping
    ApiResponse<AddressResponse> createAddress(@PathVariable String userId, @RequestBody AddressRequest request){
        return ApiResponse.<AddressResponse>builder()
                .result(service.create(userId, request))
                .build();
    }

    @PutMapping("/{addressId}")
    ApiResponse<AddressResponse> updateAdress(@PathVariable Long addressId, @RequestBody AddressRequest request){
        return ApiResponse.<AddressResponse>builder()
                .result(service.update(addressId, request))
                .build();
    }

    @DeleteMapping("/{addressId}")
    ApiResponse<Void> deleteById(@PathVariable Long addressId){
        service.deleteById(addressId);
        return ApiResponse.<Void>builder()
                .message("User address has been deleted!")
                .build();
    }

    @GetMapping("/{addressId}")
    ApiResponse<AddressResponse> findAddressById(@PathVariable Long addressId){
        return ApiResponse.<AddressResponse>builder()
                .result(service.findById(addressId))
                .build();
    }

    @GetMapping()
    ApiResponse<List<AddressResponse>> findAddressByUserId(@PathVariable String userId){
        return ApiResponse.<List<AddressResponse>>builder()
                .result(service.findByUserId(userId))
                .build();
    }
}
