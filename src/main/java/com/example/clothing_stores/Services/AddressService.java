package com.example.clothing_stores.Services;

import com.example.clothing_stores.Dto.requests.AddressRequest;
import com.example.clothing_stores.Dto.responses.AddressResponse;
import com.example.clothing_stores.Exception.AppException;
import com.example.clothing_stores.Enums.ErrorCode;
import com.example.clothing_stores.Mapper.AddressMapper;
import com.example.clothing_stores.Models.User.Address;
import com.example.clothing_stores.Repo.AddressRepository;
import com.example.clothing_stores.Repo.AppUserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AddressService {
    AppUserRepository userRepository;
    AddressRepository addressRepository;
    AddressMapper addressMapper;

    public AddressResponse create(String userId, AddressRequest request) {
        Address address = addressMapper.toEntity(request);

        address.setUser(userRepository.findById(userId).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED)));

        return addressMapper.toResponse(addressRepository.save(address));
    }

    public AddressResponse update(Long id, AddressRequest request) {
        Address address = addressRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.ADDRESS_NOT_EXISTED));

        addressMapper.toEntity(address, request);

        return addressMapper.toResponse(addressRepository.save(address));
    }

    public void deleteById (Long id){
        addressRepository.deleteById(id);
    }

    public AddressResponse findById(Long id) {
        return addressMapper.toResponse(addressRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.ADDRESS_NOT_EXISTED)));
    }

    public List<AddressResponse> findByUserId(String userId) {
        return addressRepository.findByUser_Id(userId).stream().map(addressMapper::toResponse).toList();
    }
}
