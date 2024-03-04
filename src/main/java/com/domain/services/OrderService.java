package com.domain.services;

import com.domain.models.dtos.OrderDto;
import java.util.List;

public interface OrderService {
    OrderDto getOrderById(String orderId);
    List<OrderDto> getAllOrders();
    OrderDto createOrder(OrderDto orderDto);
    OrderDto updateOrder(OrderDto orderDto);
    void deleteOrder(String orderId);
}