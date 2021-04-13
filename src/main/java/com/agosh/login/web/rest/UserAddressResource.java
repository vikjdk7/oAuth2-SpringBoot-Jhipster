package com.agosh.login.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agosh.login.domain.UserAddress;
import com.agosh.login.pojo.UserAddressVO;
import com.agosh.login.repository.UserAddressRepository;
import com.agosh.login.service.UserService;
import com.agosh.login.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.agosh.login.domain.UserAddress}.
 */
@RestController
@RequestMapping("/api")
public class UserAddressResource {

	private final Logger log = LoggerFactory.getLogger(UserAddressResource.class);

	private static final String ENTITY_NAME = "loginServiceUserAddress";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final UserAddressRepository userAddressRepository;

	private final UserService userService;

	public UserAddressResource(UserAddressRepository userAddressRepository, UserService userService) {
		this.userAddressRepository = userAddressRepository;
		this.userService = userService;
	}

	/**
	 * {@code POST  /user-addresses} : Create a new userAddress.
	 *
	 * @param userAddress the userAddress to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new userAddress, or with status {@code 400 (Bad Request)} if
	 *         the userAddress has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/user-addresses")
	public ResponseEntity<UserAddress> createUserAddress(@Valid @RequestBody UserAddress userAddress)
			throws URISyntaxException {
		log.debug("REST request to save UserAddress : {}", userAddress);
		if (userAddress.getId() != null) {
			throw new BadRequestAlertException("A new userAddress cannot already have an ID", ENTITY_NAME, "idexists");
		}

		UserAddress result = userService.insertUserAddress(userAddress);
		return ResponseEntity.created(new URI("/api/user-addresses/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
				.body(result);
	}

	/**
	 * {@code PUT  /user-addresses} : Updates an existing userAddress.
	 *
	 * @param userAddress the userAddress to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated userAddress, or with status {@code 400 (Bad Request)} if
	 *         the userAddress is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the userAddress couldn't be
	 *         updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/user-addresses")
	public ResponseEntity<UserAddress> updateUserAddress(@Valid @RequestBody UserAddress userAddress)
			throws URISyntaxException {
		log.debug("REST request to update UserAddress : {}", userAddress);
		if (userAddress.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		UserAddress result = userService.insertUserAddress(userAddress);
		return ResponseEntity.ok()
				.headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userAddress.getId()))
				.body(result);
	}

	/**
	 * {@code GET  /user-addresses} : get all the userAddresses.
	 *
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of userAddresses in body.
	 */
	@GetMapping("/user-addresses")
	public List<UserAddress> getAllUserAddresses() {
		log.debug("REST request to get all UserAddresses");
		return userAddressRepository.findAll();
	}

	/**
	 * {@code GET  /user-addresses} : get all the userAddresses by user id
	 *
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of userAddresses in body.
	 */
	@GetMapping("/user-addresses/user/{userId}")
	public List<UserAddressVO> getUserAddressesByUserId(@PathVariable String userId) {
		log.debug("REST request to get UserAddresses by UserId");
		return userService.getUserAddressesByLoginUserId(userId);

	}
	
	/**
	 * {@code GET  /user-addresses} : get all the userAddresses by user email
	 *
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of userAddresses in body.
	 */
	@GetMapping("/user-addresses/useremail/{userEmail}")
	public List<UserAddress> getUserAddressesByUserEmail(@PathVariable String userEmail) {
		log.debug("REST request to get UserAddresses by userEmail");
		return userAddressRepository.findByLoginId(userEmail);

	}

	/**
	 * {@code GET  /user-addresses/:id} : get the "id" userAddress.
	 *
	 * @param id the id of the userAddress to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the userAddress, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/user-addresses/{id}")
	public ResponseEntity<UserAddress> getUserAddress(@PathVariable String id) {
		log.debug("REST request to get UserAddress : {}", id);
		Optional<UserAddress> userAddress = userAddressRepository.findById(id);

		return ResponseUtil.wrapOrNotFound(userAddress);
	}

	/**
	 * {@code DELETE  /user-addresses/:id} : delete the "id" userAddress.
	 *
	 * @param id the id of the userAddress to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/user-addresses/{id}")
	public ResponseEntity<Void> deleteUserAddress(@PathVariable String id) {
		log.debug("REST request to delete UserAddress : {}", id);
		userAddressRepository.deleteById(id);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
	}
}
