package com.agosh.login.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.agosh.login.domain.UserContact;

/**
 * Spring Data MongoDB repository for the UserContact entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserContactRepository extends MongoRepository<UserContact, String> {
	List<UserContact> findByLoginUserEmail(String loginUserEmail);
}
