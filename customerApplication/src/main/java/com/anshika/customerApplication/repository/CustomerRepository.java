package com.anshika.customerApplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anshika.customerApplication.models.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
