package com.agosh.login.web.rest;

import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.agosh.login.domain.LoginUser;
import com.agosh.login.domain.enumeration.UserStatus;
import com.agosh.login.domain.enumeration.UserType;
import com.agosh.login.pojo.ApiMessage;
import com.agosh.login.pojo.Pagination;
import com.agosh.login.pojo.Password;
import com.agosh.login.pojo.SignUpRequest;
import com.agosh.login.pojo.UserBasicDetails;
import com.agosh.login.pojo.UserVO;
import com.agosh.login.repository.LoginUserRepository;
import com.agosh.login.service.UserService;
import com.agosh.login.web.rest.errors.BadRequestAlertException;
import com.agosh.login.web.rest.errors.BadRequestException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.agosh.login.domain.LoginUser}.
 */
@RestController
@RequestMapping("/api")
public class LoginUserResource {

	private static final String UPDATED_ON = "updated_on";

	private final Logger log = LoggerFactory.getLogger(LoginUserResource.class);

	private static final String ENTITY_NAME = "loginServiceLoginUser";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final LoginUserRepository loginUserRepository;

	private final UserService userService;

	private final PasswordEncoder passwordEncoder;

	public LoginUserResource(LoginUserRepository loginUserRepository, UserService userService,
			PasswordEncoder passwordEncoder) {
		this.loginUserRepository = loginUserRepository;
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
	}

	/**
	 * update user
	 * 
	 * @param signUpRequest
	 * @return
	 * @throws URISyntaxException
	 * @throws ParseException
	 */
	@PutMapping("/user")
	public ResponseEntity<LoginUser> updateLoginUser(@RequestBody SignUpRequest signUpRequest) throws ParseException {
		log.debug("REST request to update LoginUser : {}", signUpRequest);
		if (signUpRequest.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		if (Boolean.FALSE.equals(loginUserRepository.existsById(signUpRequest.getId()))) {
			throw new BadRequestException("User doesn't exits");
		}

		LoginUser result = userService.updateUser(signUpRequest);
		result.setPassword("");
		return ResponseEntity.ok()
				.headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, signUpRequest.getId()))
				.body(result);
	}

	/**
	 * change password
	 * 
	 * @param signUpRequest
	 * @return
	 * @throws URISyntaxException
	 */
	@PutMapping("/changepassword")
	public ResponseEntity<ApiMessage> changePassword(@RequestBody Password password) {
		ApiMessage apiMessage = new ApiMessage();
		if (password.getId() == null) {
			apiMessage.setError("Invalid User Id.");
			return new ResponseEntity<>(apiMessage, HttpStatus.BAD_REQUEST);
		}
		if (Boolean.FALSE.equals(loginUserRepository.existsById(password.getId()))) {
			apiMessage.setError("User doesn't exits.");
			return new ResponseEntity<>(apiMessage, HttpStatus.BAD_REQUEST);
		} else {
			Optional<LoginUser> loginUserOpt = loginUserRepository.findById(password.getId());
			if (loginUserOpt.isPresent()) {
				LoginUser loginUser = loginUserOpt.get();
				if (!passwordEncoder.matches(password.getCurrentPassword(), loginUser.getPassword())) {
					apiMessage.setError("Incorrect current password.");
					return new ResponseEntity<>(apiMessage, HttpStatus.BAD_REQUEST);
				}
			}
		}

		userService.changePassword(password.getId(), password.getNewPassword());
		apiMessage.setMessage("Your password has been changed successfully.");
		return ResponseEntity.ok(apiMessage);
	}

	/**
	 * Update user's status
	 * 
	 * @param id
	 * @param status
	 * @return
	 * @throws URISyntaxException
	 */
	@PutMapping("/updateuserstatus/{id}/{status}")
	public ResponseEntity<LoginUser> updateUserStatus(@PathVariable String id, @PathVariable String status) {
		log.debug("REST request to update LoginUser status: {}", id);
		if (id == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		if (Boolean.FALSE.equals(loginUserRepository.existsById(id))) {
			throw new BadRequestException("User doesn't exits");
		}

		LoginUser result = userService.updateUserStatus(id, UserStatus.valueOf(status));
		result.setPassword("");
		return ResponseEntity.ok()
				.headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId()))
				.body(result);
	}

	/**
	 * {@code GET  /login-users} : get all the loginUsers.
	 *
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of loginUsers in body.
	 */
	@GetMapping("/login-users")
	public List<LoginUser> getAllLoginUsers() {
		log.debug("REST request to get all LoginUsers");
		return loginUserRepository.findAll();
	}

	/**
	 * {@code GET  /login-users} : get users by status
	 *
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of loginUsers in body.
	 */
	@GetMapping("/login-users/userstatus/{status}")
	public Pagination getUsersByStatus(@PathVariable String status, @RequestParam int pageIndex,
			@RequestParam int pageSize, @RequestParam String search) {
		log.debug("REST request to get all LoginUsers");
		Pageable pageable = PageRequest.of(pageIndex - 1, pageSize, Sort.Direction.ASC, UPDATED_ON);
		return userService.findByUserStatus(UserStatus.valueOf(status), pageable, search);
	}

	/**
	 * {@code GET  /login-users} : get pending requests for Partner & Direct Seller
	 *
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of loginUsers in body.
	 */
	@GetMapping("/login-users/newrequest/{status}")
	public Pagination getNewRequestOfPartnerAndDirectSeller(@PathVariable String status, @RequestParam int pageIndex,
			@RequestParam int pageSize, @RequestParam String search) {
		log.debug("REST request to get pending requests for Partner & Direct Seller");
		Pageable pageable = PageRequest.of(pageIndex - 1, pageSize, Sort.Direction.ASC, UPDATED_ON);
		return userService.findNewRequestsForPartnerAndDirectSeller(UserStatus.valueOf(status), pageable, search);
	}

	/**
	 * {@code GET  /login-users} : Admin gets list of users for particular type and
	 * status
	 *
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of loginUsers in body.
	 */
	@GetMapping("/login-users/usertype/{userType}")
	public Pagination getUsersByUserTypeAndStatus(@PathVariable String userType, @RequestParam String userStatus,
			@RequestParam int pageIndex, @RequestParam int pageSize, @RequestParam String search) {
		log.debug("REST request to get getUsersByUserTypeAndStatus");
		Pageable pageable = PageRequest.of(pageIndex - 1, pageSize, Sort.Direction.DESC, UPDATED_ON);
		return userService.findByUserTypeAndStatus(UserType.valueOf(userType), UserStatus.valueOf(userStatus), search,
				pageable);
	}

	/**
	 * Get list of users by its type and for all statuses
	 * 
	 * @param userType
	 * @param userStatus
	 * @param pageIndex
	 * @param pageSize
	 * @param search
	 * @return
	 */
	@GetMapping("/login-users/mutliplestatus/usertype/{userType}")
	public Pagination getUsersByUserType(@PathVariable String userType, @RequestParam int pageIndex,
			@RequestParam int pageSize, @RequestParam String search) {
		log.debug("REST request to get getUsersByUserTypeAndStatusIn");
		Pageable pageable = PageRequest.of(pageIndex - 1, pageSize, Sort.Direction.DESC, UPDATED_ON);
		return userService.findByUserType(UserType.valueOf(userType), search, pageable);
	}

	/**
	 * 
	 * @return
	 */
	@GetMapping("/userinfo")
	public ResponseEntity<LoginUser> getUserInfo() {
		log.debug("REST request to getUserInfo");
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		return ResponseEntity.of(userService.getUserInfo(user.getUsername()));
	}

	/**
	 * {@code GET  /login-users/:id} : get the "id" loginUser.
	 *
	 * @param id the id of the loginUser to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the loginUser, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/login-users/{id}")
	public ResponseEntity<UserVO> getLoginUser(@PathVariable String id) {
		log.debug("REST request to get LoginUser : {}", id);
		Optional<UserVO> loginUser = Optional.empty();
		UserVO userVO = userService.findUserById(id);
		if (userVO != null) {
			loginUser = Optional.of(userVO);
		}

		return ResponseUtil.wrapOrNotFound(loginUser);
	}

	/**
	 * {@code GET  /login-users/:id} : get the user by email
	 *
	 * @param email the email of the loginUser to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the loginUser, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/login-users/email/{email}")
	public ResponseEntity<UserBasicDetails> getLoginUserByEmail(@PathVariable String email) {
		log.debug("REST request to get LoginUser : {}", email);
		Optional<UserBasicDetails> userBasicDetailsOpt = Optional.empty();
		UserBasicDetails userBasicDetails = userService.findUserByEmail(email);
		if (userBasicDetails != null) {
			userBasicDetailsOpt = Optional.of(userBasicDetails);
		}

		return ResponseUtil.wrapOrNotFound(userBasicDetailsOpt);
	}

}
