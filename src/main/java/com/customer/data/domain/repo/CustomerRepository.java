package com.customer.data.domain.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.customer.data.domain.Customer;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {
}
