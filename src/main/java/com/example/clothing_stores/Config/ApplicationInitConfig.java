package com.example.clothing_stores.Config;

import com.example.clothing_stores.Models.User.AppUser;
import com.example.clothing_stores.Models.User.Role;
import com.example.clothing_stores.Repo.AppUserRepository;
import com.example.clothing_stores.Repo.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ApplicationInitConfig {
    PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner applicationRunner (AppUserRepository userRepository, RoleRepository roleRepository){
        return args -> {
            if(!userRepository.existsByUsername("admin")) {
                roleRepository.save(Role.builder()
                        .name("CUSTOMER")
                        .description("Customer_role")
                        .build());

                roleRepository.save(Role.builder()
                        .name("SELLER")
                        .description("Seller_role")
                        .build());

                Role Admin_role = roleRepository.save(Role.builder()
                        .name("ADMIN")
                        .description("Admin_role")
                        .build());

                HashSet<Role> roles = new HashSet<>();
                roles.add(Admin_role);

                userRepository.save(AppUser.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("12345678"))
                        .roles(roles)
                        .build());
            }
        };
    }
}
