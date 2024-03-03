package com.domain.services;

import com.domain.models.dtos.CustomerDto;

import java.util.List;

public interface CustomerService {
    CustomerDto getCustomer(String param);
    CustomerDto getCustomerById(String customerId);
    CustomerDto addCustomer(CustomerDto customerDto);
    CustomerDto editCustomer(String customerId, CustomerDto customerDto);
    void deleteCustomer(String id);
    List<CustomerDto> getAllCustomers();
}