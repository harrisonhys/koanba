package com.domain.models.mappers;

import com.domain.models.dtos.CustomerDto;
import com.domain.models.entities.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);
    @Mapping(source = "customerId", target = "customerId")
    @Mapping(source = "customerName", target = "customerName")
    @Mapping(source = "address", target = "address")
    @Mapping(source = "phone", target = "phone")
    CustomerDto toDto(Customer customer);

    @Mapping(source = "customerName", target = "customerName")
    @Mapping(source = "address", target = "address")
    @Mapping(source = "phone", target = "phone")
    Customer toEntity(CustomerDto customerDto);
}