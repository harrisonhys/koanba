package com.domain.services.impl;

import com.domain.models.dtos.CustomerDto;
import com.domain.models.entities.Customer;
import com.domain.models.mappers.CustomerMapper;
import com.domain.repositories.CustomerRepository;
import com.domain.services.CustomerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerMapper customerMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @Override
    public CustomerDto getCustomer(String param) {
        Optional<Customer> customer = customerRepository.findByCustomerName(param);
        return customer.map(customerMapper::toDto).orElse(null);
    }

    @Override
    public CustomerDto getCustomerById(String id) {
        Optional<Customer> customer = customerRepository.findByCustomerId(id);
        return customer.map(customerMapper::toDto).orElse(null);
    }

    @Override
    public CustomerDto addCustomer(CustomerDto customerDto) {
        Customer customer = customerMapper.toEntity(customerDto);
        return customerMapper.toDto(customerRepository.save(customer));
    }

    @Override
    public CustomerDto editCustomer(String id, CustomerDto customerDto) {
        Optional<Customer> existingCustomer = customerRepository.findByCustomerId(id);
        if (existingCustomer.isPresent()) {
            Customer customer = existingCustomer.get();
            customer.setCustomerName(customerDto.getCustomerName());
            customer.setAddress(customerDto.getAddress());
            customer.setPhone(customerDto.getPhone());
            return customerMapper.toDto(customerRepository.save(customer));
        }
        return null;
    }

    @Override
    public void deleteCustomer(String id) {
        customerRepository.deleteById(id);
    }

    @Override
    public List<CustomerDto> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream().map(customerMapper::toDto).collect(Collectors.toList());
    }
}