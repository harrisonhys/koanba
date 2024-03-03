package com.domain.models.dtos;

import lombok.Data;

@Data
public class CustomerDto {
    private String customerId;
    private String customerName;
    private String address;
    private Integer phone;
}