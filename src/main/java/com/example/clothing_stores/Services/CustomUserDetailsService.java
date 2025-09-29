package com.example.clothing_stores.Services;

import com.example.clothing_stores.Exception.AppException;
import com.example.clothing_stores.Enums.ErrorCode;
import com.example.clothing_stores.Repo.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final AppUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String keyword) throws UsernameNotFoundException {
        return userRepository.findByAny(keyword)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
    }
}

