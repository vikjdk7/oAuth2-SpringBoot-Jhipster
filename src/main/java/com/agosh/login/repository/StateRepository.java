package com.agosh.login.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.agosh.login.domain.State;

/**
 * Spring Data MongoDB repository for the State entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StateRepository extends MongoRepository<State, String> {
	
	List<State> findByCountryId(Integer countryId);
	Optional<State> findBySid(Integer sid);
	
	
}
