package com.example.clothing_stores.Dto.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

import java.util.List;

public record UserCreationRequest(
        @Size(min = 3, message = "INVALID_USERNAME") String username,
        @Size(min = 8, message = "INVALID_PASSWORD") String password,
        @Email String email,
        String phone,
        List<String> roles) {}
