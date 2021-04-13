package com.agosh.login.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.agosh.login.domain.City;

/**
 * Spring Data MongoDB repository for the City entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CityRepository extends MongoRepository<City, String> {

	List<City> findByStateId(Integer stateId);
	Optional<City> findByCtid(Integer ctid);
	
}
