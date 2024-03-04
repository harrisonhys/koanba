package com.domain.services.impl;

import com.domain.helper.ErrorMessage;
import com.domain.helper.NotFoundException;
import com.domain.models.dtos.CustomerDto;
import com.domain.models.dtos.OrderDto;
import com.domain.models.dtos.ProductDto;
import com.domain.models.entities.Order;
import com.domain.models.entities.Product;
import com.domain.models.mappers.OrderMapper;
import com.domain.models.mappers.ProductMapper;
import com.domain.repositories.OrderRepository;
import com.domain.repositories.ProductRepository;
import com.domain.services.CustomerService;
import com.domain.services.OrderService;
import com.domain.services.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private CustomerService customerService;

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
    public OrderDto createOrder(OrderDto orderDto) {
        ProductDto productDto = productService.getProductById(orderDto.getProductId());

        if (productDto == null) {
            throw new NotFoundException("Product not found");
        }

        CustomerDto customerDto = customerService.getCustomerById(orderDto.getCustomerId());

        if (customerDto == null) {
            throw new NotFoundException("Customer not found");
        }

        if(productDto.getStock() < orderDto.getQuantity()) {
            throw new NotFoundException("Empty Stock");
        }

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