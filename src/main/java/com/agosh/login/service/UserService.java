package com.agosh.login.service;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Pageable;

import com.agosh.login.domain.City;
import com.agosh.login.domain.LoginUser;
import com.agosh.login.domain.Partner;
import com.agosh.login.domain.State;
import com.agosh.login.domain.UserAddress;
import com.agosh.login.domain.enumeration.UserStatus;
import com.agosh.login.domain.enumeration.UserType;
import com.agosh.login.pojo.BankAccountVO;
import com.agosh.login.pojo.FpToken;
import com.agosh.login.pojo.Pagination;
import com.agosh.login.pojo.SellerDetails;
import com.agosh.login.pojo.SignUpRequest;
import com.agosh.login.pojo.UserAddressVO;
import com.agosh.login.pojo.UserBasicDetails;
import com.agosh.login.pojo.UserContactVO;
import com.agosh.login.pojo.UserVO;

public interface UserService {

	LoginUser registerUser(SignUpRequest signUpRequest) throws ParseException;

	LoginUser updateUser(SignUpRequest signUpRequest) throws ParseException;

	UserAddress insertUserAddress(UserAddress userAddress);

	List<UserAddressVO> getUserAddressesByLoginUserId(String userId);

	LoginUser updateUserStatus(String id, UserStatus valueOf);

	LoginUser changePassword(String id, String password);

	Pagination findByUserStatus(UserStatus userStatus, Pageable pageable, String search);

	Pagination findByUserType(UserType userType, String search, Pageable pageable);

	Pagination findByUserTypeAndStatus(UserType userType, UserStatus userStatus, String search, Pageable pageable);

	String validateTokenForgotPwd(String q, List<String> errorList);

	FpToken getForgotPasswordToken(FpToken fpToken) ;

	LoginUser resetPassword(String email, String password);

	Partner updatePartner(String loginId, int cityId, int stateId, int countryId);

	Pagination findNewRequestsForPartnerAndDirectSeller(UserStatus userStatus, Pageable pageable, String search);

	UserVO findUserById(String id);

	Optional<LoginUser> getUserInfo(String userName);

	UserBasicDetails findUserByEmail(String email);

	List<UserContactVO> getUserContactsByEmail(String email);

	List<State> getAvailableStatesForPartnerByCountry(int countryId);

	BankAccountVO getByUserEmail(String email);

	Set<City> getProductsLocationList();

	SellerDetails findSellerDetailsByEmail(String email);

}
