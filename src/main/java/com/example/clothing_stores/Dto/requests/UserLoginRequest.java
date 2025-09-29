package com.example.clothing_stores.Dto.requests;

import jakarta.validation.constraints.Size;

public record UserLoginRequest (
        String account,
        @Size(min = 8, message = "INVALID_PASSWORD") String password
){}
