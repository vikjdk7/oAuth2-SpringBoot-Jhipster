package com.agosh.login.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.agosh.login.domain.Seller;

/**
 * Spring Data MongoDB repository for the Seller entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SellerRepository extends MongoRepository<Seller, String> {
	
	
	List<Seller> findByLoginId(String loginId);
	Optional<Seller> findByUserName(String email);
	List<Seller> findByPartnerLoginId(String partnerLoginId);
	List<Seller> findByPartnerLoginIdIsNull();
	
	
}
