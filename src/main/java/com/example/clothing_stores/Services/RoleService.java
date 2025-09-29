package com.example.clothing_stores.Services;

import com.example.clothing_stores.Dto.requests.RoleRequest;
import com.example.clothing_stores.Dto.responses.RoleResponse;
import com.example.clothing_stores.Exception.AppException;
import com.example.clothing_stores.Enums.ErrorCode;
import com.example.clothing_stores.Mapper.RoleMapper;
import com.example.clothing_stores.Models.User.Permission;
import com.example.clothing_stores.Models.User.Role;
import com.example.clothing_stores.Repo.PermissionRepository;
import com.example.clothing_stores.Repo.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleService {
    RoleRepository roleRepository;
    RoleMapper roleMapper;

    PermissionRepository permissionRepository;

    public RoleResponse create(RoleRequest request){
        if(roleRepository.existsByName(request.name()))
            throw new AppException(ErrorCode.ROLE_EXISTED);

        Role role = roleMapper.toEntity(request);

        Set<Permission> permissions = new HashSet<>(permissionRepository.findAllByNameIn(request.permissions()));
        role.setPermissions(permissions);

        return roleMapper.toResponse(roleRepository.save(role));
    }

    public void deleteById (String id){
        roleRepository.deleteById(id);
    }

    public List<RoleResponse> findAll(){
        return roleRepository.findAll().stream().map(roleMapper::toResponse).toList();
    }

}
