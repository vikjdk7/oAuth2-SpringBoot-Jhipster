package com.agosh.login.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.agosh.login.domain.UserAddress;

/**
 * Spring Data MongoDB repository for the UserAddress entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserAddressRepository extends MongoRepository<UserAddress, String> {
	List<UserAddress> findByLoginUserId(String userId);
	List<UserAddress> findByLoginId(String userId);
	
	
}
