package com.domain.models.dtos;

import lombok.Data;

@Data
public class ProductDto {
    private String productId;
    private String productPrice;
    private String productDescription;
    private Integer stock;
}
