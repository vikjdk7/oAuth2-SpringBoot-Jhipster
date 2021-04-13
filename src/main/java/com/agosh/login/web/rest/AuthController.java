package com.agosh.login.web.rest;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agosh.login.domain.City;
import com.agosh.login.domain.Country;
import com.agosh.login.domain.LoginUser;
import com.agosh.login.domain.State;
import com.agosh.login.pojo.ApiMessage;
import com.agosh.login.pojo.AuthResponse;
import com.agosh.login.pojo.FpToken;
import com.agosh.login.pojo.LoginRequest;
import com.agosh.login.pojo.PublishMessage;
import com.agosh.login.pojo.ResetPassword;
import com.agosh.login.pojo.SignUpRequest;
import com.agosh.login.repository.CityRepository;
import com.agosh.login.repository.CountryRepository;
import com.agosh.login.repository.LoginUserRepository;
import com.agosh.login.repository.StateRepository;
import com.agosh.login.security.jwt.TokenProvider;
import com.agosh.login.service.MailService;
import com.agosh.login.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private TokenProvider tokenProvider;

	@Autowired
	private UserService userService;

	@Autowired
	private LoginUserRepository userRepository;

	@Autowired
	private MailService mailService;

	@Autowired
	private CountryRepository countryRepository;

	@Autowired
	private StateRepository stateRepository;

	@Autowired
	private CityRepository cityRepository;

	@PostMapping("/login")
	public ResponseEntity<AuthResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String token = tokenProvider.createToken(authentication, true);
		return ResponseEntity.ok(new AuthResponse(token));
	}

	@PostMapping("/signup")
	public ResponseEntity<ApiMessage> registerUser(@Valid @RequestBody SignUpRequest signUpRequest)
			throws ParseException {
		ResponseEntity<ApiMessage> response = null;
		ApiMessage apiMessage = new ApiMessage();
		if (Boolean.TRUE.equals(userRepository.existsByEmail(signUpRequest.getEmail()))) {
			apiMessage.setError("Email address already in use.");
			response = ResponseEntity.badRequest().body(apiMessage);
		} else {
			if (StringUtils.isEmpty(signUpRequest.getPassword())) {
				signUpRequest.setPassword(" ");
			}

			userService.registerUser(signUpRequest);
			apiMessage.setMessage("User registered successfully.");
			response = ResponseEntity.ok(apiMessage);
		}
		return response;
	}

	@PostMapping("/forgotpassword")
	public ResponseEntity<ApiMessage> forgotPassword(@Valid @RequestBody FpToken fpToken) {
		Optional<LoginUser> loginUserOpt = this.userRepository.findByEmail(fpToken.getEmail());
		ApiMessage apiMessage = new ApiMessage();
		if (loginUserOpt.isPresent()) {
			LoginUser loginUser = loginUserOpt.get();
			fpToken.setFirstName(loginUser.getFirstName());
			fpToken.setLastName(loginUser.getLastName());
			fpToken.setEmail(loginUser.getEmail());
			userService.getForgotPasswordToken(fpToken);
			String emailBody = mailService.prepareForgotPwdEmailBody(fpToken);
			String from = "agosh@agosh.com";
			String type = "Forgot Password";
			String subject = "Agosh - reset password";
			String email = fpToken.getEmail();
			PublishMessage publishMessage = new PublishMessage(subject, emailBody, from, email, type);
			mailService.sendNotification("AgoshNotificationTopic", publishMessage);

			apiMessage.setMessage("Reset password link has beens sent on your email.");
			return ResponseEntity.ok(apiMessage);
		} else {
			apiMessage.setError("Email address is not registered.");
			return new ResponseEntity<>(apiMessage, HttpStatus.BAD_REQUEST);
		}

	}

	@PostMapping("/resetpassword")
	public ResponseEntity<ApiMessage> resetPassword(@Valid @RequestBody ResetPassword resetPassword) {
		ApiMessage apiMessage = new ApiMessage();
		LoginUser loginUser = userService.resetPassword(resetPassword.getKey(), resetPassword.getNewPassword());
		if (loginUser != null) {
			apiMessage.setMessage("Your password has been reset successfull.");
			return ResponseEntity.ok(apiMessage);
		} else {
			apiMessage.setError("Invalid request for reset password.");
			return new ResponseEntity<>(apiMessage, HttpStatus.BAD_REQUEST);
		}

	}

	/**
	 * {@code GET  /countries} : get all the countries.
	 *
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of countries in body.
	 */
	@GetMapping("/countries")
	public List<Country> getAllCountries() {
		return countryRepository.findAll();
	}

	/**
	 * {@code GET  /states/:id} : get the states by country
	 *
	 * @param id the id of the state to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of states in body.
	 */
	@GetMapping("/states/country/{countryId}")
	public List<State> getStateByCountry(@PathVariable int countryId) {
		return stateRepository.findByCountryId(countryId);
	}

	/**
	 * {@code GET  /cities} : get cities by state
	 *
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of cities in body.
	 */
	@GetMapping("/cities/state/{stateId}")
	public List<City> getCitiesByState(@PathVariable int stateId) {
		return cityRepository.findByStateId(stateId);
	}
	
	/**
	 * {@code GET  /cities} : get cities for existing products
	 *
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of cities in body.
	 */
	@GetMapping("/productcities")
	public Set<City> getCitiesForExistingProducts() {
		return userService.getProductsLocationList();
	}


	/**
	 * {@code GET  /states/:id} : get the states by country
	 *
	 * @param id the id of the state to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of states in body.
	 */
	@GetMapping("/availablestates/country/{countryId}")
	public List<State> getAvailableStatesForPartnerByCountry(@PathVariable int countryId) {
		return userService.getAvailableStatesForPartnerByCountry(countryId);

	}

}
