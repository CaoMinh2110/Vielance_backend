package com.example.clothing_stores.Mapper;

import com.example.clothing_stores.Dto.requests.UserCreationRequest;
import com.example.clothing_stores.Dto.requests.UserUpdateRequest;
import com.example.clothing_stores.Dto.responses.UserResponse;
import com.example.clothing_stores.Models.User.AppUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "roles", ignore = true)
    AppUser toEntity (UserCreationRequest request);

    @Mapping(target = "roles", ignore = true)
    void toEntity (@MappingTarget AppUser user, UserUpdateRequest request);

    UserResponse toResponse (AppUser user);
}
