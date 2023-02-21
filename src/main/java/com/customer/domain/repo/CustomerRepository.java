package com.customer.domain.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.customer.domain.Customer;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {
}
