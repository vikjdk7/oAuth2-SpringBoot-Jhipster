package com.agosh.login.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.agosh.login.domain.Customer;

/**
 * Spring Data MongoDB repository for the Customer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomerRepository extends MongoRepository<Customer, String> {

	List<Customer> findByLoginId(String id);
}
