package com.agosh.login.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.agosh.login.domain.Country;

/**
 * Spring Data MongoDB repository for the Country entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CountryRepository extends MongoRepository<Country, String> {
	Optional<Country> findByCid(Integer cid);
	
	
}