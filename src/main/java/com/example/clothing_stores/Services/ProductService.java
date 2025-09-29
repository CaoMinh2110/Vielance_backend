package com.example.clothing_stores.Services;

import com.example.clothing_stores.Dto.requests.ProductRequest;
import com.example.clothing_stores.Dto.responses.ProductResponse;
import com.example.clothing_stores.Enums.DiscountType;
import com.example.clothing_stores.Exception.AppException;
import com.example.clothing_stores.Enums.ErrorCode;
import com.example.clothing_stores.Mapper.ProductImageMapper;
import com.example.clothing_stores.Mapper.ProductMapper;
import com.example.clothing_stores.Mapper.ProductVariantMapper;
import com.example.clothing_stores.Models.Product.Category;
import com.example.clothing_stores.Models.Product.Product;
import com.example.clothing_stores.Models.Product.ProductImage;
import com.example.clothing_stores.Models.Product.ProductVariant;
import com.example.clothing_stores.Repo.CategoryRepository;
import com.example.clothing_stores.Repo.ProductRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductService {
    ProductRepository productRepository;
    CategoryRepository categoryRepository;

    ProductMapper productMapper;
    ProductVariantMapper variantMapper;
    ProductImageMapper imageMapper;

    public ProductResponse create(ProductRequest request) {
        if (productRepository.existsByName(request.name()))
            throw new AppException(ErrorCode.PRODUCT_EXISTED);

        Product product = productMapper.toEntity(request);

        Set<Category> categories = new HashSet<>(categoryRepository.findAllById(request.categoryId()));
        product.setCategories(categories);

        if(!CollectionUtils.isEmpty(request.variants())) {
            AtomicInteger skuCount = new AtomicInteger(1);
            var variants = request.variants().stream()
                    .map(variantReq -> {
                        ProductVariant variant = variantMapper.toEntity(variantReq);
                        variant.setSku(request.sku() + String.format("%04d", skuCount.getAndIncrement()));
                        variant.setProduct(product);
                        return variant;
                    }).toList();

            product.setVariants(variants);
        }

        if (!CollectionUtils.isEmpty(request.images())) {
            var images = request.images().stream()
                    .map(imageReq -> {
                        ProductImage image = imageMapper.toEntity(imageReq);
                        image.setProduct(product);
                        return image;
                    }).toList();

            product.setImages(images);
        }

        return productMapper.toResponse(productRepository.save(product));
    }

    public ProductResponse update(Long id, ProductRequest request) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.PRODUCT_NOT_EXISTED));

        productMapper.toEntity(product, request);
        Set<Category> categories = new HashSet<>(categoryRepository.findAllById(request.categoryId()));
        product.setCategories(categories);

        return productMapper.toResponse(productRepository.save(product));
    }
    
    public void delete (Long id){
        productRepository.deleteById(id);
    }

    public Page<ProductResponse> findAll(int page, int size, String sortBy, String categories, String keyword) {
        String[] parts = sortBy.split(":");
        String field = parts[0];
        String dir = parts[1].toUpperCase();

        Sort sort = Sort.by(Sort.Direction.valueOf(dir), field);
        Pageable pageable = PageRequest.of(page, size, sort);

        List<Long> listCategory = (categories != null && !categories.isEmpty())
                ? Arrays.stream(categories.split(",")).map(Long::parseLong).toList()
                : null;

        Page<Product> products = productRepository.searchProducts(keyword, listCategory, pageable);

        return products.map(productMapper::toResponse);
    }

    public ProductResponse findById(Long id) {
        return productMapper.toResponse(productRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXISTED)));
    }
}
