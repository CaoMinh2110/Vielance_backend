package com.example.clothing_stores.Dto.responses;

import com.example.clothing_stores.Models.User.Role;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse{
    String id;
    String username;
    String email;
    String phone;
    String fullName;
    boolean status = true;
    List<AddressResponse> addresses;
    Set<Role> roles;
}
