package com.example.clothing_stores.Services;

import com.example.clothing_stores.Dto.requests.PermissionRequest;
import com.example.clothing_stores.Dto.responses.PermissionResponse;
import com.example.clothing_stores.Exception.AppException;
import com.example.clothing_stores.Enums.ErrorCode;
import com.example.clothing_stores.Mapper.PermissionMapper;
import com.example.clothing_stores.Models.User.Permission;
import com.example.clothing_stores.Repo.PermissionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionService {
    PermissionRepository repository;
    PermissionMapper mapper;
    
    public PermissionResponse create(PermissionRequest request){
        if(repository.existsByName(request.name()))
            throw new AppException(ErrorCode.PERMISSION_EXISTED);

        Permission permission = mapper.toEntity(request);

        return mapper.toResponse(repository.save(permission));
    }
    
    public void deleteById (String id){
        repository.deleteById(id);
    }
    
    public List<PermissionResponse> findAll(){
        return repository.findAll().stream().map(mapper::toResponse).toList();
    }
    
}
