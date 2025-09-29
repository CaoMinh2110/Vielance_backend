package com.example.clothing_stores.Repo;

import com.example.clothing_stores.Models.User.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, String> {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);

    Optional<AppUser> findByUsername(String email);

    @Query("SELECT u FROM AppUser u " +
            "WHERE u.email = :value OR u.username = :value OR u.phone = :value")
    Optional<AppUser> findByAny(@Param("value") String value);

}
