package com.agosh.login.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.agosh.login.config.Constants;
import com.agosh.login.domain.BankAccount;
import com.agosh.login.domain.City;
import com.agosh.login.domain.Country;
import com.agosh.login.domain.Customer;
import com.agosh.login.domain.LoginUser;
import com.agosh.login.domain.Partner;
import com.agosh.login.domain.Seller;
import com.agosh.login.domain.State;
import com.agosh.login.domain.UserAddress;
import com.agosh.login.domain.UserContact;
import com.agosh.login.domain.UserTaxId;
import com.agosh.login.domain.enumeration.AddressType;
import com.agosh.login.domain.enumeration.AuthProvider;
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
import com.agosh.login.repository.UserTaxIdRepository;
import com.agosh.login.security.jwt.JwtTokenGenerator;
import com.agosh.login.security.jwt.JwtTokenValidator;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;

@Service
public class UserServiceImpl implements UserService {

	
	
	private final UserTaxIdRepository userTaxIdRepository;
	
	private final LoginUserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	private final SellerRepository sellerRepository;

	private final PartnerRepository partnerRepository;

	private final CustomerRepository customerRepository;

	private final UserAddressRepository userAddressRepository;

	private final BankAccountRepository bankAccountRepository;

	private final CountryRepository countryRepository;

	private final StateRepository stateRepository;

	private CityRepository cityRepository;

	private UserContactRepository userContactRepository;

	private JwtTokenGenerator jwtTokenGenerator;

	private JwtTokenValidator jwtTokenValidator;

	public UserServiceImpl(SellerRepository sellerRepository, PasswordEncoder passwordEncoder,
			LoginUserRepository userRepository, PartnerRepository partnerRepository,
			BankAccountRepository bankAccountRepository, UserAddressRepository userAddressRepository,
			CustomerRepository customerRepository, StateRepository stateRepository, CountryRepository countryRepository,
			CityRepository cityRepository, UserContactRepository userContactRepository,
			JwtTokenGenerator jwtTokenGenerator, JwtTokenValidator jwtTokenValidator, UserTaxIdRepository userTaxIdRepository) {
		this.sellerRepository = sellerRepository;
		this.passwordEncoder = passwordEncoder;
		this.userRepository = userRepository;
		this.partnerRepository = partnerRepository;
		this.userAddressRepository = userAddressRepository;
		this.customerRepository = customerRepository;
		this.bankAccountRepository = bankAccountRepository;
		this.stateRepository = stateRepository;
		this.countryRepository = countryRepository;
		this.cityRepository = cityRepository;
		this.userContactRepository = userContactRepository;
		this.jwtTokenGenerator = jwtTokenGenerator;
		this.jwtTokenValidator = jwtTokenValidator;
		this.userTaxIdRepository = userTaxIdRepository;
	}

	@Override
	public LoginUser registerUser(SignUpRequest signUpRequest) throws ParseException {

		// Creating user's account
		LoginUser user = new LoginUser();
		user.setName(signUpRequest.getFirstName());
		user.setEmail(signUpRequest.getEmail());
		user.setPassword(signUpRequest.getPassword());
		user.setProvider(AuthProvider.local);

		user.setPassword(passwordEncoder.encode(user.getPassword()));

		// Extra fields
		user.setFirstName(signUpRequest.getFirstName());
		user.setLastName(signUpRequest.getLastName());
		user.setMiddleName(signUpRequest.getMiddleName());
		user.setImageUrl(signUpRequest.getImageUrl());
		user.setPhone(signUpRequest.getPhone());
		user.setUserType(signUpRequest.getUserType());
		user.setUpdatedBy(signUpRequest.getEmail());
		user.setUpdatedOn(LocalDate.now());
		if (StringUtils.hasText(signUpRequest.getDateOfBirth())) {
			SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_US_FORMAT);
			Date date = sdf.parse(signUpRequest.getDateOfBirth());
			user.setDateOfBirth(new java.sql.Date(date.getTime()).toLocalDate());
		}

		if (UserType.BUYER.equals(signUpRequest.getUserType()) || UserType.SELLER.equals(signUpRequest.getUserType())) {
			user.setUserStatus(UserStatus.ACTIVE);
		} else {
			user.setUserStatus(UserStatus.NEW);
		}
		LoginUser dbUser = userRepository.save(user);
		saveUserTaxIds(signUpRequest, dbUser);
		
		
		dbUser.setPassword("");

		if (UserType.SELLER.equals(signUpRequest.getUserType())
				|| UserType.DIRECTSELLER.equals(signUpRequest.getUserType())) {

			Seller seller = new Seller();
			seller.setLoginId(dbUser.getId());
			seller.setUserName(signUpRequest.getEmail());
			seller.setBusinessLicNo(signUpRequest.getBusinessLicNo());
			seller.setBusinessName(signUpRequest.getBusinessName());
			seller.setBusinessType(signUpRequest.getBusinessType());
			seller.setBusinessWebsite(signUpRequest.getBusinessWebsite());
			seller.setTagLine(signUpRequest.getTagLine());
			seller.setNosLocations(signUpRequest.getNosLocations());

			if (UserType.DIRECTSELLER.equals(signUpRequest.getUserType())) {
				seller.setPartnerLoginId("Admin");
			} else {
				Optional<Partner> partnerOpt = findParnerForSeller(signUpRequest.getStateId());
				if (partnerOpt.isPresent()) {
					seller.setPartnerLoginId(partnerOpt.get().getLoginId());
				}
			}

			sellerRepository.save(seller);

			UserAddress userAddress = new UserAddress();
			userAddress.setLoginId(seller.getUserName());
			userAddress.setAddress1(signUpRequest.getAddress1());
			userAddress.setAddress2(signUpRequest.getAddress2());
			userAddress.setLandmark(signUpRequest.getLandmark());
			userAddress.setAddressType(AddressType.PRIMARY);
			userAddress.setCityId(signUpRequest.getCityId());
			userAddress.setCountryId(signUpRequest.getCountryId());
			userAddress.setStateId(signUpRequest.getStateId());
			userAddress.setZipcode(signUpRequest.getZipcode());
			userAddressRepository.save(userAddress);

		} else if (UserType.PARTNER.equals(signUpRequest.getUserType())) {
			Partner partner = new Partner();
			partner.setLoginId(dbUser.getId());
			partner.setRequestMessage(signUpRequest.getRequestMessage());
			partnerRepository.save(partner);
		} else if (UserType.BUYER.equals(signUpRequest.getUserType())
				&& !StringUtils.isEmpty(signUpRequest.getDateOfBirth())) {
			Customer customer = new Customer();
			SimpleDateFormat sdf = new SimpleDateFormat("mm/dd/yyyy");
			Date date = sdf.parse(signUpRequest.getDateOfBirth());
			customer.setLoginId(dbUser.getId());
			customer.setDateOfBirth(new java.sql.Date(date.getTime()).toLocalDate());
			customerRepository.save(customer);
		}
		return dbUser;
	}

	private void saveUserTaxIds(SignUpRequest signUpRequest, LoginUser dbUser) {
		if(null != signUpRequest.getTaxIds()) {
			signUpRequest.getTaxIds().forEach((key, value)->{
				UserTaxId userTaxId = new UserTaxId();
				userTaxId.setLoginId(dbUser.getEmail());
				userTaxId.setTaxIdName(key);
				userTaxId.setTaxIdValue(value);
				userTaxIdRepository.save(userTaxId);
			});
		}
	}

	private Optional<Partner> findParnerForSeller(int stateId) {
		return partnerRepository.findByStateId(stateId);
	}

	@Override
	public LoginUser updateUser(SignUpRequest signUpRequest) throws ParseException {

		LoginUser dbUser = null;
		Optional<LoginUser> userOptional = userRepository.findById(signUpRequest.getId());
		if (userOptional.isPresent()) {
			dbUser = userOptional.get();
			dbUser.setName(signUpRequest.getFirstName());
			dbUser.setFirstName(signUpRequest.getFirstName());
			dbUser.setLastName(signUpRequest.getLastName());
			dbUser.setMiddleName(signUpRequest.getMiddleName());
			dbUser.setImageUrl(signUpRequest.getImageUrl());
			dbUser.setPhone(signUpRequest.getPhone());
			dbUser.setUserType(signUpRequest.getUserType());
			if (StringUtils.hasText(signUpRequest.getDateOfBirth())) {
				SimpleDateFormat sdf = new SimpleDateFormat("mm/dd/yyyy");
				Date date = sdf.parse(signUpRequest.getDateOfBirth());
				dbUser.setDateOfBirth(new java.sql.Date(date.getTime()).toLocalDate());
			}
			userRepository.save(dbUser);
		}

		if (UserType.SELLER.equals(signUpRequest.getUserType())) {
			List<Seller> sellerList = sellerRepository.findByLoginId(signUpRequest.getId());
			if (!sellerList.isEmpty()) {
				Seller seller = sellerList.get(0);
				seller.setBusinessLicNo(signUpRequest.getBusinessLicNo());
				seller.setBusinessName(signUpRequest.getBusinessName());
				seller.setBusinessType(signUpRequest.getBusinessType());
				seller.setBusinessWebsite(signUpRequest.getBusinessWebsite());
				seller.setTagLine(signUpRequest.getTagLine());
				saveUserTaxIds(signUpRequest, dbUser);
				sellerRepository.save(seller);
			}

		}
		return dbUser;
	}

	@Override
	public Partner updatePartner(String loginId, int cityId, int stateId, int countryId) {
		Partner partner = null;
		Optional<Partner> partnerOpt = partnerRepository.findByLoginId(loginId);
		if (partnerOpt.isPresent()) {
			partner = partnerOpt.get();
			partner.setCityId(cityId);
			partner.setStateId(stateId);
			partner.setCountryId(countryId);
			partnerRepository.save(partner);
		}
		return partner;
	}

	@Override
	public UserAddress insertUserAddress(UserAddress userAddress) {
		UserAddress dbUserAddress = null;
		Optional<LoginUser> userOptional = userRepository.findById(userAddress.getLoginId());
		if (userOptional.isPresent()) {
			LoginUser user = userOptional.get();
			userAddress.setLoginUser(user);
			dbUserAddress = userAddressRepository.save(userAddress);
		}
		return dbUserAddress;
	}

	@Override
	public List<UserAddressVO> getUserAddressesByLoginUserId(String userId) {
		List<UserAddressVO> userAddresses = new ArrayList<>();
		List<UserAddress> userAddressList = userAddressRepository.findByLoginUserId(userId);
		userAddressList.forEach(userAddress -> {
			UserAddressVO userAddressVO = populateUserAddressVO(userAddress);
			userAddress.setLoginUser(null);
			userAddresses.add(userAddressVO);
		});
		return userAddresses;
	}

	private UserAddressVO populateUserAddressVO(UserAddress userAddress) {
		UserAddressVO userAddressVO = new UserAddressVO();
		userAddressVO.setAddress1(userAddress.getAddress1());
		userAddressVO.setAddress2(userAddress.getAddress2());
		userAddressVO.setAddressType(userAddress.getAddressType());
		userAddressVO.setBusinessHour(userAddress.getBusinessHour());

		Optional<State> stateOpt = stateRepository.findBySid(userAddress.getStateId());
		if (stateOpt.isPresent()) {
			State state = stateOpt.get();
			userAddressVO.setState(state.getName());
		}
		userAddressVO.setStateId(userAddress.getStateId());

		Optional<Country> countryOpt = countryRepository.findByCid(userAddress.getCountryId());
		if (countryOpt.isPresent()) {
			Country country = countryOpt.get();
			userAddressVO.setCountry(country.getName());
		}
		userAddressVO.setCountryId(userAddress.getCountryId());

		Optional<City> cityOpt = cityRepository.findByCtid(userAddress.getCityId());
		if (cityOpt.isPresent()) {
			City city = cityOpt.get();
			userAddressVO.setCity(city.getName());
		}
		userAddressVO.setCityId(userAddress.getCityId());

		userAddressVO.setId(userAddress.getId());
		userAddressVO.setLandmark(userAddress.getLandmark());
		userAddressVO.setLocationName(userAddress.getLocationName());
		userAddressVO.setLoginId(userAddress.getLoginId());
		userAddressVO.setPhone(userAddress.getPhone());
		userAddressVO.setWebsite(userAddress.getWebsite());
		userAddressVO.setZipcode(userAddress.getZipcode());
		return userAddressVO;
	}

	@Override
	public Pagination findByUserStatus(UserStatus userStatus, Pageable pageable, String search) {
		Page<LoginUser> pageUser = userRepository.findUserByCriteria(userStatus, search, search, pageable);

		return createUserListPagination(pageUser);
	}

	@Override
	public Pagination findNewRequestsForPartnerAndDirectSeller(UserStatus userStatus, Pageable pageable,
			String search) {
		Page<LoginUser> pageUser = userRepository.findNewRequestsForPartnerAndDirectSeller(userStatus, search, search,
				pageable);

		return createUserListPagination(pageUser);
	}

	@Override
	public Set<City> getProductsLocationList() {
		Set<City> cities = new HashSet<>();
		Set<Integer> cityIds = new HashSet<>();

		List<LoginUser> users = userRepository.findActiveSeller();
		System.out.println("users=" + users);

		users.forEach(user -> {
			List<UserAddress> addressesList = userAddressRepository.findByLoginId(user.getEmail());
			addressesList.forEach(address -> cityIds.add(address.getCityId()));
			System.out.println("addressesList=" + addressesList);
		});

		cityIds.forEach(id -> {
			Optional<City> cityOpt = cityRepository.findByCtid(id);
			if (cityOpt.isPresent()) {
				cities.add(cityOpt.get());
			}
		});
		System.out.println("cities=" + cities);
		return cities;
	}

	private UserBasicDetails populateUserBasicDetails(LoginUser loginUser) {
		UserBasicDetails userBasicDetails = new UserBasicDetails();
		userBasicDetails.setEmail(loginUser.getEmail());
		userBasicDetails.setFirstName(loginUser.getFirstName());
		userBasicDetails.setId(loginUser.getId());
		userBasicDetails.setLastName(loginUser.getLastName());
		userBasicDetails.setLoginId(loginUser.getLoginId());
		userBasicDetails.setMiddleName(loginUser.getMiddleName());
		userBasicDetails.setUserStatus(loginUser.getUserStatus().getValue());
		userBasicDetails.setUserType(loginUser.getUserType().getValue());

		return userBasicDetails;
	}

	private SellerDetails populateSellerAddress(UserAddress userAddress, SellerDetails sellerDetails) {
		Optional<State> stateOpt = stateRepository.findBySid(userAddress.getStateId());
		if (stateOpt.isPresent()) {
			State state = stateOpt.get();
			sellerDetails.setState(state.getName());
		}
		sellerDetails.setStateId(userAddress.getStateId());

		Optional<Country> countryOpt = countryRepository.findByCid(userAddress.getCountryId());
		if (countryOpt.isPresent()) {
			Country country = countryOpt.get();
			sellerDetails.setCountry(country.getName());
		}
		sellerDetails.setCountryId(userAddress.getCountryId());

		Optional<City> cityOpt = cityRepository.findByCtid(userAddress.getCityId());
		if (cityOpt.isPresent()) {
			City city = cityOpt.get();
			sellerDetails.setCity(city.getName());
		}
		sellerDetails.setCityId(userAddress.getCityId());
		
		sellerDetails.setAddress1(userAddress.getAddress1());
		sellerDetails.setAddress2(userAddress.getAddress2());
		sellerDetails.setZipcode(userAddress.getZipcode());
		return sellerDetails;
	}

	private SellerDetails populateSellerBasicDetails(LoginUser loginUser, SellerDetails sellerDetails) {
		sellerDetails.setPhone(loginUser.getPhone());
		sellerDetails.setEmail(loginUser.getEmail());
		sellerDetails.setFirstName(loginUser.getFirstName());
		sellerDetails.setLastName(loginUser.getLastName());
		sellerDetails.setMiddleName(loginUser.getMiddleName());
		return sellerDetails;
	}

	private SellerDetails populateSellerCompanyDetails(Seller seller, SellerDetails sellerDetails) {
		sellerDetails.setBusinessName(seller.getBusinessName());
		sellerDetails.setBusinessWebsite(seller.getBusinessWebsite());
		return sellerDetails;
	}

	private UserVO populateUserVO(LoginUser loginUser) {
		UserVO userVO = new UserVO();
		if (loginUser.getUserType().equals(UserType.PARTNER)) {
			Optional<Partner> partnerOpt = partnerRepository.findByLoginId(loginUser.getId());
			populateUserVOForPartner(userVO, partnerOpt);
		} else {
			List<UserAddress> userAddressList = userAddressRepository.findByLoginId(loginUser.getId());
			if (!CollectionUtils.isEmpty(userAddressList)) {
				UserAddress userAddress = userAddressList.get(0);
				userVO.setAddress1(userAddress.getAddress1());
				userVO.setAddress2(userAddress.getAddress2());
				userVO.setAddressType(userAddress.getAddressType());
				userVO.setZipcode(userAddress.getZipcode());
				userVO.setLocationName(userAddress.getLocationName());
				userVO.setLandmark(userAddress.getLandmark());

				Optional<State> stateOpt = stateRepository.findBySid(userAddress.getStateId());
				if (stateOpt.isPresent()) {
					State state = stateOpt.get();
					userVO.setState(state.getName());
					userVO.setStateId(userAddress.getStateId());
				}

				Optional<Country> countryOpt = countryRepository.findByCid(userAddress.getCountryId());
				if (countryOpt.isPresent()) {
					Country country = countryOpt.get();
					userVO.setCountry(country.getName());
					userVO.setCountryId(userAddress.getCountryId());
				}

				Optional<City> cityOpt = cityRepository.findByCtid(userAddress.getCityId());
				if (cityOpt.isPresent()) {
					City city = cityOpt.get();
					userVO.setCity(city.getName());
					userVO.setCityId(userAddress.getCityId());
				}

			}

		}

		userVO.setDateOfBirth(loginUser.getDateOfBirth());
		userVO.setEmail(loginUser.getEmail());
		userVO.setFirstName(loginUser.getFirstName());
		userVO.setId(loginUser.getId());
		userVO.setImageUrl(loginUser.getImageUrl());
		userVO.setLastName(loginUser.getLastName());
		userVO.setLoginId(loginUser.getLoginId());
		userVO.setMiddleName(loginUser.getMiddleName());
		userVO.setName(loginUser.getName());
		userVO.setPhone(loginUser.getPhone());
		userVO.setProvider(loginUser.getProvider());
		userVO.setUserStatus(loginUser.getUserStatus());
		userVO.setUserType(loginUser.getUserType());
		
		Map<String, String> taxIdMap = new HashMap<>();
		List<UserTaxId> userTaxIds = userTaxIdRepository.findByLoginId(loginUser.getEmail());
		userTaxIds.forEach(taxId -> {
			taxIdMap.put(taxId.getTaxIdName(), taxId.getTaxIdValue());
		});
		userVO.setTaxIds(taxIdMap);

		return userVO;
	}

	private void populateUserVOForPartner(UserVO userVO, Optional<Partner> partnerOpt) {
		if (partnerOpt.isPresent()) {
			Partner partner = partnerOpt.get();
			Optional<State> stateOpt = stateRepository.findBySid(partner.getStateId());
			if (stateOpt.isPresent()) {
				State state = stateOpt.get();
				userVO.setState(state.getName());
				userVO.setStateId(partner.getStateId());
			}

			Optional<Country> countryOpt = countryRepository.findByCid(partner.getCountryId());
			if (countryOpt.isPresent()) {
				Country country = countryOpt.get();
				userVO.setCountry(country.getName());
				userVO.setCountryId(partner.getCountryId());
			}

			Optional<City> cityOpt = cityRepository.findByCtid(partner.getCityId());
			if (cityOpt.isPresent()) {
				City city = cityOpt.get();
				userVO.setCity(city.getName());
				userVO.setCityId(partner.getCityId());
			}
		}
	}

	@Override
	public Pagination findByUserTypeAndStatus(UserType userType, UserStatus userStatus, String search,
			Pageable pageable) {

		Page<LoginUser> pageUser = userRepository.findByUserTypeAndUserStatus(userType, userStatus, search, pageable);

		return createUserListPagination(pageUser);
	}

	@Override
	public Pagination findByUserType(UserType userType, String search, Pageable pageable) {
		Page<LoginUser> pageUser = userRepository.findByUserType(userType, search, pageable);

		return createUserListPagination(pageUser);
	}

	private Pagination createUserListPagination(Page<LoginUser> pageUser) {
		List<UserVO> userList = new ArrayList<>();
		List<LoginUser> loginUserList = pageUser.getContent();
		for (LoginUser loginUser : loginUserList) {
			UserVO user = populateUserVO(loginUser);
			userList.add(user);
		}
		Pagination pagination = new Pagination();
		pagination.setContent(userList);
		pagination.setPageNumber(pageUser.getNumber() + 1);
		pagination.setPageSize(pageUser.getSize());
		pagination.setTotalElements(pageUser.getTotalElements());
		pagination.setTotalPages(pageUser.getTotalPages());
		return pagination;
	}

	@Override
	public LoginUser updateUserStatus(String id, UserStatus userStatus) {
		LoginUser dbUser = null;
		Optional<LoginUser> userOptional = userRepository.findById(id);
		if (userOptional.isPresent()) {
			LoginUser user = userOptional.get();
			if (UserStatus.APPROVED.equals(userStatus)) {
				user.setUserStatus(UserStatus.ACTIVE);
			} else {
				user.setUserStatus(userStatus);
			}

			dbUser = userRepository.save(user);
		}
		return dbUser;
	}

	@Override
	public LoginUser changePassword(String id, String password) {
		LoginUser dbUser = null;
		Optional<LoginUser> userOptional = userRepository.findById(id);
		if (userOptional.isPresent()) {
			LoginUser user = userOptional.get();
			user.setPassword(passwordEncoder.encode(password));
			dbUser = userRepository.save(user);
		}
		return dbUser;
	}

	@Override
	public LoginUser resetPassword(String token, String password) {
		LoginUser dbUser = null;
		FpToken fpToken = this.jwtTokenValidator.parseForgotPwdToken(token);
		Optional<LoginUser> userOptional = userRepository.findByEmail(fpToken.getEmail());
		if (userOptional.isPresent()) {
			LoginUser user = userOptional.get();
			user.setPassword(passwordEncoder.encode(password));
			dbUser = userRepository.save(user);
		}
		return dbUser;
	}

	@Override
	public Optional<LoginUser> getUserInfo(String userName) {
		Optional<LoginUser> loginUserOpt = userRepository.findByEmail(userName);
		if (loginUserOpt.isPresent()) {
			LoginUser loginUser = loginUserOpt.get();
			loginUser.setPassword("");
			List<Seller> sellerList = sellerRepository.findByLoginId(loginUser.getId());
			if (!sellerList.isEmpty()) {
				Seller seller = sellerList.get(0);
				loginUser.setPartnerUserName(seller.getPartnerLoginId());
				List<UserAddress> userAddresses = userAddressRepository.findByLoginId(loginUser.getEmail());
				if (!userAddresses.isEmpty()) {
					UserAddress userAddress = userAddresses.get(0);
					loginUser.setCityId(String.valueOf(userAddress.getCityId()));
					loginUser.setStateId(String.valueOf(userAddress.getStateId()));
					loginUser.setCountryId(String.valueOf(userAddress.getCountryId()));
				}
			}
		}
		return loginUserOpt;
	}

	@Override
	public FpToken getForgotPasswordToken(FpToken fpToken) {

		String key = jwtTokenGenerator.generateForgotPwdToken(fpToken);
		fpToken.setKey(key);
		return fpToken;
	}

	@Override
	public String validateTokenForgotPwd(String q, List<String> errorList) {
		String message = "";
		if (StringUtils.isEmpty(q)) {
			message = "token empty";

		} else {

			try {

				jwtTokenValidator.parseForgotPwdToken(q);

			} catch (ExpiredJwtException ejt) {
				message = "ExpiredJwtException";

			} catch (JwtException e) {
				message = "JwtException";
			}
		}
		return message;
	}

	@Override
	public UserVO findUserById(String id) {
		UserVO userVO = null;
		Optional<LoginUser> loginUserOpt = userRepository.findById(id);
		if (loginUserOpt.isPresent()) {
			userVO = populateUserVO(loginUserOpt.get());
		}

		return userVO;
	}

	@Override
	public UserBasicDetails findUserByEmail(String email) {
		UserBasicDetails userBasicDetails = null;
		Optional<LoginUser> loginUserOpt = userRepository.findByEmail(email);
		if (loginUserOpt.isPresent()) {
			userBasicDetails = populateUserBasicDetails(loginUserOpt.get());
		}
		return userBasicDetails;
	}

	@Override
	public SellerDetails findSellerDetailsByEmail(String email) {
		SellerDetails sellerDetails = new SellerDetails();

		Optional<LoginUser> loginUserOpt = userRepository.findByEmail(email);
		if (loginUserOpt.isPresent()) {
			sellerDetails = populateSellerBasicDetails(loginUserOpt.get(), sellerDetails);
		}
		List<UserAddress> userAddressList = userAddressRepository.findByLoginId(email);
		if (!userAddressList.isEmpty()) {
			sellerDetails = populateSellerAddress(userAddressList.get(0), sellerDetails);
		}
		Optional<Seller> sellerOpt = sellerRepository.findByUserName(email);
		if (sellerOpt.isPresent()) {
			Seller seller = sellerOpt.get();
			sellerDetails = populateSellerCompanyDetails(seller, sellerDetails);

		}
		return sellerDetails;
	}

	@Override
	public List<UserContactVO> getUserContactsByEmail(String email) {
		List<UserContactVO> userContactVOList = new ArrayList<>();
		List<UserContact> userContactList = userContactRepository.findByLoginUserEmail(email);
		userContactList.forEach(userContact -> {
			UserContactVO userContactVO = populateUserContactVO(userContact);
			userContactVOList.add(userContactVO);
		});
		return userContactVOList;
	}

	private UserContactVO populateUserContactVO(UserContact userContact) {
		UserContactVO userContactVO = new UserContactVO();
		Optional<State> stateOpt = stateRepository.findBySid(userContact.getStateId());
		if (stateOpt.isPresent()) {
			State state = stateOpt.get();
			userContactVO.setState(state.getName());
			userContactVO.setStateId(userContact.getStateId());
		}

		Optional<Country> countryOpt = countryRepository.findByCid(userContact.getCountryId());
		if (countryOpt.isPresent()) {
			Country country = countryOpt.get();
			userContactVO.setCountry(country.getName());
			userContactVO.setCountryId(userContact.getCountryId());
		}

		Optional<City> cityOpt = cityRepository.findByCtid(userContact.getCityId());
		if (cityOpt.isPresent()) {
			City city = cityOpt.get();
			userContactVO.setCity(city.getName());
			userContactVO.setCityId(userContact.getCityId());
		}
		userContactVO.setAddress1(userContact.getAddress1());
		userContactVO.setAddress2(userContact.getAddress2());
		userContactVO.setContactEmail(userContact.getContactEmail());
		userContactVO.setId(userContact.getId());
		userContactVO.setLandmark(userContact.getLandmark());
		userContactVO.setLoginUserEmail(userContact.getLoginUserEmail());
		userContactVO.setName(userContact.getName());
		userContactVO.setPhone(userContact.getPhone());
		userContactVO.setRelation(userContact.getRelation());
		userContactVO.setUpdatedBy(userContact.getUpdatedBy());
		userContactVO.setUpdatedOn(userContact.getUpdatedOn());
		userContactVO.setZipcode(userContact.getZipcode());

		return userContactVO;
	}

	@Override
	public List<State> getAvailableStatesForPartnerByCountry(int countryId) {
		List<State> availableStateList = new ArrayList<>();
		List<Integer> allocatedStateIds = new ArrayList<>();
		List<Partner> partners = partnerRepository.findByCountryId(countryId);
		partners.forEach(partner -> allocatedStateIds.add(partner.getStateId()));
		List<State> stateList = stateRepository.findByCountryId(countryId);
		stateList.forEach(state -> {

			if (!allocatedStateIds.contains(state.getSid())) {
				availableStateList.add(state);
			}
		});
		return availableStateList;
	}

	@Override
	public BankAccountVO getByUserEmail(String email) {
		BankAccountVO bankAccountVO = null;
		Optional<BankAccount> bankAccountOpt = bankAccountRepository.findByUserEmail(email);
		if (bankAccountOpt.isPresent()) {
			bankAccountVO = populateBankAccountVO(bankAccountOpt.get());
		}

		return bankAccountVO;
	}

	private BankAccountVO populateBankAccountVO(BankAccount bankAccount) {
		BankAccountVO bankAccountVO = new BankAccountVO();
		bankAccountVO.setAccountHolder(bankAccount.getAccountHolder());
		bankAccountVO.setAccountNumber(bankAccount.getAccountNumber());
		bankAccountVO.setBankName(bankAccount.getBankName());
		bankAccountVO.setBranch(bankAccount.getBranch());
		bankAccountVO.setId(bankAccount.getId());
		bankAccountVO.setIfscCode(bankAccount.getIfscCode());
		bankAccountVO.setUserId(bankAccount.getUserId());
		bankAccountVO.setUserEmail(bankAccount.getUserEmail());
		bankAccountVO.setUpdatedOn(bankAccount.getUpdatedOn());
		bankAccountVO.setUpdatedBy(bankAccount.getUpdatedBy());
		Optional<State> stateOpt = stateRepository.findBySid(bankAccount.getStateId());
		if (stateOpt.isPresent()) {
			State state = stateOpt.get();
			bankAccountVO.setState(state.getName());
			bankAccountVO.setStateId(bankAccount.getStateId());
		}

		Optional<Country> countryOpt = countryRepository.findByCid(bankAccount.getCountryId());
		if (countryOpt.isPresent()) {
			Country country = countryOpt.get();
			bankAccountVO.setCountry(country.getName());
			bankAccountVO.setCountryId(bankAccount.getCountryId());
		}

		Optional<City> cityOpt = cityRepository.findByCtid(bankAccount.getCityId());
		if (cityOpt.isPresent()) {
			City city = cityOpt.get();
			bankAccountVO.setCity(city.getName());
			bankAccountVO.setCityId(bankAccount.getCityId());
		}
		return bankAccountVO;
	}

}
