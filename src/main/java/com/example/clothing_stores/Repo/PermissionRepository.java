package com.example.clothing_stores.Repo;

import com.example.clothing_stores.Models.User.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, String> {
    boolean existsByName(String name);

    List<Permission> findAllByNameIn(Iterable<String> name);
}
