package com.agosh.login.security.jwt;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.agosh.login.pojo.FpToken;

import io.github.jhipster.config.JHipsterProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * convenience class to generate a token for testing your requests. Make sure
 * the used secret here matches the on in your application.properties
 * 
 */
@Component
public class JwtTokenGenerator {

	Logger log = LogManager.getLogger(JwtTokenGenerator.class);

	@Autowired
	private JwtTokenUtils tokenUtils;

	@Autowired
	private JHipsterProperties jHipsterProperties;

	@SuppressWarnings("deprecation")
	public String generateForgotPwdToken(FpToken fp) {

		try {
			String secret = jHipsterProperties.getSecurity().getAuthentication().getJwt().getSecret();
			Claims claims = Jwts.claims().setSubject(fp.getUserType());
			claims.put("email", fp.getEmail());

			return Jwts.builder().setClaims(claims).setExpiration(tokenUtils.generateExpirationDate())
					.signWith(SignatureAlgorithm.HS512, secret).compact();

		} catch (Exception e) {
			log.error("Exception::", e);
		}

		return StringUtils.EMPTY;
	}

}
