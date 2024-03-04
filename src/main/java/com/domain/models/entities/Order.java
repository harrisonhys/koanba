package com.domain.models.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="`order`")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String orderId;
    @ManyToOne
    @JoinColumn(name="customer_id")
    private Customer customerId;
    private String customerName;
    private BigDecimal amount;
    private Integer quantity;
    @ManyToOne
    @JoinColumn(name="product_id")
    private Product productId;
    private LocalDateTime orderDate; 
    
    @PrePersist
    public void prePersist() {
        this.orderDate = LocalDateTime.now();
    }
}