package com.example.clothing_stores.Services;

import com.example.clothing_stores.Dto.requests.CategoryRequest;
import com.example.clothing_stores.Dto.responses.CategoryResponse;
import com.example.clothing_stores.Exception.AppException;
import com.example.clothing_stores.Enums.ErrorCode;
import com.example.clothing_stores.Mapper.CategoryMapper;
import com.example.clothing_stores.Models.Product.Category;
import com.example.clothing_stores.Repo.CategoryRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryService {
    CategoryRepository categoryRepository;

    CategoryMapper mapper;

    public CategoryResponse create(CategoryRequest request) {
        Category category = mapper.toEntity(request);

        if (categoryRepository.existsByName(request.name()))
            throw new AppException(ErrorCode.CATEGORY_EXISTED);

        return mapper.toResponse(categoryRepository.save(category));
    }

    public CategoryResponse update(Long id, CategoryRequest request) {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.CATEGORY_EXISTED));

        if (categoryRepository.existsByName(request.name()) &&
                !request.name().equalsIgnoreCase(category.getName()))
            throw new AppException(ErrorCode.CATEGORY_EXISTED);

        mapper.toEntity(category, request);

        return mapper.toResponse(categoryRepository.save(category));
    }

    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

    public List<CategoryResponse> findAll() {
        return categoryRepository.findAll().stream().map(mapper::toResponse).toList();
    }

    public CategoryResponse findById(Long id) {
        return mapper.toResponse(categoryRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.CATEGORY_EXISTED)));
    }
}
