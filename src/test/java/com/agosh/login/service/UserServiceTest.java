package com.agosh.login.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.agosh.login.domain.BankAccount;
import com.agosh.login.domain.City;
import com.agosh.login.domain.Country;
import com.agosh.login.domain.LoginUser;
import com.agosh.login.domain.Partner;
import com.agosh.login.domain.Seller;
import com.agosh.login.domain.State;
import com.agosh.login.domain.UserAddress;
import com.agosh.login.domain.UserContact;
import com.agosh.login.domain.enumeration.UserStatus;
import com.agosh.login.domain.enumeration.UserType;
import com.agosh.login.pojo.FpToken;
import com.agosh.login.pojo.Pagination;
import com.agosh.login.pojo.SignUpRequest;
import com.agosh.login.repository.BankAccountRepository;
import com.agosh.login.repository.CityRepository;
import com.agosh.login.repository.CountryRepository;
import com.agosh.login.repository.CustomerRepository;
import com.agosh.login.repository.LoginUserRepository;
import com.agosh.login.repository.PartnerRepository;
import com.agosh.login.repository.SellerRepository;
import com.agosh.login.repository.StateRepository;
import com.agosh.login.repository.UserAddressRepository;
import com.agosh.login.repository.UserContactRepository;
import com.agosh.login.security.jwt.JwtTokenGenerator;
import com.agosh.login.security.jwt.JwtTokenValidator;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

	private static final String USER_ID = "user1";
	private static final String LOGIN_ID = "user1";
	private static final String ID = "id1";
	private static final String EMAIL = "abc@abc.com";
	private static final int STATE_ID = 1;
	private static final int CITY_ID = 1;
	private static final int COUNTRY_ID = 1;
	
	@InjectMocks
	private UserServiceImpl userServiceImpl;

	@Mock
	private UserAddressRepository userAddressRepository;

	@Mock
	private LoginUserRepository userRepository;

	@Mock
	private PasswordEncoder passwordEncoder;

	@Mock
	private SellerRepository sellerRepository;

	@Mock
	private PartnerRepository partnerRepository;

	@Mock
	private CustomerRepository customerRepository;

	@Mock
	private BankAccountRepository bankAccountRepository;

	@Mock
	private CountryRepository countryRepository;

	@Mock
	private StateRepository stateRepository;

	@Mock
	private CityRepository cityRepository;

	@Mock
	private UserContactRepository userContactRepository;

	@Mock
	private JwtTokenGenerator jwtTokenGenerator;

	@Mock
	private JwtTokenValidator jwtTokenValidator;

	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void registerUserAsSeller() throws Exception {
		// setup
		LoginUser loginUser = new LoginUser();
		when(userRepository.save(any(LoginUser.class))).thenReturn(loginUser);

		Partner partner = new Partner();
		Optional<Partner> partnerOpt = Optional.of(partner);
		when(partnerRepository.findByStateId(STATE_ID)).thenReturn(partnerOpt);

		SignUpRequest signUpRequest = new SignUpRequest();
		signUpRequest.setId(ID);
		signUpRequest.setUserType(UserType.SELLER);
		signUpRequest.setDateOfBirth("03/01/2020");
		signUpRequest.setStateId(STATE_ID);

		userServiceImpl.registerUser(signUpRequest);
		assertTrue(true);
	}

	@Test
	public void registerUserAsDirectSeller() throws Exception {
		// setup
		LoginUser loginUser = new LoginUser();
		when(userRepository.save(any(LoginUser.class))).thenReturn(loginUser);

		SignUpRequest signUpRequest = new SignUpRequest();
		signUpRequest.setId(ID);
		signUpRequest.setUserType(UserType.DIRECTSELLER);
		signUpRequest.setDateOfBirth("03/01/2020");
		signUpRequest.setStateId(STATE_ID);

		userServiceImpl.registerUser(signUpRequest);
		assertTrue(true);
	}

	@Test
	public void registerUserAsBuyer() throws Exception {
		// setup
		LoginUser loginUser = new LoginUser();
		when(userRepository.save(any(LoginUser.class))).thenReturn(loginUser);

		SignUpRequest signUpRequest = new SignUpRequest();
		signUpRequest.setId(ID);
		signUpRequest.setUserType(UserType.BUYER);
		signUpRequest.setDateOfBirth("03/01/2020");
		signUpRequest.setStateId(STATE_ID);

		userServiceImpl.registerUser(signUpRequest);
		assertTrue(true);
	}

	@Test
	public void registerUserAsPartner() throws Exception {
		// setup
		LoginUser loginUser = new LoginUser();
		when(userRepository.save(any(LoginUser.class))).thenReturn(loginUser);

		SignUpRequest signUpRequest = new SignUpRequest();
		signUpRequest.setId(ID);
		signUpRequest.setUserType(UserType.PARTNER);
		signUpRequest.setDateOfBirth("03/01/2020");
		signUpRequest.setStateId(STATE_ID);

		userServiceImpl.registerUser(signUpRequest);
		assertTrue(true);
	}

	@Test
	public void updateUser() throws Exception {
		// setup
		LoginUser loginUser = new LoginUser();
		Optional<LoginUser> userOptional = Optional.of(loginUser);
		when(userRepository.findById(ID)).thenReturn(userOptional);

		Seller seller = new Seller();
		List<Seller> sellerList = new ArrayList<>();
		sellerList.add(seller);
		when(sellerRepository.findByLoginId(ID)).thenReturn(sellerList);

		SignUpRequest signUpRequest = new SignUpRequest();
		signUpRequest.setId(ID);
		signUpRequest.setUserType(UserType.SELLER);
		signUpRequest.setDateOfBirth("03/01/2020");

		userServiceImpl.updateUser(signUpRequest);
		assertTrue(true);
	}

	@Test
	public void updatePartner() throws Exception {
		// setup
		Partner partner = new Partner();
		Optional<Partner> partnerOpt = Optional.of(partner);
		when(partnerRepository.findByLoginId(LOGIN_ID)).thenReturn(partnerOpt);

		userServiceImpl.updatePartner(LOGIN_ID, CITY_ID, STATE_ID, COUNTRY_ID);
		assertTrue(true);
	}

	@Test
	public void insertUserAddress() throws Exception {
		// setup
		LoginUser loginUser = new LoginUser();
		Optional<LoginUser> userOptional = Optional.of(loginUser);
		when(userRepository.findById(LOGIN_ID)).thenReturn(userOptional);

		UserAddress userAddress = new UserAddress();
		userAddress.setLoginId(LOGIN_ID);

		userServiceImpl.insertUserAddress(userAddress);
		assertTrue(true);
	}

	@Test
	public void getUserAddressesByLoginUserId() throws Exception {
		// setup
		UserAddress userAddress = new UserAddress();
		userAddress.setCityId(CITY_ID);
		userAddress.setStateId(STATE_ID);
		userAddress.setCountryId(COUNTRY_ID);
		List<UserAddress> userAddressList = new ArrayList<>();
		userAddressList.add(userAddress);
		when(userAddressRepository.findByLoginUserId(USER_ID)).thenReturn(userAddressList);

		Optional<State> stateOpt = Optional.of(new State());
		when(stateRepository.findBySid(STATE_ID)).thenReturn(stateOpt);

		Optional<Country> countryOpt = Optional.of(new Country());
		when(countryRepository.findByCid(COUNTRY_ID)).thenReturn(countryOpt);

		Optional<City> cityOpt = Optional.of(new City());
		when(cityRepository.findByCtid(CITY_ID)).thenReturn(cityOpt);

		userServiceImpl.getUserAddressesByLoginUserId(USER_ID);
		assertTrue(true);
	}

	@Test
	public void findByUserStatusForPartner() throws Exception {
		// setup
		LoginUser loginUser = new LoginUser();
		loginUser.setId(ID);
		loginUser.setUserType(UserType.PARTNER);
		List<LoginUser> loginUserList = new ArrayList<>();
		loginUserList.add(loginUser);
		Page<LoginUser> pageUser = new PageImpl<LoginUser>(loginUserList);
		when(userRepository.findUserByCriteria(any(), anyString(), anyString(), any(Pageable.class)))
				.thenReturn(pageUser);

		Partner partner = new Partner();
		partner.setCityId(CITY_ID);
		partner.setStateId(STATE_ID);
		partner.setCountryId(COUNTRY_ID);
		Optional<Partner> partnerOpt = Optional.of(partner);
		when(partnerRepository.findByLoginId(ID)).thenReturn(partnerOpt);

		Optional<State> stateOpt = Optional.of(new State());
		when(stateRepository.findBySid(STATE_ID)).thenReturn(stateOpt);

		Optional<Country> countryOpt = Optional.of(new Country());
		when(countryRepository.findByCid(COUNTRY_ID)).thenReturn(countryOpt);

		Optional<City> cityOpt = Optional.of(new City());
		when(cityRepository.findByCtid(CITY_ID)).thenReturn(cityOpt);

		userServiceImpl.findByUserStatus(UserStatus.ACTIVE, createPageRequest(), "");
		assertTrue(true);
	}

	@Test
	public void findByUserStatusForOtherUsers() throws Exception {
		// setup
		LoginUser loginUser = new LoginUser();
		loginUser.setId(ID);
		loginUser.setUserType(UserType.BUYER);
		List<LoginUser> loginUserList = new ArrayList<>();
		loginUserList.add(loginUser);
		Page<LoginUser> pageUser = new PageImpl<LoginUser>(loginUserList);
		when(userRepository.findUserByCriteria(any(), anyString(), anyString(), any(Pageable.class)))
				.thenReturn(pageUser);

		UserAddress userAddress = new UserAddress();
		userAddress.setCityId(CITY_ID);
		userAddress.setStateId(STATE_ID);
		userAddress.setCountryId(COUNTRY_ID);
		List<UserAddress> userAddressList = new ArrayList<>();
		userAddressList.add(userAddress);
		when(userAddressRepository.findByLoginId(ID)).thenReturn(userAddressList);

		Optional<State> stateOpt = Optional.of(new State());
		when(stateRepository.findBySid(STATE_ID)).thenReturn(stateOpt);

		Optional<Country> countryOpt = Optional.of(new Country());
		when(countryRepository.findByCid(COUNTRY_ID)).thenReturn(countryOpt);

		Optional<City> cityOpt = Optional.of(new City());
		when(cityRepository.findByCtid(CITY_ID)).thenReturn(cityOpt);

		userServiceImpl.findByUserStatus(UserStatus.ACTIVE, createPageRequest(), "");
		assertTrue(true);
	}
	
	@Test
	public void findNewRequestsForPartnerAndDirectSeller() throws Exception {
		// setup
		LoginUser loginUser1 = new LoginUser();
		loginUser1.setId(ID);
		loginUser1.setUserType(UserType.PARTNER);
		LoginUser loginUser2 = new LoginUser();
		loginUser2.setId(ID);
		loginUser2.setUserType(UserType.DIRECTSELLER);
		List<LoginUser> loginUserList = new ArrayList<>();
		loginUserList.add(loginUser1);
		loginUserList.add(loginUser2);
		Page<LoginUser> pageUser = new PageImpl<LoginUser>(loginUserList);
		when(userRepository.findNewRequestsForPartnerAndDirectSeller(any(), anyString(), anyString(), any(Pageable.class)))
				.thenReturn(pageUser);

		UserAddress userAddress = new UserAddress();
		userAddress.setCityId(CITY_ID);
		userAddress.setStateId(STATE_ID);
		userAddress.setCountryId(COUNTRY_ID);
		List<UserAddress> userAddressList = new ArrayList<>();
		userAddressList.add(userAddress);
		when(userAddressRepository.findByLoginId(ID)).thenReturn(userAddressList);
		
		Partner partner = new Partner();
		partner.setCityId(CITY_ID);
		partner.setStateId(STATE_ID);
		partner.setCountryId(COUNTRY_ID);
		Optional<Partner> partnerOpt = Optional.of(partner);
		when(partnerRepository.findByLoginId(ID)).thenReturn(partnerOpt);

		Optional<State> stateOpt = Optional.of(new State());
		when(stateRepository.findBySid(STATE_ID)).thenReturn(stateOpt);

		Optional<Country> countryOpt = Optional.of(new Country());
		when(countryRepository.findByCid(COUNTRY_ID)).thenReturn(countryOpt);

		Optional<City> cityOpt = Optional.of(new City());
		when(cityRepository.findByCtid(CITY_ID)).thenReturn(cityOpt);

		userServiceImpl.findNewRequestsForPartnerAndDirectSeller(UserStatus.NEW, createPageRequest(), "");
		assertTrue(true);
	}

	@Test
	public void findByUserTypeAndStatus() throws Exception {
		// setup
		LoginUser loginUser = new LoginUser();
		loginUser.setId(ID);
		loginUser.setUserType(UserType.SELLER);
		List<LoginUser> loginUserList = new ArrayList<>();
		loginUserList.add(loginUser);
		Page<LoginUser> pageUser = new PageImpl<LoginUser>(loginUserList);
		when(userRepository.findByUserTypeAndUserStatus(any(), any(), anyString(), any(Pageable.class)))
				.thenReturn(pageUser);

		UserAddress userAddress = new UserAddress();
		userAddress.setCityId(CITY_ID);
		userAddress.setStateId(STATE_ID);
		userAddress.setCountryId(COUNTRY_ID);
		List<UserAddress> userAddressList = new ArrayList<>();
		userAddressList.add(userAddress);
		when(userAddressRepository.findByLoginId(ID)).thenReturn(userAddressList);

		Optional<State> stateOpt = Optional.of(new State());
		when(stateRepository.findBySid(STATE_ID)).thenReturn(stateOpt);

		Optional<Country> countryOpt = Optional.of(new Country());
		when(countryRepository.findByCid(COUNTRY_ID)).thenReturn(countryOpt);

		Optional<City> cityOpt = Optional.of(new City());
		when(cityRepository.findByCtid(CITY_ID)).thenReturn(cityOpt);

		userServiceImpl.findByUserTypeAndStatus(UserType.SELLER, UserStatus.ACTIVE, "", createPageRequest());
		assertTrue(true);
	}
	
	@Test
	public void findByUserType() throws Exception {
		// setup
		LoginUser loginUser = new LoginUser();
		loginUser.setId(ID);
		loginUser.setUserType(UserType.SELLER);
		List<LoginUser> loginUserList = new ArrayList<>();
		loginUserList.add(loginUser);
		Page<LoginUser> pageUser = new PageImpl<LoginUser>(loginUserList);
		when(userRepository.findByUserType(any(), anyString(), any(Pageable.class)))
				.thenReturn(pageUser);

		UserAddress userAddress = new UserAddress();
		userAddress.setCityId(CITY_ID);
		userAddress.setStateId(STATE_ID);
		userAddress.setCountryId(COUNTRY_ID);
		List<UserAddress> userAddressList = new ArrayList<>();
		userAddressList.add(userAddress);
		when(userAddressRepository.findByLoginId(ID)).thenReturn(userAddressList);

		Optional<State> stateOpt = Optional.of(new State());
		when(stateRepository.findBySid(STATE_ID)).thenReturn(stateOpt);

		Optional<Country> countryOpt = Optional.of(new Country());
		when(countryRepository.findByCid(COUNTRY_ID)).thenReturn(countryOpt);

		Optional<City> cityOpt = Optional.of(new City());
		when(cityRepository.findByCtid(CITY_ID)).thenReturn(cityOpt);

		Pagination pagination = userServiceImpl.findByUserType(UserType.SELLER, "", createPageRequest());
		assertNotNull(pagination);
	}

	@Test
	public void updateUserStatusApproved() throws Exception {
		// setup
		LoginUser loginUser = new LoginUser();
		Optional<LoginUser> userOptional = Optional.of(loginUser);
		when(userRepository.findById(ID)).thenReturn(userOptional);
		
		userServiceImpl.updateUserStatus(ID, UserStatus.APPROVED);
		assertTrue(true);
	}
	
	@Test
	public void updateUserStatusOther() throws Exception {
		// setup
		LoginUser loginUser = new LoginUser();
		Optional<LoginUser> userOptional = Optional.of(loginUser);
		when(userRepository.findById(ID)).thenReturn(userOptional);
		
		userServiceImpl.updateUserStatus(ID, UserStatus.INACTIVE);
		assertTrue(true);
	}
	
	@Test
	public void changePassword() throws Exception {
		// setup
		LoginUser loginUser = new LoginUser();
		loginUser.setId(ID);
		loginUser.setEmail("email");
		loginUser.setLoginId(LOGIN_ID);
		loginUser.setUserType(UserType.SELLER);
		Optional<LoginUser> userOptional = Optional.of(loginUser);
		when(userRepository.findById(ID)).thenReturn(userOptional);
		
		userServiceImpl.changePassword(ID, "password");
		assertTrue(true);
	}
	
	@Test
	public void resetPassword() throws Exception {
		// setup
		LoginUser loginUser = new LoginUser();
		loginUser.setId(ID);
		loginUser.setEmail(EMAIL);
		loginUser.setLoginId(LOGIN_ID);
		loginUser.setUserType(UserType.SELLER);
		
		FpToken fpToken = new FpToken();
		fpToken.setEmail(EMAIL);
		when(jwtTokenValidator.parseForgotPwdToken(anyString())).thenReturn(fpToken);
		
		
		Optional<LoginUser> userOptional = Optional.of(loginUser);
		when(userRepository.findByEmail(EMAIL)).thenReturn(userOptional);
		
		userServiceImpl.resetPassword("token", "password");
		assertTrue(true);
	}
	
	@Test
	public void getUserInfo() throws Exception {
		// setup
		LoginUser loginUser = new LoginUser();
		loginUser.setId(ID);
		loginUser.setEmail(EMAIL);
		loginUser.setLoginId(LOGIN_ID);
		loginUser.setUserType(UserType.SELLER);
		
		Optional<LoginUser> userOptional = Optional.of(loginUser);
		when(userRepository.findByEmail(EMAIL)).thenReturn(userOptional);
		
		Seller seller = new Seller();
		List<Seller> sellerList = new ArrayList<>();
		sellerList.add(seller);
		when(sellerRepository.findByLoginId(loginUser.getId())).thenReturn(sellerList);
		
		userServiceImpl.getUserInfo(EMAIL);
		assertTrue(true);
	}
	
	@Test
	public void getForgotPasswordToken() throws Exception {
		// setup
		FpToken fpToken = new FpToken();
		fpToken.setEmail(EMAIL);
		when(jwtTokenGenerator.generateForgotPwdToken(fpToken)).thenReturn("key");
		
		userServiceImpl.getForgotPasswordToken(fpToken);
		assertTrue(true);
	}
	
	@Test
	public void validateTokenForgotPwd() throws Exception {
		// setup
		List<String> errorList = new ArrayList<>();
		FpToken fpToken = new FpToken();
		when(jwtTokenValidator.parseForgotPwdToken(anyString())).thenReturn(fpToken);
		
		userServiceImpl.validateTokenForgotPwd("token", errorList);
		assertTrue(true);
	}
	
	@Test
	public void validateTokenForgotPwdWithEmptyToken() throws Exception {
		// setup
		List<String> errorList = new ArrayList<>();
		
		userServiceImpl.validateTokenForgotPwd("", errorList);
		assertTrue(true);
	}
	
	@Test
	public void findUserById() throws Exception {
		// setup
		LoginUser loginUser = new LoginUser();
		loginUser.setId(ID);
		loginUser.setUserType(UserType.PARTNER);
		Optional<LoginUser> userOptional = Optional.of(loginUser);
		when(userRepository.findById(ID)).thenReturn(userOptional);
		
		Partner partner = new Partner();
		partner.setCityId(CITY_ID);
		partner.setStateId(STATE_ID);
		partner.setCountryId(COUNTRY_ID);
		Optional<Partner> partnerOpt = Optional.of(partner);
		when(partnerRepository.findByLoginId(ID)).thenReturn(partnerOpt);

		Optional<State> stateOpt = Optional.of(new State());
		when(stateRepository.findBySid(STATE_ID)).thenReturn(stateOpt);

		Optional<Country> countryOpt = Optional.of(new Country());
		when(countryRepository.findByCid(COUNTRY_ID)).thenReturn(countryOpt);

		Optional<City> cityOpt = Optional.of(new City());
		when(cityRepository.findByCtid(CITY_ID)).thenReturn(cityOpt);
		
		userServiceImpl.findUserById(ID);
		assertTrue(true);
	}
	
	@Test
	public void findUserByEmail() throws Exception {
		// setup
		LoginUser loginUser = new LoginUser();
		loginUser.setId(ID);
		loginUser.setEmail(EMAIL);
		loginUser.setLoginId(LOGIN_ID);
		loginUser.setUserType(UserType.SELLER);
		loginUser.setUserStatus(UserStatus.ACTIVE);
		
		Optional<LoginUser> userOptional = Optional.of(loginUser);
		when(userRepository.findByEmail(EMAIL)).thenReturn(userOptional);
		
		userServiceImpl.findUserByEmail(EMAIL);
		assertTrue(true);
	}
	
	@Test
	public void getUserContactsByEmail() throws Exception {
		// setup
		UserContact userContact = new UserContact();
		userContact.setCityId(CITY_ID);
		userContact.setStateId(STATE_ID);
		userContact.setCountryId(COUNTRY_ID);
		List<UserContact> userContactList = new ArrayList<UserContact>();
		userContactList.add(userContact);
		when(userContactRepository.findByLoginUserEmail(EMAIL)).thenReturn(userContactList);
		
		Optional<State> stateOpt = Optional.of(new State());
		when(stateRepository.findBySid(STATE_ID)).thenReturn(stateOpt);

		Optional<Country> countryOpt = Optional.of(new Country());
		when(countryRepository.findByCid(COUNTRY_ID)).thenReturn(countryOpt);

		Optional<City> cityOpt = Optional.of(new City());
		when(cityRepository.findByCtid(CITY_ID)).thenReturn(cityOpt);
		
		userServiceImpl.getUserContactsByEmail(EMAIL);
		assertTrue(true);
	}
	
	@Test
	public void getAvailableStatesForPartnerByCountry() throws Exception {
		// setup
		State state = new State();
		state.setSid(STATE_ID);
		state.setSid(2);
		List<State> stateList = new ArrayList<>();
		stateList.add(state);
		when(stateRepository.findByCountryId(COUNTRY_ID)).thenReturn(stateList);
		
		Partner partner = new Partner();
		partner.setStateId(STATE_ID);
		List<Partner> partners = new ArrayList<>();
		partners.add(partner);
		when(partnerRepository.findByCountryId(COUNTRY_ID)).thenReturn(partners);
				
		userServiceImpl.getAvailableStatesForPartnerByCountry(COUNTRY_ID);
		assertTrue(true);
	}
	
	@Test
	public void getByUserEmail() throws Exception {
		// setup
		BankAccount bankAccount = new BankAccount();
		bankAccount.setCityId(CITY_ID);
		bankAccount.setStateId(STATE_ID);
		bankAccount.setCountryId(COUNTRY_ID);
		Optional<BankAccount> bankAccountOpt = Optional.of(bankAccount);
		
		when(bankAccountRepository.findByUserEmail(EMAIL)).thenReturn(bankAccountOpt);
		
		Optional<State> stateOpt = Optional.of(new State());
		when(stateRepository.findBySid(STATE_ID)).thenReturn(stateOpt);

		Optional<Country> countryOpt = Optional.of(new Country());
		when(countryRepository.findByCid(COUNTRY_ID)).thenReturn(countryOpt);

		Optional<City> cityOpt = Optional.of(new City());
		when(cityRepository.findByCtid(CITY_ID)).thenReturn(cityOpt);
		
		userServiceImpl.getByUserEmail(EMAIL);
		assertTrue(true);
	}
	
	private Pageable createPageRequest() {
		return PageRequest.of(0, 10);

	}

}
