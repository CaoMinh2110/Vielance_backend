package com.example.clothing_stores.Mapper;

import com.example.clothing_stores.Dto.requests.ProductImageRequest;
import com.example.clothing_stores.Dto.responses.ProductImageResponse;
import com.example.clothing_stores.Models.Product.ProductImage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductImageMapper {
    ProductImage toEntity (ProductImageRequest request);

    void toEntity (@MappingTarget ProductImage image, ProductImageResponse response);

    @Mapping(source = "product.id", target = "productId")
    ProductImageResponse toResponse (ProductImage Image);
}
