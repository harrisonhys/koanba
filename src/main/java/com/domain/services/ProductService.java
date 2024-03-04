package com.domain.services;


import java.util.List;

import com.domain.models.dtos.ProductDto;

public interface ProductService {
    ProductDto getProduct(String param);
    ProductDto getProductById(String productId);
    ProductDto addProduct(ProductDto ProductDto);
    ProductDto editProduct(String productId, ProductDto ProductDto);
    void deleteProduct(String id);
    List<ProductDto> getAllProducts();
    
}
