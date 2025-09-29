package com.example.clothing_stores.Services;

import com.example.clothing_stores.Dto.requests.UserCreationRequest;
import com.example.clothing_stores.Dto.requests.UserUpdateRequest;
import com.example.clothing_stores.Dto.responses.UserResponse;
import com.example.clothing_stores.Exception.AppException;
import com.example.clothing_stores.Enums.ErrorCode;
import com.example.clothing_stores.Mapper.UserMapper;
import com.example.clothing_stores.Models.User.AppUser;
import com.example.clothing_stores.Models.User.Role;
import com.example.clothing_stores.Repo.AppUserRepository;
import com.example.clothing_stores.Repo.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    AppUserRepository userRepository;
    UserMapper userMapper;

    RoleRepository roleRepository;
    PasswordEncoder encoder;

    public UserResponse create(UserCreationRequest request) {
        if(userRepository.existsByUsername(request.username()))
            throw new AppException(ErrorCode.USER_EXISTED);

        if(userRepository.existsByEmail(request.email()))
            throw new AppException(ErrorCode.EMAIL_EXISTED);

        if(userRepository.existsByPhone(request.phone()))
            throw new AppException(ErrorCode.PHONE_EXISTED);

        AppUser user = userMapper.toEntity(request);
        user.setPassword(encoder.encode(request.password()));

        Set<Role> roles = new HashSet<>(roleRepository.findAllByNameIn(request.roles()));
        user.setRoles(roles);

        return userMapper.toResponse(userRepository.save(user));
    }

    public UserResponse update(String id, UserUpdateRequest request) {
        AppUser user = userRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED));

        userMapper.toEntity(user, request);
        user.setPassword(encoder.encode(request.password()));

        Set<Role> roles = new HashSet<>(roleRepository.findAllByNameIn(request.roles()));
        user.setRoles(roles);

        return userMapper.toResponse(userRepository.save(user));
    }

    public void deleteById (String id){
        userRepository.deleteById(id);
    }

    public UserResponse findById(String id) {
        return userMapper.toResponse(userRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED)));
    }

    public List<UserResponse> findAll() {
        return userRepository.findAll().stream().map(userMapper::toResponse).toList();
    }
}
