package com.example.clothing_stores.Mapper;

import com.example.clothing_stores.Dto.requests.CategoryRequest;
import com.example.clothing_stores.Dto.responses.CategoryResponse;
import com.example.clothing_stores.Models.Product.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toEntity (CategoryRequest request);

    void toEntity (@MappingTarget Category category, CategoryRequest request);

    CategoryResponse toResponse (Category category);
}
