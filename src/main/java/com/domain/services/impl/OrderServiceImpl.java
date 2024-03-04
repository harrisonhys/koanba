package com.domain.services.impl;

import com.domain.models.dtos.CustomerDto;
import com.domain.models.dtos.OrderDto;
import com.domain.models.dtos.ProductDto;
import com.domain.models.entities.Order;
import com.domain.models.entities.Product;
import com.domain.models.mappers.OrderMapper;
import com.domain.models.mappers.ProductMapper;
import com.domain.repositories.OrderRepository;
import com.domain.repositories.ProductRepository;
import com.domain.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private final OrderRepository orderRepository;
    @Autowired
    private final OrderMapper orderMapper;
    @Autowired
    private final ProductMapper productMapper;
    @Autowired
    private final ProductRepository productRepository;
    

    public OrderServiceImpl(OrderRepository orderRepository, OrderMapper orderMapper, ProductRepository productRepository, ProductMapper productMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public OrderDto getOrderById(String orderId) {
        Order order = orderRepository.findById(orderId).orElse(null);
        return orderMapper.toDto(order);
    }

    @Override
    public List<OrderDto> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public OrderDto createOrder(OrderDto orderDto, ProductDto productDto, CustomerDto customerDto) {
        Order order         = orderMapper.toEntity(orderDto);
        Integer quantity    = order.getQuantity();
        BigDecimal price    = new BigDecimal(productDto.getProductPrice());
        BigDecimal amount   = price.multiply(BigDecimal.valueOf(quantity));
        order.setCustomerName(customerDto.getCustomerName());
        order.setAmount(amount);

        Product product         = productMapper.toEntity(productDto);
        Integer currentStock    = product.getStock();
        Integer finalStock      = currentStock-quantity;
        product.setStock(finalStock);
        productRepository.save(product);
        Order savedOrder = orderRepository.save(order);
        return orderMapper.toDto(savedOrder);
    }

    @Override
    public OrderDto updateOrder(OrderDto orderDTO) {
        Order order = orderMapper.toEntity(orderDTO);
        Order savedOrder = orderRepository.save(order);
        return orderMapper.toDto(savedOrder);
    }

    @Override
    public void deleteOrder(String orderId) {
        orderRepository.deleteById(orderId);
    }

}