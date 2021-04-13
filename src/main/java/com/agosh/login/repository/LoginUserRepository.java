package com.agosh.login.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.agosh.login.domain.LoginUser;
import com.agosh.login.domain.enumeration.UserStatus;
import com.agosh.login.domain.enumeration.UserType;

/**
 * Spring Data MongoDB repository for the LoginUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LoginUserRepository extends MongoRepository<LoginUser, String> {

	Optional<LoginUser> findByEmail(String email);

	Optional<LoginUser> findByEmailAndUserStatus(String email, UserStatus userStatus);

	boolean existsByEmail(String email);

	Page<LoginUser> findByUserStatus(UserStatus userStatus, Pageable pageable);

	Page<LoginUser> findByUserStatusAndEmailOrFirstNameOrLastName(UserStatus userStatus, Pageable pageable,
			String search1, String search2, String search3);

	Page<LoginUser> findByUserStatusAndEmailLikeOrFirstNameLikeOrLastNameLike(UserStatus userStatus, Pageable pageable,
			String search1, String search2, String search3);

	Page<LoginUser> findByEmailLikeOrFirstNameLikeOrLastNameLikeAndUserStatusIs(String search1, String search2,
			String search3, UserStatus userStatus, Pageable pageable);

	@Query("{'userType' : ?0, 'userStatus' : ?1 , '$or':[ {'firstName':{ $regex: ?2 }}, {'lastName':{ $regex: ?2 }}, {'email':{ $regex: ?2 }}, {'phone':{ $regex: ?2 }} ] }")
	Page<LoginUser> findByUserTypeAndUserStatus(UserType userType, UserStatus userStatus, String search,
			Pageable pageable);
	
	@Query("{ 'userStatus' : 'ACTIVE', '$or':[ {'userType':'SELLER'}, {'userType': 'DIRECTSELLER' } ]   }")
	List<LoginUser> findActiveSeller();

	@Query("{ 'userStatus' : ?0 , '$or':[ {'firstName':{ $regex: ?1 }}, {'lastName':{ $regex: ?2 }} ] }")
	Page<LoginUser> findUserByCriteria(UserStatus userStatus, String firstname, String lastName, Pageable pageable);

	@Query("{'userType' : ?0, '$or':[ {'firstName':{ $regex: ?1 }}, {'lastName':{ $regex: ?1 }}, {'email':{ $regex: ?1 }}, {'phone':{ $regex: ?1 }} ] }")
	Page<LoginUser> findByUserType(UserType userType, String search, Pageable pageable);

	@Query("{'userStatus' : ?0 , '$or':[ {'userType':'PARTNER'}, {'userType': 'DIRECTSELLER' } ] , '$or':[ {'firstName':{ $regex: ?1 }}, {'lastName':{ $regex: ?2 }} ] }")
	Page<LoginUser> findNewRequestsForPartnerAndDirectSeller(UserStatus userStatus, String firstname, String lastName,
			Pageable pageable);

}
