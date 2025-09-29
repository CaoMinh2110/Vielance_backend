package com.example.clothing_stores.Repo;

import com.example.clothing_stores.Models.User.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    boolean existsByName(String name);

    List<Role> findAllByNameIn(Iterable<String> name);
}
