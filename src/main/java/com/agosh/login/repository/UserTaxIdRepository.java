package com.agosh.login.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.agosh.login.domain.UserTaxId;

/**
 * Spring Data MongoDB repository for the City entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserTaxIdRepository extends MongoRepository<UserTaxId, String> {

	List<UserTaxId> findByLoginId(String email);

}
