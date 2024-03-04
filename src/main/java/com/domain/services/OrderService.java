package com.domain.services;

import com.domain.models.dtos.CustomerDto;
import com.domain.models.dtos.OrderDto;
import com.domain.models.dtos.ProductDto;

import java.util.List;

public interface OrderService {
    OrderDto getOrderById(String orderId);
    List<OrderDto> getAllOrders();
    OrderDto createOrder(OrderDto orderDto, ProductDto productDto, CustomerDto customerDto);
    OrderDto updateOrder(OrderDto orderDto);
    void deleteOrder(String orderId);
}