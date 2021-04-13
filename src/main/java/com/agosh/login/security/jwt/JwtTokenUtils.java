package com.agosh.login.security.jwt;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.github.jhipster.config.JHipsterProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtils implements Serializable {

	private static final long serialVersionUID = -4335237427876519896L;

	@Autowired
	private JHipsterProperties jHipsterProperties;

	private long expiration = 99999999;

	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	@SuppressWarnings("deprecation")
	private Claims getAllClaimsFromToken(String token) {
		String secret = jHipsterProperties.getSecurity().getAuthentication().getJwt().getSecret();
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}
	/*-------------------------------------------------------------------------------------------*/

	public Date getIssuedAtDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getIssuedAt);
	}

	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	private Boolean isTokenExpired(String token) {
		final Date expirationDate = getExpirationDateFromToken(token);
		return expirationDate.before(new Date());
	}

	private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
		return (lastPasswordReset != null && created.before(lastPasswordReset));
	}

	private Boolean ignoreTokenExpiration(String token) {
		// here you specify tokens, for that the expiration is ignored
		return false;
	}

	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		return doGenerateToken(claims, userDetails.getUsername());
	}

	@SuppressWarnings("deprecation")
	private String doGenerateToken(Map<String, Object> claims, String subject) {
		String secret = jHipsterProperties.getSecurity().getAuthentication().getJwt().getSecret();
		final Date createdDate = new Date();
		final Date expirationDate = calculateExpirationDate(createdDate);

		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(createdDate)
				.setExpiration(expirationDate).signWith(SignatureAlgorithm.HS512, secret).compact();
	}

	public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
		final Date created = getIssuedAtDateFromToken(token);
		return !isCreatedBeforeLastPasswordReset(created, lastPasswordReset)
				&& (!isTokenExpired(token) || ignoreTokenExpiration(token));
	}

	@SuppressWarnings("deprecation")
	public String refreshToken(String token) {
		String secret = jHipsterProperties.getSecurity().getAuthentication().getJwt().getSecret();
		final Date createdDate = new Date();
		final Date expirationDate = calculateExpirationDate(createdDate);

		final Claims claims = getAllClaimsFromToken(token);
		claims.setIssuedAt(createdDate);
		claims.setExpiration(expirationDate);

		return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, secret).compact();
	}

	private Date calculateExpirationDate(Date createdDate) {
		this.expiration = jHipsterProperties.getSecurity().getAuthentication().getJwt().getTokenValidityInSeconds();
		return new Date(createdDate.getTime() + this.expiration);
	}

	public Date getCreatedDateFromToken(String token) {

		Date created = null;
		try {
			final Claims claims = this.getClaimsFromToken(token);
			if (claims != null) {
				created = new Date((Long) claims.get("created"));
			}
		} catch (Exception e) {
			created = null;
		}
		return created;
	}

	public String getAudienceFromToken(String token) {

		String audience = "";
		try {
			final Claims claims = this.getClaimsFromToken(token);
			if (claims != null) {
				audience = (String) claims.get("audience");
			}

		} catch (Exception e) {
			audience = null;
		}
		return audience;
	}

	@SuppressWarnings("deprecation")
	private Claims getClaimsFromToken(String token) {
		String secret = jHipsterProperties.getSecurity().getAuthentication().getJwt().getSecret();

		Claims claims;
		try {
			claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		} catch (Exception e) {
			claims = null;
		}
		return claims;
	}

	public Date generateCurrentDate() {

		return new Date(System.currentTimeMillis());
	}

	public Date generateExpirationDate() {

		return new Date(System.currentTimeMillis() + this.expiration * 1000);
	}

	@SuppressWarnings("deprecation")
	public String generateToken(Map<String, Object> claims) {
		String secret = jHipsterProperties.getSecurity().getAuthentication().getJwt().getSecret();
		return Jwts.builder().setClaims(claims).setExpiration(this.generateExpirationDate())
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}

}
