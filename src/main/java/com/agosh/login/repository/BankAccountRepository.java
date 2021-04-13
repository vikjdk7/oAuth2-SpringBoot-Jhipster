package com.agosh.login.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.agosh.login.domain.BankAccount;

/**
 * Spring Data MongoDB repository for the BankAccount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BankAccountRepository extends MongoRepository<BankAccount, String> {
	
	Optional<BankAccount> findByUserEmail(String email);
	
}
