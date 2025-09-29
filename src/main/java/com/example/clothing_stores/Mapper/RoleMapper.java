package com.example.clothing_stores.Mapper;

import com.example.clothing_stores.Dto.requests.RoleRequest;
import com.example.clothing_stores.Dto.responses.RoleResponse;
import com.example.clothing_stores.Models.User.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toEntity (RoleRequest request);
    RoleResponse toResponse (Role role);
}
