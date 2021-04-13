package com.agosh.login.security.jwt;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.agosh.login.pojo.FpToken;

import io.github.jhipster.config.JHipsterProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class JwtTokenValidator {

	Logger log = LogManager.getLogger(JwtTokenValidator.class);
	
	@Autowired
	private JHipsterProperties jHipsterProperties;

	@SuppressWarnings("deprecation")
	public FpToken parseForgotPwdToken(String token) {
		String secret = jHipsterProperties.getSecurity().getAuthentication().getJwt().getSecret();
		Claims body = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();

		FpToken fpToken = new FpToken();
		fpToken.setEmail((String) body.get("email"));
		return fpToken;
	}

}
