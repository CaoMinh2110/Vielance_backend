package com.example.clothing_stores.Mapper;

import com.example.clothing_stores.Dto.requests.ProductVariantRequest;
import com.example.clothing_stores.Dto.responses.ProductVariantResponse;
import com.example.clothing_stores.Models.Product.ProductVariant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductVariantMapper {
    ProductVariant toEntity (ProductVariantRequest request);

    void toEntity (@MappingTarget ProductVariant variant, ProductVariantRequest request);

    @Mapping(target = "productId", source = "product.id")
    ProductVariantResponse toResponse (ProductVariant variant);
}
