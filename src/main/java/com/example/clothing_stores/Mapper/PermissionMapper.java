package com.example.clothing_stores.Mapper;

import com.example.clothing_stores.Dto.requests.PermissionRequest;
import com.example.clothing_stores.Dto.responses.PermissionResponse;
import com.example.clothing_stores.Models.User.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toEntity (PermissionRequest request);
    PermissionResponse toResponse (Permission permission);
}
