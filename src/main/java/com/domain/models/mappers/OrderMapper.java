package com.domain.models.mappers;

import com.domain.models.dtos.OrderDto;
import com.domain.models.entities.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(source = "orderId", target = "orderId")
    @Mapping(source = "customerId.customerId", target = "customerId")
    @Mapping(source = "customerName", target = "customerName")
    @Mapping(source = "productId.productId", target = "productId")
    @Mapping(source = "productId.productDescription", target = "productDescription")
    @Mapping(source = "amount", target = "amount")
    OrderDto toDto(Order order);

    @Mapping(source = "customerId", target = "customerId.customerId")
    @Mapping(source = "customerName", target = "customerId.customerName")
    @Mapping(source = "productId", target = "productId.productId")
    Order toEntity(OrderDto orderDTO);
}
