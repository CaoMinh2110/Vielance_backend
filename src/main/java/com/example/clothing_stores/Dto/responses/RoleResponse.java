package com.example.clothing_stores.Dto.responses;

import com.example.clothing_stores.Models.User.Permission;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)

public class RoleResponse {
    String id;
    String name;
    String description;
    List<Permission> permissions;
}
