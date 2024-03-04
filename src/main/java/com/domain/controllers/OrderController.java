package com.domain.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.domain.helper.ErrorMessage;
import com.domain.helper.SuccessMessage;
import com.domain.models.dtos.CustomerDto;
import com.domain.models.dtos.OrderDto;
import com.domain.models.dtos.ProductDto;
import com.domain.services.CustomerService;
import com.domain.services.OrderService;
import com.domain.services.ProductService;


@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private final OrderService orderService;
    @Autowired
    private final ProductService productService;
    @Autowired
    private final CustomerService customerService;

    public OrderController(OrderService orderService, ProductService productService, CustomerService customerService) {
        this.orderService = orderService;
        this.productService = productService;
        this.customerService = customerService;
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable String orderId) {
        OrderDto orderDto = orderService.getOrderById(orderId);
        if (orderDto != null) {
            return ResponseEntity.ok(orderDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        List<OrderDto> orderDtos = orderService.getAllOrders();
        return ResponseEntity.ok(orderDtos);
    }

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderDto orderDto) {

        ProductDto productDto = productService.getProductById(orderDto.getProductId());

        if (productDto == null) {
            ErrorMessage errorProduct = new ErrorMessage("Product not found");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorProduct); 
        }

        CustomerDto customerDto = customerService.getCustomerById(orderDto.getCustomerId());

        if (customerDto == null) {
            ErrorMessage errorCustomer = new ErrorMessage("Customer not found");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorCustomer); 
        }

        if(productDto.getStock() < orderDto.getQuantity()) {
            ErrorMessage emptyStock = new ErrorMessage("Empty Stock");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(emptyStock); 
        }

        try {
            OrderDto createdOrderDto = orderService.createOrder(orderDto, productDto, customerDto);
            if(createdOrderDto != null) {
                SuccessMessage successOrderMsg = new SuccessMessage("Successfully order!");
                return ResponseEntity.status(HttpStatus.CREATED).body(successOrderMsg);
            }else{
                ErrorMessage orderFailedMsg = new ErrorMessage("Order Failed");
                return ResponseEntity.status(HttpStatus.CREATED).body(orderFailedMsg);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CREATED).body(e.getMessage());
        }

        
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable String orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.noContent().build();
    }


}
