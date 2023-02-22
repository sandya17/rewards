package com.customer.data.domain.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.customer.data.domain.Transactions;

@Repository
public interface TransactionsRepository extends CrudRepository<Transactions, Long> {
	
}
