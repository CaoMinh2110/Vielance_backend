package com.example.clothing_stores.Dto.requests;

public record AddressRequest(String receiverName, String phone, String line,
                             String city, String state) {
}
