package com.example.clothing_stores.Repo;

import com.example.clothing_stores.Models.User.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByUser_Id(String userId);
}
