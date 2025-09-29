package com.example.clothing_stores.Services;

import com.example.clothing_stores.Dto.requests.ProductVariantRequest;
import com.example.clothing_stores.Dto.responses.ProductVariantResponse;
import com.example.clothing_stores.Enums.ProductStatus;
import com.example.clothing_stores.Exception.AppException;
import com.example.clothing_stores.Enums.ErrorCode;
import com.example.clothing_stores.Mapper.ProductImageMapper;
import com.example.clothing_stores.Mapper.ProductVariantMapper;
import com.example.clothing_stores.Models.Product.ProductImage;
import com.example.clothing_stores.Models.Product.ProductVariant;
import com.example.clothing_stores.Repo.ProductRepository;
import com.example.clothing_stores.Repo.ProductVariantRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductVariantService {
    ProductVariantRepository variantRepository;
    ProductRepository productRepository;

    ProductVariantMapper variantMapper;
    ProductImageMapper imageMapper;

    public ProductVariantResponse create(Long id, ProductVariantRequest request) {
        ProductVariant variant = variantMapper.toEntity(request);

        variant.setProduct(productRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.PRODUCT_NOT_EXISTED)));

        return variantMapper.toResponse(variantRepository.save(variant));
    }

    public ProductVariantResponse update(Long id, ProductVariantRequest request) {
        ProductVariant variant = variantRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.PRODUCT_NOT_EXISTED));

        variantMapper.toEntity(variant, request);

        return variantMapper.toResponse(variantRepository.save(variant));
    }

    public ProductVariantResponse changeStatus (Long id, ProductStatus status) {
        ProductVariant variant = variantRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.PRODUCT_NOT_EXISTED));

        variant.setStatus(status);

        return variantMapper.toResponse(variantRepository.save(variant));
    }

    public void delete(Long id) {
        variantRepository.deleteById(id);
    }

    public ProductVariantResponse findVariantById(Long id){
        return variantMapper.toResponse(variantRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXISTED)));
    }

    public List<ProductVariantResponse> findVariantByProductId(Long productId) {
        return variantRepository.findAllByProduct_Id(productId)
                .stream()
                .map(variantMapper::toResponse)
                .toList();
    }
}
