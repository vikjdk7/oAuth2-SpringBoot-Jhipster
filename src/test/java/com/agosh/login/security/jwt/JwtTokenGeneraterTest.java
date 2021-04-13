package com.agosh.login.security.jwt;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.agosh.login.pojo.FpToken;

import io.github.jhipster.config.JHipsterProperties;

@ExtendWith(MockitoExtension.class)
public class JwtTokenGeneraterTest {

	@InjectMocks
	private JwtTokenGenerator jwtTokenGenerator;

	@Mock
	private JwtTokenUtils tokenUtils;

	@Mock
	private JHipsterProperties jHipsterProperties;

	@Test
	public void generateForgotPwdToken() throws Exception {
		// setup
		FpToken fpToken = new FpToken();
		fpToken.setEmail("abc@abc.com");
		fpToken.setUserType("ADMIN");

		String token = jwtTokenGenerator.generateForgotPwdToken(fpToken);
		assertEquals("", token);

	}

}
