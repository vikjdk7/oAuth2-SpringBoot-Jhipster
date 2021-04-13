package com.agosh.login.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.agosh.login.domain.Partner;

/**
 * Spring Data MongoDB repository for the Partner entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PartnerRepository extends MongoRepository<Partner, String> {
	
	Optional<Partner> findByLoginId(String loginId);
	
	Optional<Partner> findByStateId(int stateId);
	
	List<Partner> findByCountryId(int countryId);

}
