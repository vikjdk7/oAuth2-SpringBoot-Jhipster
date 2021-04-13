package com.agosh.login.security;

import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.agosh.login.domain.LoginUser;
import com.agosh.login.domain.enumeration.UserType;
import com.agosh.login.repository.LoginUserRepository;

@ExtendWith(MockitoExtension.class)
public class UserPrincipalTest {

	@InjectMocks
	private CustomUserDetailsService customUserDetailsService;

	@Mock
	private LoginUserRepository userRepository;

	@Test
	public void create() {

		LoginUser loginUser = new LoginUser();
		loginUser.setId("id");
		loginUser.setPassword("password");
		loginUser.setEmail("abc@abc.com");
		loginUser.setUserType(UserType.PARTNER);

		UserPrincipal userPrincipal = UserPrincipal.create(loginUser);
		assertNotNull(userPrincipal);

	}

	@Test
	public void createWithAttributes() {
		Map<String, Object> attributes = new HashMap<>();
		attributes.put("sub", "sub value");
		attributes.put("picture", "picture url");

		LoginUser loginUser = new LoginUser();
		loginUser.setId("id");
		loginUser.setUserType(UserType.PARTNER);

		UserPrincipal userPrincipal = UserPrincipal.create(loginUser, attributes);
		assertNotNull(userPrincipal);

	}

}
