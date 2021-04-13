package com.agosh.login.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.agosh.login.LoginServiceApp;
import com.agosh.login.domain.UserAddress;
import com.agosh.login.domain.enumeration.AddressType;
import com.agosh.login.repository.UserAddressRepository;

/**
 * Integration tests for the {@link UserAddressResource} REST controller.
 */
@SpringBootTest(classes = LoginServiceApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class UserAddressResourceIT {

	private static final String DEFAULT_LOGIN_ID = "AAAAAAAAAA";
	private static final String UPDATED_LOGIN_ID = "BBBBBBBBBB";

	private static final String DEFAULT_ADDRESS_1 = "AAAAAAAAAA";
	private static final String UPDATED_ADDRESS_1 = "BBBBBBBBBB";

	private static final String DEFAULT_ADDRESS_2 = "AAAAAAAAAA";
	private static final String UPDATED_ADDRESS_2 = "BBBBBBBBBB";

	private static final String DEFAULT_LANDMARK = "AAAAAAAAAA";
	private static final String UPDATED_LANDMARK = "BBBBBBBBBB";

	private static final String DEFAULT_CITY = "AAAAAAAAAA";
	
	private static final String DEFAULT_STATE = "AAAAAAAAAA";
	
	private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
	
	private static final String DEFAULT_ZIPCODE = "AAAAAAAAAA";
	private static final String UPDATED_ZIPCODE = "BBBBBBBBBB";

	private static final AddressType DEFAULT_ADDRESS_TYPE = AddressType.PRIMARY;
	private static final AddressType UPDATED_ADDRESS_TYPE = AddressType.SECONDARY;

	private static final String DEFAULT_LOCATION_NAME = "AAAAAAAAAA";
	private static final String UPDATED_LOCATION_NAME = "BBBBBBBBBB";

	private static final String DEFAULT_PHONE = "AAAAAAAAAA";
	private static final String UPDATED_PHONE = "BBBBBBBBBB";

	private static final String DEFAULT_WEBSITE = "AAAAAAAAAA";
	private static final String UPDATED_WEBSITE = "BBBBBBBBBB";

	private static final String DEFAULT_BUSINESS_HOUR = "AAAAAAAAAA";
	private static final String UPDATED_BUSINESS_HOUR = "BBBBBBBBBB";

	@Autowired
	private UserAddressRepository userAddressRepository;

	@Autowired
	private MockMvc restUserAddressMockMvc;

	private UserAddress userAddress;

	/**
	 * Create an entity for this test.
	 *
	 * This is a static method, as tests for other entities might also need it, if
	 * they test an entity which requires the current entity.
	 */
	public static UserAddress createEntity() {
		UserAddress userAddress = new UserAddress().loginId(DEFAULT_LOGIN_ID).address1(DEFAULT_ADDRESS_1)
				.address2(DEFAULT_ADDRESS_2).landmark(DEFAULT_LANDMARK).zipcode(DEFAULT_ZIPCODE)
				.addressType(DEFAULT_ADDRESS_TYPE).locationName(DEFAULT_LOCATION_NAME).phone(DEFAULT_PHONE)
				.website(DEFAULT_WEBSITE).businessHour(DEFAULT_BUSINESS_HOUR);
		return userAddress;
	}

	/**
	 * Create an updated entity for this test.
	 *
	 * This is a static method, as tests for other entities might also need it, if
	 * they test an entity which requires the current entity.
	 */
	public static UserAddress createUpdatedEntity() {
		UserAddress userAddress = new UserAddress().loginId(UPDATED_LOGIN_ID).address1(UPDATED_ADDRESS_1)
				.address2(UPDATED_ADDRESS_2).landmark(UPDATED_LANDMARK).zipcode(UPDATED_ZIPCODE)
				.addressType(UPDATED_ADDRESS_TYPE).locationName(UPDATED_LOCATION_NAME).phone(UPDATED_PHONE)
				.website(UPDATED_WEBSITE).businessHour(UPDATED_BUSINESS_HOUR);
		return userAddress;
	}

	@BeforeEach
	public void initTest() {
		userAddressRepository.deleteAll();
		userAddress = createEntity();
	}

	@Test
	public void createUserAddress() throws Exception {
		int databaseSizeBeforeCreate = userAddressRepository.findAll().size();
		// Create the UserAddress
		restUserAddressMockMvc.perform(post("/api/user-addresses").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(userAddress))).andExpect(status().isCreated());

		// Validate the UserAddress in the database
		List<UserAddress> userAddressList = userAddressRepository.findAll();
		assertThat(userAddressList).hasSize(databaseSizeBeforeCreate + 1);
		UserAddress testUserAddress = userAddressList.get(userAddressList.size() - 1);
		assertThat(testUserAddress.getLoginId()).isEqualTo(DEFAULT_LOGIN_ID);
		assertThat(testUserAddress.getAddress1()).isEqualTo(DEFAULT_ADDRESS_1);
		assertThat(testUserAddress.getAddress2()).isEqualTo(DEFAULT_ADDRESS_2);
		assertThat(testUserAddress.getLandmark()).isEqualTo(DEFAULT_LANDMARK);
		assertThat(testUserAddress.getZipcode()).isEqualTo(DEFAULT_ZIPCODE);
		assertThat(testUserAddress.getAddressType()).isEqualTo(DEFAULT_ADDRESS_TYPE);
		assertThat(testUserAddress.getLocationName()).isEqualTo(DEFAULT_LOCATION_NAME);
		assertThat(testUserAddress.getPhone()).isEqualTo(DEFAULT_PHONE);
		assertThat(testUserAddress.getWebsite()).isEqualTo(DEFAULT_WEBSITE);
		assertThat(testUserAddress.getBusinessHour()).isEqualTo(DEFAULT_BUSINESS_HOUR);
	}

	@Test
	public void createUserAddressWithExistingId() throws Exception {
		int databaseSizeBeforeCreate = userAddressRepository.findAll().size();

		// Create the UserAddress with an existing ID
		userAddress.setId("existing_id");

		// An entity with an existing ID cannot be created, so this API call must fail
		restUserAddressMockMvc.perform(post("/api/user-addresses").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(userAddress))).andExpect(status().isBadRequest());

		// Validate the UserAddress in the database
		List<UserAddress> userAddressList = userAddressRepository.findAll();
		assertThat(userAddressList).hasSize(databaseSizeBeforeCreate);
	}

	@Test
	public void checkAddress1IsRequired() throws Exception {
		int databaseSizeBeforeTest = userAddressRepository.findAll().size();
		// set the field null
		userAddress.setAddress1(null);

		// Create the UserAddress, which fails.

		restUserAddressMockMvc.perform(post("/api/user-addresses").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(userAddress))).andExpect(status().isBadRequest());

		List<UserAddress> userAddressList = userAddressRepository.findAll();
		assertThat(userAddressList).hasSize(databaseSizeBeforeTest);
	}

	@Test
	public void checkCityIsRequired() throws Exception {
		int databaseSizeBeforeTest = userAddressRepository.findAll().size();
		// set the field null

		// Create the UserAddress, which fails.

		restUserAddressMockMvc.perform(post("/api/user-addresses").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(userAddress))).andExpect(status().isBadRequest());

		List<UserAddress> userAddressList = userAddressRepository.findAll();
		assertThat(userAddressList).hasSize(databaseSizeBeforeTest);
	}

	@Test
	public void checkStateIsRequired() throws Exception {
		int databaseSizeBeforeTest = userAddressRepository.findAll().size();
		// set the field null

		// Create the UserAddress, which fails.

		restUserAddressMockMvc.perform(post("/api/user-addresses").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(userAddress))).andExpect(status().isBadRequest());

		List<UserAddress> userAddressList = userAddressRepository.findAll();
		assertThat(userAddressList).hasSize(databaseSizeBeforeTest);
	}

	@Test
	public void checkCountryIsRequired() throws Exception {
		int databaseSizeBeforeTest = userAddressRepository.findAll().size();
		// set the field null

		// Create the UserAddress, which fails.

		restUserAddressMockMvc.perform(post("/api/user-addresses").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(userAddress))).andExpect(status().isBadRequest());

		List<UserAddress> userAddressList = userAddressRepository.findAll();
		assertThat(userAddressList).hasSize(databaseSizeBeforeTest);
	}

	@Test
	public void checkZipcodeIsRequired() throws Exception {
		int databaseSizeBeforeTest = userAddressRepository.findAll().size();
		// set the field null
		userAddress.setZipcode(null);

		// Create the UserAddress, which fails.

		restUserAddressMockMvc.perform(post("/api/user-addresses").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(userAddress))).andExpect(status().isBadRequest());

		List<UserAddress> userAddressList = userAddressRepository.findAll();
		assertThat(userAddressList).hasSize(databaseSizeBeforeTest);
	}

	@Test
	public void getAllUserAddresses() throws Exception {
		// Initialize the database
		userAddressRepository.save(userAddress);

		// Get all the userAddressList
		restUserAddressMockMvc.perform(get("/api/user-addresses?sort=id,desc")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.[*].id").value(hasItem(userAddress.getId())))
				.andExpect(jsonPath("$.[*].loginId").value(hasItem(DEFAULT_LOGIN_ID)))
				.andExpect(jsonPath("$.[*].address1").value(hasItem(DEFAULT_ADDRESS_1)))
				.andExpect(jsonPath("$.[*].address2").value(hasItem(DEFAULT_ADDRESS_2)))
				.andExpect(jsonPath("$.[*].landmark").value(hasItem(DEFAULT_LANDMARK)))
				.andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
				.andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE)))
				.andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)))
				.andExpect(jsonPath("$.[*].zipcode").value(hasItem(DEFAULT_ZIPCODE)))
				.andExpect(jsonPath("$.[*].addressType").value(hasItem(DEFAULT_ADDRESS_TYPE.toString())))
				.andExpect(jsonPath("$.[*].locationName").value(hasItem(DEFAULT_LOCATION_NAME)))
				.andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
				.andExpect(jsonPath("$.[*].website").value(hasItem(DEFAULT_WEBSITE)))
				.andExpect(jsonPath("$.[*].businessHour").value(hasItem(DEFAULT_BUSINESS_HOUR)));
	}

	@Test
	public void getUserAddress() throws Exception {
		// Initialize the database
		userAddressRepository.save(userAddress);

		// Get the userAddress
		restUserAddressMockMvc.perform(get("/api/user-addresses/{id}", userAddress.getId())).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.id").value(userAddress.getId()))
				.andExpect(jsonPath("$.loginId").value(DEFAULT_LOGIN_ID))
				.andExpect(jsonPath("$.address1").value(DEFAULT_ADDRESS_1))
				.andExpect(jsonPath("$.address2").value(DEFAULT_ADDRESS_2))
				.andExpect(jsonPath("$.landmark").value(DEFAULT_LANDMARK))
				.andExpect(jsonPath("$.city").value(DEFAULT_CITY)).andExpect(jsonPath("$.state").value(DEFAULT_STATE))
				.andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY))
				.andExpect(jsonPath("$.zipcode").value(DEFAULT_ZIPCODE))
				.andExpect(jsonPath("$.addressType").value(DEFAULT_ADDRESS_TYPE.toString()))
				.andExpect(jsonPath("$.locationName").value(DEFAULT_LOCATION_NAME))
				.andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
				.andExpect(jsonPath("$.website").value(DEFAULT_WEBSITE))
				.andExpect(jsonPath("$.businessHour").value(DEFAULT_BUSINESS_HOUR));
	}

	@Test
	public void getNonExistingUserAddress() throws Exception {
		// Get the userAddress
		restUserAddressMockMvc.perform(get("/api/user-addresses/{id}", Long.MAX_VALUE))
				.andExpect(status().isNotFound());
	}

	@Test
	public void updateUserAddress() throws Exception {
		// Initialize the database
		userAddressRepository.save(userAddress);

		int databaseSizeBeforeUpdate = userAddressRepository.findAll().size();

		// Update the userAddress
		UserAddress updatedUserAddress = userAddressRepository.findById(userAddress.getId()).get();
		updatedUserAddress.loginId(UPDATED_LOGIN_ID).address1(UPDATED_ADDRESS_1).address2(UPDATED_ADDRESS_2)
				.landmark(UPDATED_LANDMARK).zipcode(UPDATED_ZIPCODE).addressType(UPDATED_ADDRESS_TYPE)
				.locationName(UPDATED_LOCATION_NAME).phone(UPDATED_PHONE).website(UPDATED_WEBSITE)
				.businessHour(UPDATED_BUSINESS_HOUR);

		restUserAddressMockMvc.perform(put("/api/user-addresses").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(updatedUserAddress))).andExpect(status().isOk());

		// Validate the UserAddress in the database
		List<UserAddress> userAddressList = userAddressRepository.findAll();
		assertThat(userAddressList).hasSize(databaseSizeBeforeUpdate);
		UserAddress testUserAddress = userAddressList.get(userAddressList.size() - 1);
		assertThat(testUserAddress.getLoginId()).isEqualTo(UPDATED_LOGIN_ID);
		assertThat(testUserAddress.getAddress1()).isEqualTo(UPDATED_ADDRESS_1);
		assertThat(testUserAddress.getAddress2()).isEqualTo(UPDATED_ADDRESS_2);
		assertThat(testUserAddress.getLandmark()).isEqualTo(UPDATED_LANDMARK);
		assertThat(testUserAddress.getZipcode()).isEqualTo(UPDATED_ZIPCODE);
		assertThat(testUserAddress.getAddressType()).isEqualTo(UPDATED_ADDRESS_TYPE);
		assertThat(testUserAddress.getLocationName()).isEqualTo(UPDATED_LOCATION_NAME);
		assertThat(testUserAddress.getPhone()).isEqualTo(UPDATED_PHONE);
		assertThat(testUserAddress.getWebsite()).isEqualTo(UPDATED_WEBSITE);
		assertThat(testUserAddress.getBusinessHour()).isEqualTo(UPDATED_BUSINESS_HOUR);
	}

	@Test
	public void updateNonExistingUserAddress() throws Exception {
		int databaseSizeBeforeUpdate = userAddressRepository.findAll().size();

		// If the entity doesn't have an ID, it will throw BadRequestAlertException
		restUserAddressMockMvc.perform(put("/api/user-addresses").contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(userAddress))).andExpect(status().isBadRequest());

		// Validate the UserAddress in the database
		List<UserAddress> userAddressList = userAddressRepository.findAll();
		assertThat(userAddressList).hasSize(databaseSizeBeforeUpdate);
	}

	@Test
	public void deleteUserAddress() throws Exception {
		// Initialize the database
		userAddressRepository.save(userAddress);

		int databaseSizeBeforeDelete = userAddressRepository.findAll().size();

		// Delete the userAddress
		restUserAddressMockMvc
				.perform(delete("/api/user-addresses/{id}", userAddress.getId()).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());

		// Validate the database contains one less item
		List<UserAddress> userAddressList = userAddressRepository.findAll();
		assertThat(userAddressList).hasSize(databaseSizeBeforeDelete - 1);
	}
}
