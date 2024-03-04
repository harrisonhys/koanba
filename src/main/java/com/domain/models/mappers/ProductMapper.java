package com.domain.models.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.domain.models.dtos.ProductDto;
import com.domain.models.entities.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(source = "productId", target = "productId")
    @Mapping(source = "productPrice", target = "productPrice")
    @Mapping(source = "productDescription", target = "productDescription")
    @Mapping(source = "stock", target = "stock")
    ProductDto toDto(Product product);

    @Mapping(source = "productPrice", target = "productPrice")
    @Mapping(source = "productDescription", target = "productDescription")
    @Mapping(source = "stock", target = "stock")
    Product toEntity(ProductDto productDto);
    
} 
