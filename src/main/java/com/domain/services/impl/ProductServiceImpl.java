package com.domain.services.impl;

import com.domain.models.dtos.ProductDto;
import com.domain.models.entities.Product;
import com.domain.models.mappers.ProductMapper;
import com.domain.repositories.ProductRepository;
import com.domain.services.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductMapper productMapper;

    @Override
    public ProductDto getProduct(String param) {
        Optional<Product> product = productRepository.findByProductId(param);
        return product.map(productMapper::toDto).orElse(null);
    }

    @Override
    public ProductDto getProductById(String id) {
        Optional<Product> product = productRepository.findByProductId(id);
        return product.map(productMapper::toDto).orElse(null);
    }

    @Override
    public ProductDto addProduct(ProductDto productDto) {
        Product product = productMapper.toEntity(productDto);
        return productMapper.toDto(productRepository.save(product));
    }

    @Override
    public ProductDto editProduct(String id, ProductDto productDto) {
        Optional<Product> existingProduct = productRepository.findByProductId(id);
        if (existingProduct.isPresent()) {
            Product product = existingProduct.get();
            product.setProductPrice(productDto.getProductPrice());
            product.setProductDescription(productDto.getProductDescription());
            product.setStock(productDto.getStock());
            return productMapper.toDto(productRepository.save(product));
        }
        return null;
    }

    @Override
    public void deleteProduct(String id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(productMapper::toDto).collect(Collectors.toList());
    }
}