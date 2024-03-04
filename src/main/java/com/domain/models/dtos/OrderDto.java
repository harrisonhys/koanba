package com.domain.models.dtos;


import lombok.Data;

@Data
public class OrderDto {
    private String orderId;
    private String customerId;
    private String customerName;
    private String productId;
    private String productDescription;
    private int quantity;
    private int amount;

    // private CustomerDto customer;
    // private ProductDto product;


}
