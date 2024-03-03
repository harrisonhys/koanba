package com.domain.repositories;

import com.domain.models.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
    Optional<Customer> findByCustomerName(String customerName);
    Optional<Customer> findByCustomerId(String customerId);

}