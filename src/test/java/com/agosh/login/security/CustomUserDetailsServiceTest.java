package com.agosh.login.security;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.agosh.login.domain.LoginUser;
import com.agosh.login.domain.enumeration.UserStatus;
import com.agosh.login.domain.enumeration.UserType;
import com.agosh.login.repository.LoginUserRepository;
import com.agosh.login.web.rest.errors.ResourceNotFoundException;

@ExtendWith(MockitoExtension.class)
public class CustomUserDetailsServiceTest {

	@InjectMocks
	private CustomUserDetailsService customUserDetailsService;

	@Mock
	private LoginUserRepository userRepository;

	private static final String LOGIN_ID = "user1";
	private static final String ID = "id1";
	private static final String EMAIL = "abc@abc.com";

	@Test
	public void loadUserByUsernameForInactiveUser() {
		LoginUser loginUser = new LoginUser();
		loginUser.setId(ID);
		loginUser.setEmail(EMAIL);
		loginUser.setLoginId(LOGIN_ID);
		loginUser.setUserType(UserType.SELLER);
		loginUser.setUserStatus(UserStatus.INACTIVE);

		Optional<LoginUser> userOptional = Optional.of(loginUser);
		when(userRepository.findByEmail(EMAIL)).thenReturn(userOptional);

		try {
			customUserDetailsService.loadUserByUsername(EMAIL);
			assertTrue(false);
		} catch (UsernameNotFoundException e) {
			assertTrue(true);
		}

	}

	@Test
	public void loadUserByUsername() {
		LoginUser loginUser = new LoginUser();
		loginUser.setId(ID);
		loginUser.setEmail(EMAIL);
		loginUser.setLoginId(LOGIN_ID);
		loginUser.setUserType(UserType.SELLER);
		loginUser.setUserStatus(UserStatus.ACTIVE);

		Optional<LoginUser> userOptional = Optional.of(loginUser);
		when(userRepository.findByEmail(EMAIL)).thenReturn(userOptional);

		UserDetails userDetails = customUserDetailsService.loadUserByUsername(EMAIL);
		assertNotNull(userDetails);
	}

	@Test
	public void loadUserByIdForNotFoundUser() {
		when(userRepository.findById(ID)).thenThrow(new ResourceNotFoundException("User", "id", ID));

		try {
			customUserDetailsService.loadUserById(ID);
			assertTrue(false);
		} catch (ResourceNotFoundException e) {
			assertTrue(true);
		}

	}

	@Test
	public void loadUserById() {
		LoginUser loginUser = new LoginUser();
		loginUser.setId(ID);
		loginUser.setEmail(EMAIL);
		loginUser.setLoginId(LOGIN_ID);
		loginUser.setUserType(UserType.SELLER);
		loginUser.setUserStatus(UserStatus.ACTIVE);

		Optional<LoginUser> userOptional = Optional.of(loginUser);
		when(userRepository.findById(ID)).thenReturn(userOptional);

		UserDetails userDetails = customUserDetailsService.loadUserById(ID);
		assertNotNull(userDetails);
	}

}
