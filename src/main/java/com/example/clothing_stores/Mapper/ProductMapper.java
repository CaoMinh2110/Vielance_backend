package com.example.clothing_stores.Mapper;

import com.example.clothing_stores.Dto.requests.ProductRequest;
import com.example.clothing_stores.Dto.responses.ProductResponse;
import com.example.clothing_stores.Models.Product.Category;
import com.example.clothing_stores.Models.Product.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target = "variants", ignore = true)
    @Mapping(target = "images", ignore = true)
    Product toEntity (ProductRequest request);

    @Mapping(target = "variants", ignore = true)
    @Mapping(target = "images", ignore = true)
    void toEntity (@MappingTarget Product product, ProductRequest request);

    @Mapping(target = "categories", source = "categories")
    ProductResponse toResponse (Product product);

    default List<Long> mapCategories(Set<Category> categories) {
        if (categories == null) return Collections.emptyList();
        return categories.stream()
                .map(Category::getId)
                .collect(Collectors.toList());
    }
}
