package com.example.clothing_stores.Mapper;

import com.example.clothing_stores.Dto.requests.AddressRequest;
import com.example.clothing_stores.Dto.responses.AddressResponse;
import com.example.clothing_stores.Models.User.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    Address toEntity (AddressRequest request);

    void toEntity (@MappingTarget Address address, AddressRequest request);

    @Mapping(source = "user.id", target = "userId")
    AddressResponse toResponse (Address address);
}
